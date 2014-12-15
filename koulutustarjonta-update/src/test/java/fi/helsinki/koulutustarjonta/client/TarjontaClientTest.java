package fi.helsinki.koulutustarjonta.client;

import com.google.common.collect.Maps;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Hannu Lyytikainen
 */
@Ignore
public class TarjontaClientTest {


    TarjontaClient client;


    @Before
    public void init() {

        Map<String, Object> result = Maps.newHashMap();
        result.put("foo", "bar");

        WebResource koulutusResource = mock(WebResource.class);
        when(koulutusResource.path(anyString())).thenReturn(koulutusResource);
        when(koulutusResource.get((GenericType<Object>) any())).thenReturn(result);

        client = new TarjontaClient(koulutusResource, null, null, null, null, null);

    }


    @Test
    public void testGetLearningOpportunity() {
        client.getLearningOpportunity("1.2.3");
    }
}

