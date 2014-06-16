package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class UpdaterTest {


    Updater updater;
    TarjontaClient client;

    @Before
    public void init() {
        client = mock(TarjontaClient.class);
        LearningOpportunityDAO dao = mock(LearningOpportunityDAO.class);
        updater = new Updater(client, dao);
    }

    @Test
    public void testUpdate() {
        updater.update();
        verify(client, times(1)).getLearningOpportunity(anyString());
    }
}
