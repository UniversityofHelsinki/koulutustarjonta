package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Hannu Lyytikainen
 */
public class UpdaterTest {


    Updater updater;
    TarjontaClient client;

    @Before
    public void init() {
        client = mock(TarjontaClient.class);
        updater = new Updater(client);
    }

    @Test
    public void testUpdate() {
        updater.update();
        verify(client, times(1)).getDegree(anyString());
    }
}
