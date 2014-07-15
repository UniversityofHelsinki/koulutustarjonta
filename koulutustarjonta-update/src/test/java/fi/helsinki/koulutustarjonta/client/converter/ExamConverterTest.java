package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Exam;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * @author Hannu Lyytikainen
 */
public class ExamConverterTest extends AbstractClientConverterTest {

    JsonNode fixture;
    ExamConverter converter;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mock(KoodistoClient.class);
        converter = new ExamConverter(koodistoClient);
        fixture = fixture("fixtures/valintakoe.json");
    }

    @Test
    public void testConvert() {
        Exam e = converter.convert(fixture);
        assertNotNull(e);
        assertEquals("333300", e.getOid());
        assertEquals("en", e.getLang());
        assertEquals("soveltuvuuskoe en", e.getType());
        assertEquals("soveltuvuus kuvaus en", e.getDescription());
        List<ExamEvent> events = e.getEvents();
        assertNotNull(events);
        assertEquals(1, events.size());
        ExamEvent event = events.get(0);
        assertEquals("333301", event.getOid());
        assertEquals("lisatiedot", event.getInfo());
        assertNotNull(event.getAddress());
        assertEquals(1406115803849L, event.getStarts().getTime());
        assertEquals(1406123013232L, event.getEnds().getTime());
    }

}
