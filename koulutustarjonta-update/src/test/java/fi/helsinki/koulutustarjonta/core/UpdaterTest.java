package fi.helsinki.koulutustarjonta.core;

import com.sun.jersey.api.client.ClientHandlerException;
import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.core.converter.UpdateResultConverter;
import fi.helsinki.koulutustarjonta.dao.*;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static fi.helsinki.koulutustarjonta.matchers.UpdateResultMatchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UpdaterTest {

    @Mock
    private TarjontaClient tarjontaClient;

    @Mock
    private OrganisaatioClient organisaatioClient;

    @Mock
    private LearningOpportunityDAO learningOpportunityDAO;

    @Mock
    private ApplicationOptionDAO applicationOptionDAO;

    @Mock
    private ApplicationSystemDAO applicationSystemDAO;

    @Mock
    private OrganizationDAO organizationDAO;

    @Mock
    private UpdateResultDAO updateResultDAO;

    @Mock
    private UpdateResultConverter updateResultConverter;

    @InjectMocks
    private Updater updater;

    @Before
    public void before() {
        when(updateResultConverter.toUpdateResult(anyObject())).thenCallRealMethod();
    }

    @Test
    public void thatUpdateResultIsSaved() {
        updater.update();
        verify(updateResultDAO, times(1)).save(argThat(allOf(errorsEqual("[]"), stateEquals(UpdateResult.State.OK))));
    }

    @Test
    public void thatGetLearningOpportunityOidsByProviderErrorsAreSaved() {
        when(organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368")).thenReturn(Arrays.asList("1.2.3"));
        when(tarjontaClient.getLearningOpportunityOidsByProvider("1.2.3")).thenThrow(new ClientHandlerException());

        updater.update();

        verify(updateResultDAO, times(1)).save(argThat(allOf(errorsContain(
                "[\"com.sun.jersey.api.client.ClientHandlerException"), stateEquals(UpdateResult.State.ERROR))));
    }

    @Test
    public void thatResourceExceptionErrorsAreSaved() throws DataUpdateException {
        when(organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368")).thenReturn(Arrays.asList("1.2.3"));
        when(tarjontaClient.getLearningOpportunityOidsByProvider("1.2.3")).thenReturn(Arrays.asList("4.5.6"));
        when(organisaatioClient.getOrganization("1.2.3")).thenThrow(new ClientHandlerException());

        updater.update();

        verify(updateResultDAO, times(1)).save(argThat(allOf(errorsEqual(
                        "[\"Failed to get resource class fi.helsinki.koulutustarjonta.domain.Organization with oid 1.2.3\"]"),
                stateEquals(UpdateResult.State.ERROR))));
    }

}
