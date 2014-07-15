package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import fi.helsinki.koulutustarjonta.KotaUpdateApplication;
import fi.helsinki.koulutustarjonta.config.KotaUpdateConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Hannu Lyytikainen
 */
@Ignore
public class TarjontaIT {

    @ClassRule
    public static final DropwizardAppRule<KotaUpdateConfiguration> RULE =
            new DropwizardAppRule<KotaUpdateConfiguration>(KotaUpdateApplication.class, "update-config.yml");


    @Test
    public void testUpdate() {
        Client client = new Client();
        JsonNode response = client.resource(
                String.format("http://localhost:%d/update", RULE.getLocalPort()))
                .get(new GenericType<JsonNode>() {});
        //assertEquals(String.format("Update failed: %s", response.get("cause").textValue()), "ok", response.get("status").textValue());
        assertEquals( "ok", response.get("status").textValue());

    }

    @Test
    public void testUpdateApplicationOptions() {
        Client client = new Client();
        JsonNode response = client.resource(
                String.format("http://localhost:%d/update/applicationOption", RULE.getLocalPort()))
                .get(new GenericType<JsonNode>() {});
        //assertEquals(String.format("Update failed: %s", response.get("cause").textValue()), "ok", response.get("status").textValue());
        assertEquals( "ok", response.get("status").textValue());

    }
}
