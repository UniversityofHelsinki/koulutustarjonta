package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClientFactory {

    private static final String LEARNING_OPPORTUNITY_PATH = "koulutus/";
    private static final String APPLICATION_OPTION_PATH = "hakukohde/";
    private static final String APPLICATION_SYSTEM_PATH = "haku/";

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @NotEmpty
    private String baseUrl;

    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
    }

    @JsonProperty
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public TarjontaClient build(Environment environment, KoodistoClient koodistoClient) {

        final Client client = new JerseyClientBuilder(environment)
                .using(httpClient)
                .build(this.getClass().toString());
        WebResource learningOpportunityResource = client.resource(String.format("%s%s", baseUrl, LEARNING_OPPORTUNITY_PATH));
        WebResource applicationOptionResource = client.resource(String.format("%s%s", baseUrl, APPLICATION_OPTION_PATH));
        WebResource applicationSystemResource = client.resource(String.format("%s%s", baseUrl, APPLICATION_SYSTEM_PATH));

        return new TarjontaClient(learningOpportunityResource, applicationOptionResource, applicationSystemResource, koodistoClient);

    }
}
