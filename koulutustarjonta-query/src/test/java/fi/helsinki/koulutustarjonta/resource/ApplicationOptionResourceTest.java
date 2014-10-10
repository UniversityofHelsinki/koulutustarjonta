package fi.helsinki.koulutustarjonta.resource;

import com.sun.jersey.api.client.GenericType;
import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.dto.*;
import fi.helsinki.koulutustarjonta.mapping.ApplicationOptionModelMapper;
import fi.helsinki.koulutustarjonta.test.Fixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.fest.util.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionResourceTest {


    private final String aoOid = "ao_oid";
    private static final String API_ENDPOINT = "API_ENDPOINT";
    private static final ApplicationOptionDAO dao = mock(ApplicationOptionDAO.class);
    private final ApplicationOption applicationOption = Fixture.applicationOption(aoOid);
    private final ModelMapper modelMapper = new ApplicationOptionModelMapper(API_ENDPOINT);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ApplicationOptionResource(dao, API_ENDPOINT))
            .build();

    @Before
    public void setup() throws ResourceNotFound {
        when(dao.findByOid(eq(aoOid))).thenReturn(applicationOption);
        when(dao.findAll()).thenReturn(Lists.newArrayList(applicationOption));
    }

    @Test
    public void testGetApplicationOption() throws ResourceNotFound {
        ApplicationOptionDTO response = resources.client().resource(String.format("/hakukohde/%s", aoOid))
                .get(ApplicationOptionDTO.class);
        ApplicationOptionDTO expected = modelMapper.map(applicationOption, ApplicationOptionDTO.class);
        applicationOptionDTOsEqual(expected, response);
        verify(dao).findByOid(aoOid);
    }

    @Test
    public void testGetApplicationOptions() {
        List<ApplicationOptionDTO> response = resources.client().resource("/hakukohde")
                .get(new GenericType<List<ApplicationOptionDTO>>() {});
        ApplicationOptionDTO expected = modelMapper.map(applicationOption, ApplicationOptionDTO.class);
        applicationOptionDTOsEqual(expected, response.get(0));
        verify(dao).findAll();
    }

    private void applicationOptionDTOsEqual(ApplicationOptionDTO expected, ApplicationOptionDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        DTOTestUtil.i18NDTOsEqual(expected.getName(), actual.getName());
        assertEquals(expected.getStartingQuota(), actual.getStartingQuota());
        DTOTestUtil.i18NDTOsEqual(expected.getSora(), actual.getSora());
        DTOTestUtil.i18NDTOsEqual(expected.getAdditionalInfo(), actual.getAdditionalInfo());
        DTOTestUtil.i18NDTOsEqual(expected.getSelectionCriteria(), actual.getSelectionCriteria());
        DTOTestUtil.i18NDTOsEqual(expected.getRequirementDescription(), actual.getRequirementDescription());
        assertNotNull(actual.getExams());
        ExamDTO examActual = actual.getExams().get(0);
        ExamDTO examExpected = expected.getExams().get(0);
        examDTOsEqual(examExpected, examActual);
        assertNotNull(actual.getAttachments());
        attachmentDTOsEqual(expected.getAttachments().get(0), actual.getAttachments().get(0));
        assertNotNull(actual.getRequirements());
        DTOTestUtil.i18NDTOsEqual(expected.getRequirements().get(0), actual.getRequirements().get(0));
        assertEquals(expected.getApplicationSystem(), actual.getApplicationSystem());
    }

    private void attachmentDTOsEqual(AttachmentDTO expected, AttachmentDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        assertEquals(expected.getLang(), actual.getLang());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDue(), actual.getDue());
        addressDTOsEqual(expected.getAddress(), actual.getAddress());
    }

    private void examDTOsEqual(ExamDTO expected, ExamDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        assertEquals(expected.getLang(), actual.getLang());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getType(), actual.getType());
        assertNotNull(actual.getEvents());
        ExamEventDTO eventActual = actual.getEvents().get(0);
        ExamEventDTO eventExpected = expected.getEvents().get(0);
        examEventDTOsEqual(eventExpected, eventActual);
    }

    private void examEventDTOsEqual(ExamEventDTO expeceted, ExamEventDTO actual) {
        assertEquals(expeceted.getOid(), actual.getOid());
        assertEquals(expeceted.getInfo(), actual.getInfo());
        assertEquals(expeceted.getStarts(), actual.getStarts());
        assertEquals(expeceted.getEnds(), actual.getEnds());
        addressDTOsEqual(expeceted.getAddress(), actual.getAddress());
    }

    private void addressDTOsEqual(AddressDTO expected, AddressDTO actual) {
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getPostOffice(), actual.getPostOffice());
    }
}
