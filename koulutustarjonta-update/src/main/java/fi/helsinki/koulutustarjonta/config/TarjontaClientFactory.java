package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClientFactory extends JerseyClientConfiguration {

    private static final String LEARNING_OPPORTUNITY_PATH = "koulutus/";
    private static final String APPLICATION_OPTION_PATH = "hakukohde/";
    private static final String APPLICATION_SYSTEM_PATH = "haku/";
    private static final String LINK_PATH = "link/";

    @NotEmpty
    private String baseUrl;

    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
    }

    @Valid
    @NotNull
    @JsonProperty("opintopolku")
    private OpintopolkuConfiguration opintopolku = new OpintopolkuConfiguration();

    @JsonProperty
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public TarjontaClient build(Environment environment, KoodistoClient koodistoClient) {
        final Client client = new JerseyClientBuilder(environment)
                .using(this)
                .build(this.getClass().toString());
        WebResource learningOpportunityResource = client.resource(String.format("%s%s", baseUrl, LEARNING_OPPORTUNITY_PATH));
        WebResource applicationOptionResource = client.resource(String.format("%s%s", baseUrl, APPLICATION_OPTION_PATH));
        WebResource applicationSystemResource = client.resource(String.format("%s%s", baseUrl, APPLICATION_SYSTEM_PATH));
        WebResource linkResource = client.resource(String.format("%s%s", baseUrl, LINK_PATH));

        return new TarjontaClient(learningOpportunityResource,
                applicationOptionResource,
                applicationSystemResource,
                linkResource,
                koodistoClient,
                opintopolku);

    }
}
