package fi.helsinki.koulutustarjonta.client.wrapper;

import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.client.converter.LearningOpportunityWrapper;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityWrapperTest extends AbstractClientWrapperTest {

    LearningOpportunityWrapper wrapper;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mockKoodistoClient();
        wrapper = new LearningOpportunityWrapper(fixture("fixtures/koulutus.json"), koodistoClient);
    }

    @Test
    public void testConvert() {
        LearningOpportunity lo = wrapper.getLearningOpportunity();
        assertNotNull(lo);
        assertEquals("1.2.246.562.17.17939899864", lo.getOid());
        assertNotNull(lo.getQualification());
        assertNotNull(lo.getEducationalField());
        assertNotNull(lo.getDegreeProgram());
        assertEquals(Integer.valueOf(2015), lo.getStartYear());
        assertNotNull(lo.getStartSeason());
        assertEquals(Integer.valueOf(2), lo.getPlannedDurationValue());
        assertNotNull(lo.getPlannedDurationUnit());
        assertEquals(Integer.valueOf(120), lo.getCreditValue());
        assertNotNull(lo.getCreditUnit());
        assertNotNull(lo.getTeachingLanguages());
        assertEquals(3, lo.getTeachingLanguages().size());
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
}