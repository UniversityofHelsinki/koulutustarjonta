package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class SearchResultWrapperTest extends AbstractClientConverterTest {

    JsonNode fixture;
    SearchResultWrapper wrapper;

    @Before
    public void init() throws IOException {
        JsonNode fixture = fixture("fixtures/koulutus_haku.json");
        wrapper = new SearchResultWrapper(fixture);
    }

    @Test
    public void testGetOids() {
        List<String> oids = wrapper.getOids();
        assertNotNull(oids);
        assertEquals(5, oids.size());
    }
}

