package fi.helsinki.koulutustarjonta.job;

import com.xeiam.sundial.Job;
import com.xeiam.sundial.SundialJobScheduler;
import com.xeiam.sundial.exceptions.JobInterruptException;
import fi.helsinki.koulutustarjonta.core.Updater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class UpdateJob extends Job {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateJob.class);

    public static final String NAME = "updateJob";

    @Override
    public void doRun() throws JobInterruptException {
        LOG.debug("Starting scheduled update job");
        Updater updater = (Updater) SundialJobScheduler.getServletContext().getAttribute("updater");
        updater.update();
    }
}
