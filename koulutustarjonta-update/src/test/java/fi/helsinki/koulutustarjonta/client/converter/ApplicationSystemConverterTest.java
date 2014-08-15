package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.domain.Code;
import fi.helsinki.koulutustarjonta.domain.I18N;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemConverterTest extends AbstractClientConverterTest {

    JsonNode fixture;
    ApplicationSystemConverter converter;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mock(KoodistoClient.class);

        Code applicationMethod = new Code();
        applicationMethod.setName(new I18N("hakutapa fi", "hakutapa sv", "hakutapa en"));
        when(koodistoClient.getCode(eq("hakutapa_02#1"))).thenReturn(applicationMethod);

        Code applicationSeason = new Code();
        applicationSeason.setValue("S");
        applicationSeason.setName(new I18N("app season fi", "app season sv", "app season en"));
        when(koodistoClient.getCode(eq("kausi_s#1"))).thenReturn(applicationSeason);

        Code educationStartSeason = new Code();
        educationStartSeason.setValue("K");
        educationStartSeason.setName(new I18N("edu season fi", "edu season sv", "edu season en"));
        when(koodistoClient.getCode(eq("kausi_k#1"))).thenReturn(educationStartSeason);

        converter = new ApplicationSystemConverter(koodistoClient);
        fixture = fixture("fixtures/haku.json");
    }

    @Test
    public void testConvert() {
        ApplicationSystem as = converter.convert(fixture);
        assertNotNull(as);
        assertEquals("1.2.246.562.29.31035368682", as.getOid());
        assertEquals("Haku avoimessa", as.getName().getFi());
        assertEquals("Ansökan", as.getName().getSv());
        assertEquals("Application system", as.getName().getEn());
        assertEquals("hakutapa fi", as.getApplicationMethod().getFi());
        assertEquals("hakutapa sv", as.getApplicationMethod().getSv());
        assertEquals("hakutapa en", as.getApplicationMethod().getEn());
        assertEquals(2014, as.getApplicationYear());
        assertEquals("S", as.getApplicationSeason().getValue());
        assertEquals("app season fi", as.getApplicationSeason().getName().getFi());
        assertEquals("app season sv", as.getApplicationSeason().getName().getSv());
        assertEquals("app season en", as.getApplicationSeason().getName().getEn());
        assertEquals(2015, as.getEducationStartYear());
        assertEquals("K", as.getEducationStartSeason().getValue());
        assertEquals("edu season fi", as.getEducationStartSeason().getName().getFi());
        assertEquals("edu season sv", as.getEducationStartSeason().getName().getSv());
        assertEquals("edu season en", as.getEducationStartSeason().getName().getEn());
        assertEquals("http://www.helsinki.fi/ml/lomakkeet/opintooikeus.pdf", as.getApplicationFormUrl());
        assertNotNull(as.getApplicationPeriods());
        ApplicationPeriod ap = as.getApplicationPeriods().get(0);
        assertNotNull(ap);
        assertEquals("228749", ap.getId());
        assertEquals("Haku avoimessa yliopistossa opintoja suorittaneille", ap.getName());
        assertEquals(1410152404012L, ap.getStarts().getTime());
        assertEquals(1411387241418L, ap.getEnds().getTime());
    }
}

