package fi.helsinki.koulutustarjonta;

import fi.helsinki.koulutustarjonta.config.KotaQueryConfig;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.resource.LearningOpportunityResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.TimeZone;

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

        TimeZone tz = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(tz);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "oracle");
        final LearningOpportunityDAO dao = jdbi.onDemand(LearningOpportunityDAO.class);
        final LearningOpportunityResource lor = new LearningOpportunityResource(dao);
        environment.jersey().register(lor);
    }
}
