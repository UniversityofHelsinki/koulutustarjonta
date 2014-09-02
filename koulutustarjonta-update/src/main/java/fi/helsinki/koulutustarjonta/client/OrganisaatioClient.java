package fi.helsinki.koulutustarjonta.client;

import com.sun.jersey.api.client.WebResource;

/**
 * @author Hannu Lyytikainen
 */
public class OrganisaatioClient {

    private final WebResource organizationResource;

    public OrganisaatioClient(WebResource organizationResource) {
        this.organizationResource = organizationResource;
    }



}
