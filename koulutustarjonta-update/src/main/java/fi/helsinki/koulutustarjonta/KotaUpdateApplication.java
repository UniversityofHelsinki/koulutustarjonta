package fi.helsinki.koulutustarjonta;

import fi.helsinki.koulutustarjonta.cli.UpdateCommand;
import fi.helsinki.koulutustarjonta.config.KotaUpdateConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Hannu Lyytikainen
 */
public class KotaUpdateApplication extends Application<KotaUpdateConfiguration> {

    public static void main(String[] args) throws Exception {
        new KotaUpdateApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KotaUpdateConfiguration> bootstrap) {
        bootstrap.addCommand(new UpdateCommand(this));
    }

    @Override
    public void run(KotaUpdateConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().disable();
    }
}
