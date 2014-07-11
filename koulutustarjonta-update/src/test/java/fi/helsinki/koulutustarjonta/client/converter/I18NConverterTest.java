package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.domain.I18N;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Hannu Lyytikainen
 */
public class I18NConverterTest extends AbstractClientConverterTest {

    JsonNode description;
    I18NConverter i18nConverter;

    @Before
    public void inti() throws IOException {
        i18nConverter = new I18NConverter();
        description = fixture("fixtures/koulutus_description.json");
    }

    @Test
    public void testConvert() {
        I18N i18n = i18nConverter.convertI18N(description);
        assertNotNull(i18n);
        assertEquals("description fi", i18n.getFi());
        assertEquals("description sv", i18n.getSv());
        assertEquals("description en", i18n.getEn());
    }

    @Test
    public void testConvertNull() {
        assertNull(i18nConverter.convertI18N(null));
    }

}
