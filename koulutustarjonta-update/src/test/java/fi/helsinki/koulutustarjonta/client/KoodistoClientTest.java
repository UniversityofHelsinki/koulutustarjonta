package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.domain.Code;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Pasi Viertola on 6.6.2017.
 */
public class KoodistoClientTest {

    private KoodistoClient koodistoClient;

    @Before
    public void setup() throws IOException {
        WebResource codeResource = Mockito.mock(WebResource.class);
        koodistoClient = new KoodistoClient(codeResource);
        WebResource webResource = Mockito.mock(WebResource.class);
        Mockito.when(codeResource.path(Mockito.anyString())).thenReturn(webResource);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(testJson, JsonNode.class);
        Mockito.when(webResource.get((GenericType<Object>) Mockito.anyObject())).thenReturn(jsonNode);
    }

    @Test
    public void testGetCode(){
        String codeUriAndVersion = "testuriandversion";

        Assert.assertEquals(0, getInternalJsonCache().size());

        Code code = koodistoClient.getCode(codeUriAndVersion);

        Assert.assertEquals(1, getInternalJsonCache().size());

        Assert.assertEquals(code.getUri(), "kieli_sv");
        Assert.assertEquals(code.getValue(), "SV");

        Assert.assertEquals(code.getName().getEn(), "Swedish");
        Assert.assertEquals(code.getName().getFi(), "ruotsi");
        Assert.assertEquals(code.getName().getSv(), "svenska");

        Assert.assertEquals(code.getShortName().getEn(), "Swedish");
        Assert.assertEquals(code.getShortName().getFi(), "ruotsi");
        Assert.assertEquals(code.getShortName().getSv(), "svenska");

        Assert.assertEquals(code.getDescription().getEn(), "Swedish");
        Assert.assertEquals(code.getDescription().getFi(), "ruotsi");
        Assert.assertEquals(code.getDescription().getSv(), "svenska");

        // Cache will not be disposed because the time limit is not up
        koodistoClient.disposeOldCache();
        Assert.assertEquals(1, getInternalJsonCache().size());
        code = koodistoClient.getCode(codeUriAndVersion);
        Assert.assertEquals(1, getInternalJsonCache().size());

        // Set the time limit so that the cache will be cleared
        long one_hour = 1000 * 60 * 60 + 100;
        Whitebox.setInternalState(koodistoClient, "cacheCreatedAt", System.currentTimeMillis() - one_hour);
        // Check that the cache will be cleared
        koodistoClient.disposeOldCache();
        Assert.assertEquals(0, getInternalJsonCache().size());

        // The cache will be filled up after calling getCode.
        code = koodistoClient.getCode(codeUriAndVersion);
        Assert.assertEquals(1, getInternalJsonCache().size());
    }

    private Map<String, JsonNode> getInternalJsonCache() {
        return (Map<String, JsonNode>) Whitebox.getInternalState(koodistoClient, "jsonNodeCache");
    }

    private String testJson = "{\"koodiUri\":\"kieli_sv\"," +
            "\"resourceUri\":\"https://testi.virkailija.opintopolku.fi/koodisto-service/rest/codeelement/kieli_sv\"," +
            "\"version\":0," +
            "\"versio\":1," +
            "\"koodisto\":{\"koodistoUri\":\"kieli\"," +
            "\"organisaatioOid\":\"1.2.246.562.10.00000000001\"," +
            "\"koodistoVersios\":[1]}," +
            "\"koodiArvo\":\"SV\"," +
            "\"paivitysPvm\":1382966986774," +
            "\"voimassaAlkuPvm\":\"1990-01-01\"," +
            "\"voimassaLoppuPvm\":null," +
            "\"tila\":\"LUONNOS\"," +
            "\"metadata\":[{\"nimi\":\"ruotsi\"," +
            "\"kuvaus\":\"ruotsi\"," +
            "\"lyhytNimi\":\"ruotsi\"," +
            "\"kayttoohje\":null," +
            "\"kasite\":null," +
            "\"sisaltaaMerkityksen\":null," +
            "\"eiSisallaMerkitysta\":null," +
            "\"huomioitavaKoodi\":null," +
            "\"sisaltaaKoodiston\":null," +
            "\"kieli\":\"FI\"}," +
            "{\"nimi\":\"Swedish\"," +
            "\"kuvaus\":\"Swedish\"," +
            "\"lyhytNimi\":\"Swedish\"," +
            "\"kayttoohje\":null," +
            "\"kasite\":null," +
            "\"sisaltaaMerkityksen\":null," +
            "\"eiSisallaMerkitysta\":null," +
            "\"huomioitavaKoodi\":null," +
            "\"sisaltaaKoodiston\":null," +
            "\"kieli\":\"EN\"}," +
            "{\"nimi\":\"svenska\"," +
            "\"kuvaus\":\"svenska\"," +
            "\"lyhytNimi\":\"svenska\"," +
            "\"kayttoohje\":null," +
            "\"kasite\":null," +
            "\"sisaltaaMerkityksen\":null," +
            "\"eiSisallaMerkitysta\":null," +
            "\"huomioitavaKoodi\":null," +
            "\"sisaltaaKoodiston\":null," +
            "\"kieli\":\"SV\"}]}";
}
