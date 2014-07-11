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
public class KoodistoClientFactory {

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

    public KoodistoClient build(Environment environment) {

        final Client client = new JerseyClientBuilder(environment)
                .using(httpClient)
                .build(this.getClass().toString());
        WebResource codeResource = client.resource(baseUrl);

        return new KoodistoClient(codeResource);
    }
}
