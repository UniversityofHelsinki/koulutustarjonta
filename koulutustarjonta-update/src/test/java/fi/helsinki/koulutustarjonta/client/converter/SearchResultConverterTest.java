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
public class SearchResultConverterTest extends AbstractClientConverterTest {

    JsonNode fixture;
    SearchResultConverter converter;

    @Before
    public void init() throws IOException {
        converter = new SearchResultConverter();
        fixture = fixture("fixtures/koulutus_haku.json");
    }

    @Test
    public void testConvert() {
        List<String> oids = converter.convert(fixture);
        assertNotNull(oids);
        assertEquals(5, oids.size());
    }
}

