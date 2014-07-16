package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
@Ignore
public class UpdaterTest {


    Updater updater;
    TarjontaClient client;

    @Before
    public void init() {
        client = mock(TarjontaClient.class);
        LearningOpportunityJDBI dao = mock(LearningOpportunityJDBI.class);
        LearningOpportunityDAO loService = new LearningOpportunityDAO(dao);
        updater = new Updater(client, loService, null);
    }

    @Test
    public void testUpdate() {
        updater.update();
        verify(client, times(1)).getLearningOpportunity(anyString());
    }
}
