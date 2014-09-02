package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Address;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class AddressConverterTest extends AbstractClientConverterTest {

    AddressConverter converter;

    @Before
    public void init() {
        KoodistoClient koodistoClient = mockKoodistoClient();
        converter = new AddressConverter(koodistoClient);
    }

    @Test
    public void testConvertWithPostalCode() throws IOException {
        JsonNode fixture = fixture("fixtures/osoite1.json");
        Address address = converter.convert(fixture);
        assertNotNull(address);
        assertEquals("soveltuvuustie", address.getStreet());
        assertEquals("00100", address.getPostalCode());
        assertEquals("HELSINKI", address.getPostOffice());
    }

    @Test
    public void testConvertWithPostalCodeUri() throws IOException {
        JsonNode fixture = fixture("fixtures/osoite2.json");
        Address address = converter.convert(fixture);
        assertNotNull(address);
        assertEquals("jyrängöntie 2", address.getStreet());
        assertEquals("00014", address.getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", address.getPostOffice());
    }

    @Test
    public void testConvertOrganizationAddress() throws IOException {
        JsonNode fixture = fixture("fixtures/osoite_organisaatio.json");
        Address address = converter.convertOrganizationAddress(fixture);
        assertEquals("Jyrängöntie 2", address.getStreet());
        assertEquals("00560", address.getPostalCode());
        assertEquals("HELSINKI", address.getPostOffice());
    }

}
