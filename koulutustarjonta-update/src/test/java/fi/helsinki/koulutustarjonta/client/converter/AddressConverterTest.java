package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Address;
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
public class AddressConverterTest extends AbstractClientConverterTest {

    JsonNode fixture;
    AddressConverter converter;

    @Before
    public void init() {
        KoodistoClient koodistoClient = mockKoodistoClient();
        Code postalCode = new Code();
        postalCode.setValue("00014");
        when(koodistoClient.getCode(eq("posti_00014"))).thenReturn(postalCode);
        converter = new AddressConverter(koodistoClient);
    }

    @Test
    public void testWithPostalCode() throws IOException {
        fixture = fixture("fixtures/osoite1.json");
        Address address = converter.convert(fixture);
        assertNotNull(address);
        assertEquals("soveltuvuustie", address.getStreet());
        assertEquals("00100", address.getPostalCode());
        assertEquals("HELSINKI", address.getPostOffice());
    }

    @Test
    public void testWithPostalCodeUri() throws IOException {
        fixture = fixture("fixtures/osoite2.json");
        Address address = converter.convert(fixture);
        assertNotNull(address);
        assertEquals("jyrängöntie 2", address.getStreet());
        assertEquals("00014", address.getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", address.getPostOffice());
    }
}
