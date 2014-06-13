package fi.helsinki.koulutustarjonta.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@Path("/koulutus")
@Produces(MediaType.APPLICATION_JSON)
public class LearningOpportunityResource {

    @GET
    public List getLearningOpportunities() {
        return new ArrayList();
    }

}
