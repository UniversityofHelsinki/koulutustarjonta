package fi.helsinki.koulutustarjonta.task;

import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;
import fi.helsinki.koulutustarjonta.job.UpdateJob;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

/**
 * @author Hannu Lyytikainen
 */
public class UpdateTask extends Task {

    public UpdateTask(String name) {
        super(name);
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        if (!SundialJobScheduler.isJobRunning(UpdateJob.NAME)) {
            SundialJobScheduler.startJob(UpdateJob.NAME);
        }
    }
}
