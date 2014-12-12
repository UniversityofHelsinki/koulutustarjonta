package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityConverterTest extends AbstractClientConverterTest {

    LearningOpportunityConverter converter;
    JsonNode fixture;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mockKoodistoClient();
        converter = new LearningOpportunityConverter(koodistoClient);
        fixture = fixture("fixtures/koulutus.json");
    }

    @Test
    public void testConvert() {
        LearningOpportunity lo = converter.convert(fixture);
        assertNotNull(lo);
        assertEquals("1.2.246.562.17.17939899864", lo.getOid());
        assertNotNull(lo.getQualification());
        assertNotNull(lo.getEducationalField());
        assertNotNull(lo.getDegreeProgram());
        assertEquals(Integer.valueOf(2015), lo.getStartYear());
        assertNotNull(lo.getStartSeason());
        assertEquals("2", lo.getPlannedDurationValue());
        assertNotNull(lo.getPlannedDurationUnit());
        assertEquals(Integer.valueOf(120), lo.getCreditValue());
        assertNotNull(lo.getCreditUnit());
        validateTeachingLanguages(lo.getTeachingLanguages());
        assertNotNull(lo.getGoals());
        assertNotNull(lo.getStructure());
        assertNotNull(lo.getPostgraduateStudies());
        assertNotNull(lo.getCompetency());
        assertNotNull(lo.getTranslations());
        assertEquals(3, lo.getTranslations().size());
        assertNotNull(lo.getLanguageInfo());
        assertNotNull(lo.getCooperation());
        assertNotNull(lo.getSelectingMajorSubject());
        assertNotNull(lo.getInternationalization());
        assertNotNull(lo.getWorkLifePlacement());
        assertNotNull(lo.getContents());
        assertNotNull(lo.getResearch());
        assertNotNull(lo.getThesis());
        assertEquals("1.2.246.562.10.94639300915", lo.getProvider());
    }

    private void validateTeachingLanguages(List<TeachingLanguage> teachnigLanguages) {
        assertNotNull(teachnigLanguages);
        assertEquals(3, teachnigLanguages.size());
        teachnigLanguages.forEach(tl -> {
                    if (tl.getLang().equals("fi")) {
                        assertEquals("suomi", tl.getName().getFi());
                        assertEquals("finska", tl.getName().getSv());
                        assertEquals("Finnish", tl.getName().getEn());
                    }
                    if (tl.getLang().equals("sv")) {
                        assertEquals("ruotsi", tl.getName().getFi());
                        assertEquals("svenska", tl.getName().getSv());
                        assertEquals("Swedish", tl.getName().getEn());
                    }
                    if (tl.getLang().equals("en")) {
                        assertEquals("englanti", tl.getName().getFi());
                        assertEquals("engelska", tl.getName().getSv());
                        assertEquals("English", tl.getName().getEn());
                    }
                }
        );

    }
}
