package fi.helsinki.koulutustarjonta;

import fi.helsinki.koulutustarjonta.config.KotaQueryConfig;
import fi.helsinki.koulutustarjonta.resource.LearningOpportunityResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Hannu Lyytikainen
 */
public class KotaQueryApplication extends Application<KotaQueryConfig> {

    public static void main(String[] args) throws Exception {
        new KotaQueryApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KotaQueryConfig> bootstrap) {

    }

    @Override
    public void run(KotaQueryConfig configuration, Environment environment) throws Exception {
        final LearningOpportunityResource lor = new LearningOpportunityResource();
        environment.jersey().register(lor);
    }
}
