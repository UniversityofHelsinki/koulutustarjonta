package fi.helsinki.koulutustarjonta.resource;

import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.LOContactDTO;
import fi.helsinki.koulutustarjonta.dto.LearningOpportunityDTO;
import fi.helsinki.koulutustarjonta.dto.TeachingLanguageDTO;
import fi.helsinki.koulutustarjonta.mapping.LearningOpportunityModelMapper;
import fi.helsinki.koulutustarjonta.test.Fixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityResourceTest {

    private static final LearningOpportunityDAO dao = mock(LearningOpportunityDAO.class);
    private final String oid = "1.2.3";
    private final LearningOpportunity learningOpportunity = Fixture.learningOpportunity(oid);
    private static final String API_ENDPOINT = "API_ENDPOINT";
    private final ModelMapper modelMapper = new LearningOpportunityModelMapper(API_ENDPOINT);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new LearningOpportunityResource(dao, API_ENDPOINT))
            .build();

    @Before
    public void setup() throws ResourceNotFound {
        when(dao.findById(eq(oid))).thenReturn(learningOpportunity);
        when(dao.findAll()).thenReturn(Lists.newArrayList(learningOpportunity));
    }

    @Test
    public void testGetLearningOpportunity() throws ResourceNotFound {
        LearningOpportunityDTO response = resources.client().resource(String.format("/koulutus/%s", oid))
                .get(LearningOpportunityDTO.class);
        LearningOpportunityDTO expected = modelMapper.map(learningOpportunity, LearningOpportunityDTO.class);
        assertTrue("Failure setting up expected result, missing loContact", expected.getLoContacts() != null);
        assertEquals("Failure setting up expected result, missing loContact", 1,expected.getLoContacts().size());
        learningOpportunityDTOsEqual(expected, response);
        verify(dao).findById(oid);
    }

    @Test
    public void testGetLearningOpportunities() {
        List<LearningOpportunityDTO> response = resources.client().resource(String.format("/koulutus/", oid))
                .get(new GenericType<List<LearningOpportunityDTO>>() {});
        LearningOpportunityDTO expected = modelMapper.map(learningOpportunity, LearningOpportunityDTO.class);
        learningOpportunityDTOsEqual(expected, response.get(0));
        verify(dao).findAll();
    }

    private void learningOpportunityDTOsEqual(LearningOpportunityDTO expected, LearningOpportunityDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        DTOTestUtil.i18NDTOsEqual(expected.getQualification(), actual.getQualification());
        DTOTestUtil.i18NDTOsEqual(expected.getEducationalField(), actual.getEducationalField());
        DTOTestUtil.i18NDTOsEqual(expected.getDegreeProgram(), actual.getDegreeProgram());
        assertEquals(expected.getStartYear(), actual.getStartYear());
        DTOTestUtil.i18NDTOsEqual(expected.getStartSeason(), actual.getStartSeason());
        assertEquals(expected.getPlannedDurationValue(), actual.getPlannedDurationValue());
        DTOTestUtil.i18NDTOsEqual(expected.getPlannedDurationUnit(), actual.getPlannedDurationUnit());
        assertEquals(expected.getCreditValue(), actual.getCreditValue());
        DTOTestUtil.i18NDTOsEqual(expected.getCreditUnit(), actual.getCreditUnit());
        DTOTestUtil.i18NDTOsEqual(expected.getStructure(), actual.getStructure());
        DTOTestUtil.i18NDTOsEqual(expected.getGoals(), actual.getGoals());
        DTOTestUtil.i18NDTOsEqual(expected.getPostgraduateStudies(), actual.getPostgraduateStudies());
        DTOTestUtil.i18NDTOsEqual(expected.getCompetency(), actual.getCompetency());
        DTOTestUtil.i18NDTOsEqual(expected.getLanguageInfo(), actual.getLanguageInfo());
        DTOTestUtil.i18NDTOsEqual(expected.getCooperation(), actual.getCooperation());
        DTOTestUtil.i18NDTOsEqual(expected.getSelectingMajorSubject(), actual.getSelectingMajorSubject());
        DTOTestUtil.i18NDTOsEqual(expected.getInternationalization(), actual.getInternationalization());
        DTOTestUtil.i18NDTOsEqual(expected.getWorkLifePlacement(), actual.getWorkLifePlacement());
        DTOTestUtil.i18NDTOsEqual(expected.getContents(), actual.getContents());
        DTOTestUtil.i18NDTOsEqual(expected.getResearch(), actual.getResearch());
        DTOTestUtil.i18NDTOsEqual(expected.getThesis(), actual.getThesis());
        DTOTestUtil.i18NDTOsEqual(expected.getEducationLevel(), actual.getEducationLevel());
        assertEquals(expected.getTeachingLanguages().size(), actual.getTeachingLanguages().size());
        assertEquals(expected.getTranslations().size(), actual.getTranslations().size());

        for (TeachingLanguageDTO expectedLang : expected.getTeachingLanguages()) {
            TeachingLanguageDTO actualLang = actual.getTeachingLanguages().stream()
                    .filter(x -> x.getLang().equals(expectedLang.getLang())).findFirst().get();
            DTOTestUtil.i18NDTOsEqual(expectedLang.getName(), actualLang.getName());
        }
        assertEquals(expected.getApplicationOptions().size(), actual.getApplicationOptions().size());
        assertEquals(expected.getApplicationOptions().get(0), actual.getApplicationOptions().get(0));
        assertEquals(expected.getProvider(), actual.getProvider());

        List<LOContactDTO> expectedLoContacts = expected.getLoContacts();
        List<LOContactDTO> actualLoContacts = actual.getLoContacts();

        assertEquals("Contacts didn't match", expectedLoContacts, actualLoContacts);

    }
}
