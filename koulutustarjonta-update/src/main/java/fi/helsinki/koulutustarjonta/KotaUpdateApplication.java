package fi.helsinki.koulutustarjonta;

import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.config.KotaUpdateConfiguration;
import fi.helsinki.koulutustarjonta.core.Updater;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.dao.util.OracleStatementBuilderFactory;
import fi.helsinki.koulutustarjonta.resource.UpdateResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.TimeZone;

/**
 * @author Hannu Lyytikainen
 */
public class KotaUpdateApplication extends Application<KotaUpdateConfiguration> {

    public static void main(String[] args) throws Exception {
        new KotaUpdateApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KotaUpdateConfiguration> bootstrap) {
    }

    @Override
    public void run(KotaUpdateConfiguration configuration, Environment environment) throws Exception {
        TimeZone tz = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(tz);
        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(environment, configuration.getDataSourceFactory(), "oracle");
        dbi.setStatementBuilderFactory(new OracleStatementBuilderFactory());
        final LearningOpportunityJDBI jdbi = dbi.onDemand(LearningOpportunityJDBI.class);
        final LearningOpportunityDAO learningOpportunityDAO = new LearningOpportunityDAO(jdbi);
        final TarjontaClient tarjontaClient = configuration.getTarjontaClientFactory().build(environment);
        final Updater updater = new Updater(tarjontaClient, learningOpportunityDAO);
        final UpdateResource updateResource = new UpdateResource(updater);
        environment.jersey().register(updateResource);

    }
}
