package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Hannu Lyytikainen
 */
@Path("/hakukohde")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationOptionResource {

    private final ApplicationOptionDAO applicationOptionDAO;

    public ApplicationOptionResource(ApplicationOptionDAO applicationOptionDAO) {
        this.applicationOptionDAO = applicationOptionDAO;
    }

    @GET
    public String getApplicationOptions() {

        return "ok";
    }

    @GET
    @Path("/{oid}")
    public String getApplicationOption(@PathParam("oid") String oid) {
        ApplicationOption applicationOption = applicationOptionDAO.findByOid(oid);
        return "ok";
    }

}
