package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
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
        JsonNode ret = mock(JsonNode.class);
        List<String> ignoreList = Lists.newArrayList("Avoin yliopisto","Open University","Öppna universitetet");

        WebResource learningOpportunityResource = mock(WebResource.class);
        when(learningOpportunityResource.path(anyString())).thenReturn(learningOpportunityResource);
        when(learningOpportunityResource.get((GenericType<Object>) any())).thenReturn(ret);

        WebResource applicationOptionResource = mock(WebResource.class);
        when(applicationOptionResource.queryParam(anyString(), anyString())).thenReturn(applicationOptionResource);
        when(applicationOptionResource.path(anyString())).thenReturn(applicationOptionResource);
        JsonNode mock = mock(JsonNode.class);
        JsonNode mock2 = mock(JsonNode.class);


        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> valuemap = new HashMap<>();
        valuemap.put("nimi", ImmutableMap.of("fi", "Avoin yliopisto", "sv", "Öppna universitetet", "en", "Open university"));
        JsonNode searchResult = mapper.valueToTree(valuemap);
        when(mock.get("result")).thenReturn(mock2);
        when(mock2.get("tulokset")).thenReturn(searchResult);
        when(applicationOptionResource.get((GenericType<Object>) any())).thenReturn(mock);

        client = new TarjontaClient(learningOpportunityResource, applicationOptionResource, mock(WebResource.class), mock(WebResource.class), null, null, ignoreList);

    }


    @Test
    public void testGetLearningOpportunity() {
        client.getLearningOpportunity("1.2.3");
    }

    @Test
    public void getFilteredLearningOpportunity() {
        List<String> applicationOptionOidsByProvider = client.getApplicationOptionOidsByProvider("1.2.246.562.10.74168867104");
        assertTrue("Should return empty list for filter",applicationOptionOidsByProvider.isEmpty());

    }
}

