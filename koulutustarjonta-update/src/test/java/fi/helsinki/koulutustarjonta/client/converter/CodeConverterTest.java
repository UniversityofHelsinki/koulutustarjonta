package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.domain.Code;
import fi.helsinki.koulutustarjonta.domain.I18N;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Hannu Lyytikainen
 */
public class CodeConverterTest extends AbstractClientConverterTest {

    JsonNode fixture;
    CodeConverter converter;

    @Before
    public void init() {
        converter = new CodeConverter();
    }

    @Test
    public void testConvert() throws IOException {
        fixture = fixture("fixtures/koodi1.json");
        Code code = converter.convert(fixture);
        assertNotNull(code);
        assertEquals("pohjakoulutusvaatimuskorkeakoulut_118", code.getUri());
        assertEquals("118", code.getValue());
        I18N name = code.getName();
        assertNotNull(name);
        assertEquals("Avoimen yliopiston opinnot", name.getFi());
        assertEquals("Studier vid öppna universitetet", name.getSv());
        assertEquals("Studies at the Open University", name.getEn());
        I18N shortName = code.getShortName();
        assertNotNull(shortName);
        assertEquals("Avoimen yliopiston opinnot short", shortName.getFi());
        assertEquals("Studier vid öppna universitetet short", shortName.getSv());
        assertEquals("Studies at the Open University short", shortName.getEn());
        I18N description = code.getDescription();
        assertNotNull(description);
        assertEquals("Avoimen yliopiston opinnot desc", description.getFi());
        assertEquals("Studier vid öppna universitetet desc", description.getSv());
        assertEquals("Studies at the Open University desc", description.getEn());
    }

    @Test
    public void testConvertWithMissingLanguages() throws IOException {
        fixture = fixture("fixtures/koodi2.json");
        Code code = converter.convert(fixture);
        assertNotNull(code);
        assertEquals("pohjakoulutusvaatimuskorkeakoulut_118", code.getUri());
        assertEquals("118", code.getValue());
        I18N name = code.getName();
        assertNotNull(name);
        assertEquals("Avoimen yliopiston opinnot", name.getFi());
        assertNull(name.getSv());
        assertNull(name.getEn());
        I18N shortName = code.getShortName();
        assertNotNull(shortName);
        assertEquals("Avoimen yliopiston opinnot short", shortName.getFi());
        assertNull(shortName.getSv());
        assertNull(shortName.getEn());
        I18N description = code.getDescription();
        assertNotNull(description);
        assertEquals("Avoimen yliopiston opinnot desc", description.getFi());
        assertNull(description.getSv());
        assertNull(description.getEn());
    }
}
