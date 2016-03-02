package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.converter.ApplicationOptionConverter;
import fi.helsinki.koulutustarjonta.client.converter.ApplicationSystemConverter;
import fi.helsinki.koulutustarjonta.client.converter.LearningOpportunityConverter;
import fi.helsinki.koulutustarjonta.client.converter.SearchResultConverter;
import fi.helsinki.koulutustarjonta.config.OpintopolkuConfiguration;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClient {

    private static final Logger LOG = LoggerFactory.getLogger(TarjontaClient.class);

    private final WebResource learningOpportunitResource;
    private final WebResource applicationOptionResource;
    private final WebResource applicationSystemResource;
    private final WebResource linkResource;
    private final ApplicationOptionConverter applicationOptionConverter;
    private final ApplicationSystemConverter applicationSystemConverter;
    private final LearningOpportunityConverter learningOpportunityConverter;
    private final SearchResultConverter searchResultConverter;
    private final List<String> ignoreList;

    public TarjontaClient(WebResource learningOpportunityResource, WebResource applicationOptionResource,
                          WebResource applicationSystemResource, WebResource linkResource, KoodistoClient koodistoClient,
                          OpintopolkuConfiguration opintopolku, List<String> ignoreList) {
        this.learningOpportunitResource = learningOpportunityResource;
        this.applicationOptionResource = applicationOptionResource;
        this.applicationOptionConverter = new ApplicationOptionConverter(koodistoClient);
        this.applicationSystemResource = applicationSystemResource;
        this.applicationSystemConverter = new ApplicationSystemConverter(koodistoClient, opintopolku);
        this.learningOpportunityConverter = new LearningOpportunityConverter(koodistoClient);
        this.searchResultConverter = new SearchResultConverter();
        this.linkResource = linkResource;

        this.learningOpportunitResource.addFilter(new RequestLoggingFilter());
        this.applicationOptionResource.addFilter(new RequestLoggingFilter());
        this.applicationSystemResource.addFilter(new RequestLoggingFilter());
        this.linkResource.addFilter(new RequestLoggingFilter());
        this.ignoreList = ignoreList;
    }

    public List<String> getLearningOpportunityOidsByProvider(String organizationOid) {

        LOG.debug(String.format("Searching learning opportunities with organization %s", organizationOid));
        JsonNode searchResult = learningOpportunitResource.path("search")
                .queryParam("organisationOid", organizationOid)
                .queryParam("tila", "JULKAISTU")
                .get(new GenericType<JsonNode>() {})
                .get("result").get("tulokset");
        if (searchResult.size() > 0) {
            return Lists.newArrayList(searchResult.get(0).get("tulokset"))
                    .parallelStream()
                    .map(result -> result.get("oid").textValue())
                    .collect(toList());
        }
        else {
            return Lists.newArrayList();
        }
    }

    private boolean shouldIgnoreSearchResult(JsonNode searchResult) {
        JsonNode name = searchResult.get("nimi");
        if(name == null) return false;
        List<String> names = Lists.newArrayList(name.get("fi"),name.get("en"),name.get("sv"))
                .stream()
                .filter(JsonNode::isTextual)
                .map(JsonNode::textValue).collect(Collectors.toList());
        return names.stream()
                .anyMatch(organizationName -> ignoreList.stream()
                        .anyMatch(ignoredWord -> ignoredWord.equalsIgnoreCase(organizationName)));
    }

    public List<String> getApplicationOptionOidsByProvider(String organizationOid) {
        LOG.debug(String.format("Searching application options with organization %s", organizationOid));
        JsonNode searchResult = applicationOptionResource.path("search")
                .queryParam("organisationOid", organizationOid)
                .queryParam("tila", "JULKAISTU")
                .get(new GenericType<JsonNode>() {})
                .get("result").get("tulokset");
        if(shouldIgnoreSearchResult(searchResult)) return Lists.newArrayList(); //KOTA-166, filter avoin yliopisto
        if (searchResult.size() > 0) {
            return Lists.newArrayList(searchResult.get(0).get("tulokset"))
                    .parallelStream()
                    .map(result -> result.get("oid").textValue())
                    .collect(toList());
        }
        else {
            return Lists.newArrayList();
        }
    }

    public LearningOpportunity getLearningOpportunity(String oid) {
        LOG.debug(String.format("Fetching learning opportunity %s", oid));
        JsonNode apiCallResult = learningOpportunitResource.path(oid).get(new GenericType<JsonNode>() {});

        LearningOpportunity learningOpportunity = learningOpportunityConverter.convert(apiCallResult);
        learningOpportunity.setApplicationOptions(getApplicationOptionOidsByLearningOpportunity(oid));
        learningOpportunity.setChildren(getChildLearningOpportunities(apiCallResult));
        learningOpportunity.setParents(getParentLearningOpportunities(apiCallResult));
        LOG.debug(String.format("Related LOs for %s -> %d, %s", oid, learningOpportunity.getChildren().size(), learningOpportunity.getParents().size()));

        return learningOpportunity;
    }

    private List<String> getChildLearningOpportunities(JsonNode apiCallResult) {

        // get komo oid
        String komoOid = learningOpportunityConverter.resolveKomoOid(apiCallResult);

        List<String> childKomoOids = searchChildKomos(komoOid);

        return searchRelatedLearningOpportunities(childKomoOids,
                String.valueOf(learningOpportunityConverter.resolveStartingYear(apiCallResult)),
                learningOpportunityConverter.resolveStartingSeasonCode(apiCallResult));
    }

    public List<String> getParentLearningOpportunities(JsonNode apiCallResult) {

        // get komo oid
        String komoOid = learningOpportunityConverter.resolveKomoOid(apiCallResult);

        List<String> parentKomoOids = searchParentKomos(komoOid);

        return searchRelatedLearningOpportunities(parentKomoOids,
                String.valueOf(learningOpportunityConverter.resolveStartingYear(apiCallResult)),
                learningOpportunityConverter.resolveStartingSeasonCode(apiCallResult));
    }

    private List<String> searchRelatedLearningOpportunities(List<String> komoOids,
                                                            String startingYear,
                                                            String startingSeasonCode) {
        return komoOids.stream()
                .map(oid -> {
                    JsonNode searchResult = learningOpportunitResource.path("search")
                            .queryParam("komoOid", oid)
                            .queryParam("alkamisVuosi", startingYear)
                            .queryParam("alkamisKausi", startingSeasonCode)
                            .get(new GenericType<JsonNode>() {});
                    return searchResultConverter.convert(searchResult);

                })
                .flatMap(oids -> oids.stream())
                .collect(toList());
    }

    private List<String> searchParentKomos(String komoOid) {
        return Lists.newArrayList(linkResource
                .path(komoOid)
                .path("parents")
                .get(new GenericType<JsonNode>() {})
                .get("result"))
                .stream()
                .map(element -> element.textValue())
                .collect(toList());
    }

    private List<String> searchChildKomos(String komoOid) {
        return  Lists.newArrayList(linkResource
                .path(komoOid)
                .get(new GenericType<JsonNode>() {})
                .get("result"))
                .stream()
                .map(element -> element.textValue())
                .collect(toList());
    }

    public ApplicationOption getApplicationOption(String oid) {
        LOG.debug(String.format("Fetching application option with oid %s", oid));
        JsonNode applicationOptionJson = getApplicationOptionJson(oid);
        return applicationOptionConverter.convert(applicationOptionJson);
    }

    public ApplicationSystem getApplicationSystem(String oid) {
        JsonNode applicationSystemJson = applicationSystemResource
                .path(oid)
                .get(new GenericType<JsonNode>() {});

        return applicationSystemConverter.convert(applicationSystemJson);
    }

    private List<String> getApplicationOptionOidsByLearningOpportunity(String learningOpportunityOid) {
        LOG.debug(String.format("Fetching application option oids for learning opportunity %s",
                learningOpportunityOid));
        JsonNode result = learningOpportunitResource
                .path(learningOpportunityOid)
                .path("hakukohteet")
                .get(new GenericType<JsonNode>() {});

        return applicationOptionConverter.resolveOids(result)
                .stream()
                .map(oid -> getApplicationOptionJson(oid))
                .filter(apiResult -> statusOk(apiResult) && isApplicationOptionReleased(apiResult))
                .map(apiResult -> applicationOptionConverter.resolveOid(apiResult))
                .collect(Collectors.toList());
    }

    private JsonNode getApplicationOptionJson(String oid) {
        return applicationOptionResource.path(oid)
                .get(new GenericType<JsonNode>() {});
    }

    private boolean statusOk(JsonNode apiResult) {
        return apiResult.get("status").textValue().equals("OK");
    }

    private boolean isApplicationOptionReleased(JsonNode apiResult) {
        return applicationOptionConverter.isApplicationOptionReleased(apiResult);
    }
}
