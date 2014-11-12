package fi.helsinki.koulutustarjonta;

import fi.helsinki.koulutustarjonta.config.KoulutustarjontaServiceConfig;
import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.ApplicationSystemDAO;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.dao.OrganizationDAO;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationSystemJDBI;
import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.dao.jdbi.OrganizationJDBI;
import fi.helsinki.koulutustarjonta.resource.ApplicationOptionResource;
import fi.helsinki.koulutustarjonta.resource.ApplicationSystemResource;
import fi.helsinki.koulutustarjonta.resource.LearningOpportunityResource;
import fi.helsinki.koulutustarjonta.resource.OrganizationResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.TimeZone;

/**
 * @author Hannu Lyytikainen
 */
public class KoulutustarjontaServiceApplication extends Application<KoulutustarjontaServiceConfig> {

    public static void main(String[] args) throws Exception {
        new KoulutustarjontaServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KoulutustarjontaServiceConfig> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<KoulutustarjontaServiceConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(KoulutustarjontaServiceConfig configuration) {
                return configuration.getDatabase();
            }
        });

    }

    @Override
    public void run(KoulutustarjontaServiceConfig configuration, Environment environment) throws Exception {
        TimeZone tz = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(tz);

        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(environment, configuration.getDatabase(), "oracle");

        final LearningOpportunityJDBI learningOpportunityJDBI = dbi.onDemand(LearningOpportunityJDBI.class);
        final LearningOpportunityDAO learningOpportunityDAO = new LearningOpportunityDAO(learningOpportunityJDBI);
        final LearningOpportunityResource lor = new LearningOpportunityResource(learningOpportunityDAO, configuration.getApiEndpoint());
        final ApplicationOptionJDBI applicationOptionJDBI = dbi.onDemand(ApplicationOptionJDBI.class);
        final ApplicationOptionDAO applicationOptionDAO = new ApplicationOptionDAO(applicationOptionJDBI);
        final ApplicationOptionResource aor = new ApplicationOptionResource(applicationOptionDAO, configuration.getApiEndpoint());
        final ApplicationSystemJDBI applicationSystemJDBI = dbi.onDemand(ApplicationSystemJDBI.class);
        final ApplicationSystemDAO applicationSystemDAO = new ApplicationSystemDAO(applicationSystemJDBI);
        final ApplicationSystemResource asr = new ApplicationSystemResource(applicationSystemDAO);
        final OrganizationJDBI organizationJDBI = dbi.onDemand(OrganizationJDBI.class);
        final OrganizationDAO organizationDAO = new OrganizationDAO(organizationJDBI);
        final OrganizationResource or = new OrganizationResource(organizationDAO);
        environment.jersey().register(lor);
        environment.jersey().register(aor);
        environment.jersey().register(asr);
        environment.jersey().register(or);
    }
}