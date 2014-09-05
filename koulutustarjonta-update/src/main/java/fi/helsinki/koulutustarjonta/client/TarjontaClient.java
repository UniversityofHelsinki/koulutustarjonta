package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.converter.ApplicationOptionConverter;
import fi.helsinki.koulutustarjonta.client.converter.ApplicationSystemConverter;
import fi.helsinki.koulutustarjonta.client.converter.LearningOpportunityConverter;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClient {

    private static final Logger LOG = LoggerFactory.getLogger(TarjontaClient.class);

    private WebResource learningOpportunitResource;
    private WebResource applicationOptionResource;
    private WebResource applicationSystemResource;
    private LearningOpportunityConverter learningOpportunityConverter;
    private ApplicationOptionConverter applicationOptionConverter;
    private ApplicationSystemConverter applicationSystemConverter;
    private KoodistoClient koodistoClient;

    public TarjontaClient(WebResource learningOpportunityResource, WebResource applicationOptionResource,
                          WebResource applicationSystemResource, KoodistoClient koodistoClient) {
        this.learningOpportunitResource = learningOpportunityResource;
        this.koodistoClient = koodistoClient;
        this.learningOpportunityConverter = new LearningOpportunityConverter(koodistoClient);
        this.applicationOptionResource = applicationOptionResource;
        this.applicationOptionConverter = new ApplicationOptionConverter(koodistoClient);
        this.applicationSystemResource = applicationSystemResource;
        this.applicationSystemConverter = new ApplicationSystemConverter(koodistoClient);
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
            return new ArrayList<>();
        }
    }

    public List<String> getApplicationOptionOidsByProvider(String organizationOid) {
        LOG.debug(String.format("Searching application options with organization %s", organizationOid));
        JsonNode searchResult = applicationOptionResource.path("search")
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
            return new ArrayList<>();
        }
    }

    public LearningOpportunity getLearningOpportunity(String oid) {
        LOG.debug(String.format("Fetching learning opportunity %s", oid));
        JsonNode learningOpportunityNode = learningOpportunitResource.path(oid).get(new GenericType<JsonNode>() {});

        LearningOpportunity learningOpportunity = learningOpportunityConverter.convert(learningOpportunityNode);

        return learningOpportunity;
    }

    public ApplicationOption getApplicationOption(String oid) {
        LOG.debug(String.format("Fetching application option with oid %s", oid));
        JsonNode applicationOptionJson = applicationOptionResource.path(oid)
                .get(new GenericType<JsonNode>() {});

        ApplicationOption applicationOption = applicationOptionConverter.convert(applicationOptionJson);

        String applicationSystemOid = applicationOptionConverter.resolveApplicationSystemOid(applicationOptionJson);
        JsonNode applicationSystemJson = applicationSystemResource
                .path(applicationSystemOid)
                .get(new GenericType<JsonNode>() {});

        ApplicationSystem applicationSystem = applicationSystemConverter.convert(applicationSystemJson);
        applicationOption.setApplicationSystem(applicationSystem);

        return applicationOption;
    }
}
