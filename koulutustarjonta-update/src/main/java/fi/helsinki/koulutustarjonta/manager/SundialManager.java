package fi.helsinki.koulutustarjonta.manager;

import fi.helsinki.koulutustarjonta.config.SundialConfiguration;
import fi.helsinki.koulutustarjonta.core.Updater;
import fi.helsinki.koulutustarjonta.job.UpdateCronlInitializerListener;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

/**
 * @author Hannu Lyytikainen
 */
public class SundialManager implements Managed {

    private final SundialConfiguration sundialConfiguration;
    private final Environment environment;
    private final Updater updater;
    private final String updateCron;

    public SundialManager(SundialConfiguration sundialConfiguration, Environment environment, Updater updater, String updateCron) {
        this.sundialConfiguration = sundialConfiguration;
        this.environment = environment;
        this.updater = updater;
        this.updateCron = updateCron;
    }

    @Override
    public void start() throws Exception {

        // this sets up Sundial with all the default values
        environment.servlets().addServletListeners(new UpdateCronlInitializerListener(this.updater, this.updateCron));

        // here we can override the defaults
        if (sundialConfiguration.getThreadPoolSize() != null) {
            environment.servlets().setInitParameter("thread-pool-size", sundialConfiguration.getThreadPoolSize());
        }
        if (sundialConfiguration.getPerformShutdown() != null) {
            environment.servlets().setInitParameter("shutdown-on-unload", sundialConfiguration.getPerformShutdown());
        }
        if (sundialConfiguration.getWaitOnShutdown() != null) {
            environment.servlets().setInitParameter("wait-on-shutdown", sundialConfiguration.getWaitOnShutdown());
        }
        if (sundialConfiguration.getStartDelay() != null) {
            environment.servlets().setInitParameter("start-delay-seconds", sundialConfiguration.getStartDelay());
        }
        if (sundialConfiguration.getStartOnLoad() != null) {
            environment.servlets().setInitParameter("start-scheduler-on-load", sundialConfiguration.getStartOnLoad());
        }
        if (sundialConfiguration.getGlobalLockOnLoad() != null) {
            environment.servlets().setInitParameter("global-lock-on-load", sundialConfiguration.getGlobalLockOnLoad());
        }

    }

    @Override
    public void stop() throws Exception {

    }
}
