package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Hannu Lyytikainen
 */
public class KoodistoClientFactory extends JerseyClientConfiguration {
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

    public KoodistoClient build(Environment environment) {

        final Client client = new JerseyClientBuilder(environment)
                .using(this)
                .build(this.getClass().toString());
        WebResource codeResource = client.resource(baseUrl);

        return new KoodistoClient(codeResource);
    }
}
