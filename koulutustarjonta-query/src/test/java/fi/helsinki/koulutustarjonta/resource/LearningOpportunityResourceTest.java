package fi.helsinki.koulutustarjonta.resource;

import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
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
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityResourceTest {

    private static final LearningOpportunityDAO dao = mock(LearningOpportunityDAO.class);
    private final String oid = "1.2.3";
    private final LearningOpportunity learningOpportunity = Fixture.learningOpportunity(oid);
    private final ModelMapper modelMapper = new LearningOpportunityModelMapper();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new LearningOpportunityResource(dao))
            .build();

    @Before
    public void setup() {
        when(dao.findById(eq(oid))).thenReturn(learningOpportunity);
        when(dao.findAll()).thenReturn(Lists.newArrayList(learningOpportunity));
    }

    @Test
    public void testGetLearningOpportunity() {
        LearningOpportunityDTO response = resources.client().resource(String.format("/koulutus/%s", oid))
                .get(LearningOpportunityDTO.class);
        LearningOpportunityDTO expected = modelMapper.map(learningOpportunity, LearningOpportunityDTO.class);
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

    public static void learningOpportunityDTOsEqual(LearningOpportunityDTO expected, LearningOpportunityDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        i18NDTOsEqual(expected.getQualification(), actual.getQualification());
        i18NDTOsEqual(expected.getEducationalField(), actual.getEducationalField());
        i18NDTOsEqual(expected.getDegreeProgram(), actual.getDegreeProgram());
        assertEquals(expected.getStartYear(), actual.getStartYear());
        i18NDTOsEqual(expected.getStartSeason(), actual.getStartSeason());
        assertEquals(expected.getPlannedDurationValue(), actual.getPlannedDurationValue());
        i18NDTOsEqual(expected.getPlannedDurationUnit(), actual.getPlannedDurationUnit());
        assertEquals(expected.getCreditValue(), actual.getCreditValue());
        i18NDTOsEqual(expected.getCreditUnit(), actual.getCreditUnit());
        i18NDTOsEqual(expected.getStructure(), actual.getStructure());
        i18NDTOsEqual(expected.getGoals(), actual.getGoals());
        i18NDTOsEqual(expected.getPostgraduateStudies(), actual.getPostgraduateStudies());
        i18NDTOsEqual(expected.getCompetency(), actual.getCompetency());
        i18NDTOsEqual(expected.getLanguageInfo(), actual.getLanguageInfo());
        i18NDTOsEqual(expected.getCooperation(), actual.getCooperation());
        i18NDTOsEqual(expected.getSelectingMajorSubject(), actual.getSelectingMajorSubject());
        i18NDTOsEqual(expected.getInternationalization(), actual.getInternationalization());
        i18NDTOsEqual(expected.getWorkLifePlacement(), actual.getWorkLifePlacement());
        i18NDTOsEqual(expected.getContents(), actual.getContents());
        i18NDTOsEqual(expected.getResearch(), actual.getResearch());
        i18NDTOsEqual(expected.getThesis(), actual.getThesis());
        assertEquals(expected.getTeachingLanguages().size(), actual.getTeachingLanguages().size());
        assertEquals(expected.getTranslations().size(), actual.getTranslations().size());

        for (TeachingLanguageDTO expectedLang : expected.getTeachingLanguages()) {
            TeachingLanguageDTO actualLang = actual.getTeachingLanguages().stream()
                    .filter(x -> x.getLang().equals(expectedLang.getLang())).findFirst().get();
            i18NDTOsEqual(expectedLang.getName(), actualLang.getName());
        }

    }

    public static void i18NDTOsEqual(I18NDTO expected, I18NDTO actual) {
        assertEquals(expected.getFi(), actual.getFi());
        assertEquals(expected.getSv(), actual.getSv());
        assertEquals(expected.getEn(), actual.getEn());

    }
}
