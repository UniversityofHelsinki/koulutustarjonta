package fi.helsinki.koulutustarjonta.resource;

import com.google.common.collect.Maps;
import fi.helsinki.koulutustarjonta.core.Updater;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author Hannu Lyytikainen
 */
@Path("update")
@Produces(MediaType.APPLICATION_JSON)
public class UpdateResource {

    final Updater updater;

    public UpdateResource(Updater updater) {
        this.updater = updater;
    }

    @GET
    public Map<String, String> update() {
        Map<String, String> status = Maps.newHashMap();
        try {
            updater.update();
            status.put("status", "ok");
        } catch (Exception e) {
            status.put("status", "error");
            status.put("cause", e.getMessage());
        }
        return status;
    }
}
