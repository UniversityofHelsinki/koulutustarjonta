package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationConverterTest extends AbstractClientConverterTest {

    private JsonNode fixture;
    private OrganizationConverter converter;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mockKoodistoClient();
        converter = new OrganizationConverter(koodistoClient);
        fixture = fixture("fixtures/organisaatio.json");
    }

    @Test
    public void testConvert() throws DataUpdateException {
        Organization o = converter.convert(fixture);
        assertNotNull(o);
        assertEquals("1.2.246.562.10.94639300915", o.getOid());
        assertEquals("Helsingin yliopisto, Matemaattis-luonnontieteellinen tiedekunta", o.getName().getFi());
        assertEquals("Helsingfors universitet, Matematisk-naturvetenskapliga fakulteten", o.getName().getSv());
        assertEquals("University of Helsinki, Faculty of Science", o.getName().getEn());
        assertEquals("<p>Luonnontieteellinen osaaminen on Suomessa kansainvälistä huippua.</p>", o.getOutline().getFi());
        assertEquals("<p>Kunskaperna i naturvetenskap är på internationell toppnivå i Finland.</p>", o.getOutline().getSv());
        assertEquals("<p>outline</p>", o.getOutline().getEn());
        assertEquals("<p>Yliopisto-opiskelijoille on tarjolla palveluja ja etuja </p>", o.getExpenses().getFi());
        assertEquals("<p>Åt universitetsstuderande erbjuds tjänster och förmåner gällande</p>", o.getExpenses().getSv());
        assertEquals("<p>expenses</p>", o.getExpenses().getEn());
        assertEquals("<p>matemaattis-luonnontieteellinen tiedekunta tarjoaa englanninkielistä</p>", o.getInternationalStudyPrograms().getFi());
        assertEquals("<p>Matematisk-naturvetenskapliga fakulteten ger undervisning på engelska</p>", o.getInternationalStudyPrograms().getSv());
        assertEquals("<p>international study programs</p>", o.getInternationalStudyPrograms().getEn());
        assertEquals("<p>Kaikilla Helsingin yliopiston opiskelijoilla on mahdollisuus</p>", o.getStudentTransfer().getFi());
        assertEquals("<p>Alla studenter vid Helsingfors universitet har möjlighet att delta</p>", o.getStudentTransfer().getSv());
        assertEquals("<p>student transfer</p>", o.getStudentTransfer().getEn());
        assertEquals("<p>Matemaattis-luonnontieteellinen tiedekunta on tutkimuksellisesti</p>", o.getStudyEnvironment().getFi());
        assertEquals("<p>Matematisk-naturvetenskapliga fakulteten är en internationell</p>", o.getStudyEnvironment().getSv());
        assertEquals("<p>study environment</p>", o.getStudyEnvironment().getEn());
        assertEquals("<p>Hakijoiden erityisjärjestelyillä tarkoitetaan käytännön </p>", o.getAccessibility().getFi());
        assertEquals("<div style=\"\"> <div> <p>Med specialarrangemang för sökande avses praktiska arrangemang </p> </div> </div>", o.getAccessibility().getSv());
        assertEquals("<p>esteettomyys en</p>", o.getAccessibility().getEn());
        assertEquals("<p>vousikello fi</p>", o.getYearClock().getFi());
        assertEquals("<p>vousikello sv</p>", o.getYearClock().getSv());
        assertEquals("<p>vousikello en</p>", o.getYearClock().getEn());
        assertEquals("<p>vastuuhenkilot fi</p>", o.getPeopleInCharge().getFi());
        assertEquals("<p>vastuuhenkilot sv</p>", o.getPeopleInCharge().getSv());
        assertEquals("<p>vastuuhenkilot en</p>", o.getPeopleInCharge().getEn());
        assertEquals("<p>valintamenettely fi</p>", o.getSelectionProcedure().getFi());
        assertEquals("<p>valintamenettely sv</p>", o.getSelectionProcedure().getSv());
        assertEquals("<p>valintamenettely en</p>", o.getSelectionProcedure().getEn());
        assertEquals("<p>aikaisemmin hankittu osaaminen fi</p>", o.getPreviouslyGainedExperience().getFi());
        assertEquals("<p>aikaisemmin hankittu osaaminen sv</p>", o.getPreviouslyGainedExperience().getSv());
        assertEquals("<p>aikaisemmin hankittu osaaminen en</p>", o.getPreviouslyGainedExperience().getEn());
        assertEquals("<p>kieliopinnot fi</p>", o.getLanguageStudies().getFi());
        assertEquals("<p>kieliopinnot sv</p>", o.getLanguageStudies().getSv());
        assertEquals("<p>kieliopinnot en</p>", o.getLanguageStudies().getEn());
        assertEquals("<p>Matemaattis-luonnontieteellisen tiedekunnan tutkintoihin</p>", o.getInternship().getFi());
        assertEquals("<p>Examina vid Matematisk-naturvetenskapliga fakulteten innehåller</p>", o.getInternship().getSv());
        assertEquals("<p>tyoharjoittelu en</p>", o.getInternship().getEn());
        assertEquals("https://twitter.fi/helsinkiuni", o.getSome().getTwitter().getFi());
        assertEquals("https://twitter.sv/helsinkiuni", o.getSome().getTwitter().getSv());
        assertEquals("https://twitter.en/helsinkiuni", o.getSome().getTwitter().getEn());
        assertEquals("https://www.facebook.com/ml.tiedekuntafi", o.getSome().getFacebook().getFi());
        assertEquals("https://www.facebook.com/ml.tiedekuntasv", o.getSome().getFacebook().getSv());
        assertEquals("https://www.facebook.com/ml.tiedekuntaen", o.getSome().getFacebook().getEn());
        assertEquals("https://www.googleplus.fi", o.getSome().getGooglePlus().getFi());
        assertEquals("https://www.googleplus.sv", o.getSome().getGooglePlus().getSv());
        assertEquals("https://www.googleplus.en", o.getSome().getGooglePlus().getEn());
        assertEquals("http://www.youtube.fi", o.getSome().getYoutube().getFi());
        assertEquals("http://www.youtube.sv", o.getSome().getYoutube().getSv());
        assertEquals("http://www.youtube.en", o.getSome().getYoutube().getEn());
        assertEquals("http://www.other.fi", o.getSome().getOther().getFi());
        assertEquals("http://www.other.sv", o.getSome().getOther().getSv());
        assertEquals("http://www.other.en", o.getSome().getOther().getEn());

        List<ContactInfo> applicantServices = o.getApplicantServices();
        assertNotNull(applicantServices);
        validateApplicantServices(applicantServices);
        List<ContactInfo> contactInfos = o.getContactInfos();
        assertNotNull(contactInfos);
        validateContactInfos(contactInfos);

    }
    private void validateContactInfos(List<ContactInfo> infos) {
        Map<String, List<ContactInfo>> byLang = infos.stream()
                .collect(groupingBy(info -> info.getLang()));

        ContactInfo fi = byLang.get("fi").get(0);
        assertEquals("1.2.246.562.5.83505933065", fi.getOid());
        assertEquals("Jyrängöntie 2", fi.getVisitingAddress().getStreet());
        assertEquals("00560", fi.getVisitingAddress().getPostalCode());
        assertEquals("HELSINKI", fi.getVisitingAddress().getPostOffice());
        assertEquals("PL 44", fi.getPostalAddress().getStreet());
        assertEquals("00014", fi.getPostalAddress().getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", fi.getPostalAddress().getPostOffice());
        assertEquals("ml-neuvonta@helsinki.fi", fi.getEmail());
        assertEquals("02941 50070", fi.getFax());
        assertEquals("02941 50066", fi.getPhone());
        assertEquals("http://www.helsinki.fi/ml/", fi.getWww());

        ContactInfo sv = byLang.get("sv").get(0);
        assertEquals("1.2.246.562.5.2014043013252959039288", sv.getOid());
        assertEquals("Jyränkövägen 2", sv.getVisitingAddress().getStreet());
        assertEquals("00560", sv.getVisitingAddress().getPostalCode());
        assertEquals("HELSINGFORS", sv.getVisitingAddress().getPostOffice());
        assertEquals("PB 44", sv.getPostalAddress().getStreet());
        assertEquals("00014", sv.getPostalAddress().getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", sv.getPostalAddress().getPostOffice());
        assertEquals("ml-neuvonta@helsinki.sv", sv.getEmail());
        assertNull(sv.getFax());
        assertEquals("02941 50066", sv.getPhone());
        assertEquals("http://www.helsinki.sv/matnat", sv.getWww());

    }


    private void validateApplicantServices(List<ContactInfo> infos) {
        Map<String, List<ContactInfo>> byLanguage = infos.stream()
                .collect(groupingBy(info -> info.getLang()));

        ContactInfo fi = byLanguage.get("fi").get(0);
        assertNotNull(fi);
        assertEquals("1.2.246.562.5.2014043013015175696862", fi.getOid());
        assertEquals("Fabianinkatu 33, 1. krs", fi.getVisitingAddress().getStreet());
        assertEquals("00014", fi.getVisitingAddress().getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", fi.getVisitingAddress().getPostOffice());
        assertEquals("PL 3", fi.getPostalAddress().getStreet());
        assertEquals("00014", fi.getPostalAddress().getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", fi.getPostalAddress().getPostOffice());
        assertEquals("hakijapalvelut@helsinki.fi", fi.getEmail());
        assertEquals("http://www.helsinki.fi/opiskelijaksi", fi.getWww());
        assertEquals("02941 24140", fi.getPhone());

        ContactInfo sv = byLanguage.get("sv").get(0);
        assertNotNull(fi);
        assertEquals("1.2.246.562.5.2014043013252959014345", sv.getOid());
        assertEquals("Fabiansgatan 33, 1 vån", sv.getVisitingAddress().getStreet());
        assertEquals("00014", sv.getVisitingAddress().getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", sv.getVisitingAddress().getPostOffice());
        assertEquals("PB 3", sv.getPostalAddress().getStreet());
        assertEquals("00014", sv.getPostalAddress().getPostalCode());
        assertEquals("HELSINGIN YLIOPISTO", sv.getPostalAddress().getPostOffice());
        assertEquals("studera@helsinki.fi", sv.getEmail());
        assertEquals("http://www.helsinki.fi/ansokningen", sv.getWww());
        assertEquals("02941 24140", sv.getPhone());
    }
}
