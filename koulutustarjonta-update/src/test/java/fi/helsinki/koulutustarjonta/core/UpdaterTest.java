package fi.helsinki.koulutustarjonta.core;

import com.sun.jersey.api.client.ClientHandlerException;
import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.core.converter.UpdateResultConverter;
import fi.helsinki.koulutustarjonta.dao.*;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        verify(updateResultDAO, times(1)).save(argThat(new EmptyUpdateResultMatcher()));
    }

    @Test
    public void thatErrorsAreSaved() {
        when(organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368")).thenReturn(Arrays.asList("1.2.3"));
        when(tarjontaClient.getLearningOpportunityOidsByProvider("1.2.3")).thenThrow(new ClientHandlerException());

        updater.update();

        verify(updateResultDAO, times(1)).save(argThat(new OneErrorUpdateResultMatcher()));
    }

    private class EmptyUpdateResultMatcher extends ArgumentMatcher<UpdateResult> {

        @Override
        public boolean matches(Object argument) {
            UpdateResult updateResult = (UpdateResult) argument;
            return updateResult.getErrors().equals("[]") && updateResult.getState().equals(UpdateResult.State.OK);
        }
    }

    private class OneErrorUpdateResultMatcher extends ArgumentMatcher<UpdateResult> {

        @Override
        public boolean matches(Object argument) {
            UpdateResult updateResult = (UpdateResult) argument;
            return updateResult.getErrors().contains("[\"com.sun.jersey.api.client.ClientHandlerException");
        }
    }
}
