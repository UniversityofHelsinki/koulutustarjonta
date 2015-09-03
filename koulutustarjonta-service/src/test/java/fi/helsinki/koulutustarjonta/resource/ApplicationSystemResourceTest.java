package fi.helsinki.koulutustarjonta.resource;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.dao.ApplicationSystemDAO;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.dto.ApplicationPeriodDTO;
import fi.helsinki.koulutustarjonta.dto.ApplicationSystemDTO;
import fi.helsinki.koulutustarjonta.dto.SeasonDTO;
import fi.helsinki.koulutustarjonta.mapping.ApplicationSystemModelMapper;
import fi.helsinki.koulutustarjonta.test.Fixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemResourceTest {

    private static final String AS_OID = "as_oid";
    private static final ApplicationSystemDAO dao = mock(ApplicationSystemDAO.class);
    private final ApplicationSystem applicationSystem = Fixture.applicationSystemWithApplicationForm(AS_OID);
    private final ModelMapper modelMapper = new ApplicationSystemModelMapper();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ApplicationSystemResource(dao))
            .build();

    @Before
    public void setup() throws ResourceNotFound {
        when(dao.findByOid(eq(AS_OID))).thenReturn(applicationSystem);
        when(dao.findAll()).thenReturn(Lists.newArrayList(applicationSystem));
    }

    @Test
    public void testGetApplicationSystem() throws ResourceNotFound {
        ApplicationSystemDTO response = resources.client().resource(String.format("/haku/%s", AS_OID))
                .get(ApplicationSystemDTO.class);
        ApplicationSystemDTO expected = modelMapper.map(applicationSystem, ApplicationSystemDTO.class);
        applicationSystemDTOsEqual(expected, response);
        verify(dao).findByOid(AS_OID);
    }

    private void applicationSystemDTOsEqual(ApplicationSystemDTO expected, ApplicationSystemDTO actual) {
        assertEquals(expected.getOid(), actual.getOid());
        DTOTestUtil.i18NDTOsEqual(expected.getName(), actual.getName());
        DTOTestUtil.i18NDTOsEqual(expected.getApplicationMethod(), actual.getApplicationMethod());
        assertEquals(expected.getApplicationYear(), actual.getApplicationYear());
        seasonDTOsEqual(expected.getApplicationSeason(), actual.getApplicationSeason());
        assertEquals(expected.getEducationStartYear(), actual.getEducationStartYear());
        assertNotNull(actual.getEducationStartSeason());
        seasonDTOsEqual(expected.getEducationStartSeason(), actual.getEducationStartSeason());
        DTOTestUtil.i18NDTOsEqual(expected.getFormUrl(), actual.getFormUrl());
        ApplicationPeriodDTO expectedAP = expected.getApplicationPeriods().get(0);
        ApplicationPeriodDTO actualAP = actual.getApplicationPeriods().get(0);
        assertNotNull(actualAP);
        applicationPeriodDTOsEqual(expectedAP, actualAP);
        assertEquals(expected.getTranslations().size(), actual.getTranslations().size());
    }

    private void applicationPeriodDTOsEqual(ApplicationPeriodDTO expected, ApplicationPeriodDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        DTOTestUtil.i18NDTOsEqual(expected.getName(), actual.getName());
        assertEquals(expected.getStarts(), actual.getStarts());
        assertEquals(expected.getEnds(), actual.getEnds());
    }

    private void seasonDTOsEqual(SeasonDTO expected, SeasonDTO actual) {
        assertEquals(expected.getValue(), actual.getValue());
        DTOTestUtil.i18NDTOsEqual(expected.getName(), actual.getName());
    }

}
