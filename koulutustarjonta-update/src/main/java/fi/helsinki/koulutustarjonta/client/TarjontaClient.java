package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.converter.ApplicationOptionConverter;
import fi.helsinki.koulutustarjonta.client.converter.LearningOpportunityConverter;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClient {

    private static final Logger LOG = LoggerFactory.getLogger(TarjontaClient.class);

    private WebResource learningOpportunitResource;
    private WebResource applicationOptionResource;
    private LearningOpportunityConverter learningOpportunityConverter;
    private ApplicationOptionConverter applicationOptionConverter;
    private KoodistoClient koodistoClient;

    public TarjontaClient(WebResource learningOpportunityResource, WebResource applicationOptionResource, KoodistoClient koodistoClient) {
        this.learningOpportunitResource = learningOpportunityResource;
        this.koodistoClient = koodistoClient;
        this.learningOpportunityConverter = new LearningOpportunityConverter();
        this.applicationOptionResource = applicationOptionResource;
        this.applicationOptionConverter = new ApplicationOptionConverter(koodistoClient);
    }

    public LearningOpportunity getLearningOpportunity(String oid) {
        LOG.debug(String.format("Fetching learning opportunity %s", oid));
        JsonNode learningOpportunityNode = learningOpportunitResource.path(oid).get(new GenericType<JsonNode>() {});

        LearningOpportunity learningOpportunity = learningOpportunityConverter.convert(learningOpportunityNode);

        return learningOpportunity;

    }

    public ApplicationOption getApplicationOption(String oid) {
        LOG.debug(String.format("Fetching application option with oid %s", oid));
        JsonNode applicationOptionNode = applicationOptionResource.path(oid)
                .get(new GenericType<JsonNode>() {});

        return applicationOptionConverter.convert(applicationOptionNode);

    }

}
