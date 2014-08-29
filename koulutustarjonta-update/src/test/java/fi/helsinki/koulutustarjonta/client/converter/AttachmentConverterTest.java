package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Attachment;
import fi.helsinki.koulutustarjonta.domain.Code;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Hannu Lyytikainen
 */
public class AttachmentConverterTest extends AbstractClientConverterTest {

    JsonNode fixture;
    AttachmentConverter converter;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mockKoodistoClient();
        Code postalCode = new Code();
        postalCode.setValue("00014");
        when(koodistoClient.getCode(eq("posti_00014"))).thenReturn(postalCode);
        converter = new AttachmentConverter(koodistoClient);
        fixture = fixture("fixtures/liite.json");
    }

    @Test
    public void testConvert() {
        Attachment a = converter.convert(fixture);
        assertNotNull(a);
        assertEquals("333282", a.getOid());
        assertEquals("fi", a.getLang());
        assertEquals("Opintosuoritusote", a.getName());
        assertEquals("liitteen kuvaus fi", a.getDescription());
        assertEquals(1411372327561L, a.getDue().getTime());
        assertNotNull(a.getAddress());
    }
}
