package fi.helsinki.koulutustarjonta.job;

import com.xeiam.sundial.SundialJobScheduler;
import com.xeiam.sundial.ee.SundialInitializerListener;
import fi.helsinki.koulutustarjonta.core.Updater;
import org.quartz.*;
import org.quartz.exceptions.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import java.text.ParseException;

/**
 * @author Hannu Lyytikainen
 */
public class UpdateCronlInitializerListener extends SundialInitializerListener {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateCronlInitializerListener.class);

    private final Updater updater;
    private final String cronExpression;

    public UpdateCronlInitializerListener(Updater updater, String cronExpression) {
        this.updater = updater;
        this.cronExpression = cronExpression;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
        SundialJobScheduler.getServletContext().setAttribute("updater", this.updater);

        JobKey jobKey = new JobKey(UpdateJob.NAME);
        JobDetail jobDetail = JobBuilder.newJob(UpdateJob.class).withIdentity(jobKey).build();

        Trigger trigger = null;
        try {
            trigger = TriggerBuilder.newTrigger().
                    withIdentity(new TriggerKey("updateTrigger", "triggerGroup"))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
        } catch (ParseException e) {
            LOG.error(String.format("Could not parse cron expression %s", cronExpression));
            e.printStackTrace();
        }

        try {
            SundialJobScheduler.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error("Could not schedule update job");
        }
    }
}
