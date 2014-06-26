package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.FixtureHelpers;

import java.io.IOException;

/**
 * @author Hannu Lyytikainen
 */
public abstract class AbstractClientConverterTest {

    protected JsonNode fixture(String file) throws IOException {
        String fixture = FixtureHelpers.fixture(file);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(fixture);
    }

}
