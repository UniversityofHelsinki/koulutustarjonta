package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class OrganisaatioClientFactory extends JerseyClientConfiguration {

    private static final String ORGANIZATION_PATH = "organisaatio/";

    @NotEmpty
    private String baseUrl;

    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
    }

    @JsonProperty
    public List<String> organisaatioOidIgnore;

    @JsonProperty
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public OrganisaatioClient build(Environment environment, KoodistoClient koodistoClient) {

        final Client client = new JerseyClientBuilder(environment)
                .using(this)
                .build(this.getClass().toString());
        WebResource organizationResource = client.resource(String.format("%s%s", baseUrl, ORGANIZATION_PATH));
        List<String> ignoreOids = new ArrayList<>();
        if(organisaatioOidIgnore != null) {
            ignoreOids.addAll(organisaatioOidIgnore);
        }
        return new OrganisaatioClient(organizationResource, koodistoClient, ignoreOids);

    }
}
