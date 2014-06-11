package fi.helsinki.koulutustarjonta.client;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.util.Map;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClient {

    private WebResource koulutusResource;


    public TarjontaClient(WebResource koulutusResource) {
        this.koulutusResource = koulutusResource;
    }

    public void getDegree(String oid) {
        Map<String, Object> koulutus = koulutusResource.path(oid).get(new GenericType<Map<String, Object>>() {});

        System.out.println("");
    }

}
