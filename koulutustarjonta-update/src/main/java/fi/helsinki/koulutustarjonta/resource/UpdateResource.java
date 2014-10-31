package fi.helsinki.koulutustarjonta.resource;

import com.xeiam.sundial.SundialJobScheduler;
import fi.helsinki.koulutustarjonta.core.Updater;
import fi.helsinki.koulutustarjonta.job.UpdateJob;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public String update() {
        if (SundialJobScheduler.isJobRunning(UpdateJob.NAME)) {
            return "Update is already running";

        }
        else {
            SundialJobScheduler.startJob(UpdateJob.NAME);
            return "Update started";
        }
    }
}
