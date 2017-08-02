package fi.helsinki.koulutustarjonta;

import com.codahale.metrics.health.HealthCheck;
import fi.helsinki.koulutustarjonta.config.KoulutustarjontaServiceConfig;
import fi.helsinki.koulutustarjonta.dao.*;
import fi.helsinki.koulutustarjonta.dao.jdbi.*;
import fi.helsinki.koulutustarjonta.resource.*;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.TimeZone;

/**
 * @author Hannu Lyytikainen
 */
public class KoulutustarjontaServiceApplication extends Application<KoulutustarjontaServiceConfig> {

    public KoulutustarjontaServiceApplication() {
        TimeZone tz = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(tz);
    }

    public static void main(String[] args) throws Exception {
        new KoulutustarjontaServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KoulutustarjontaServiceConfig> bootstrap) {
    }

    @Override
    public void run(KoulutustarjontaServiceConfig configuration, Environment environment) throws Exception {

        final DBIFactory factory = new DBIFactory();
        DataSourceFactory database = configuration.getDatabase();

        // setCheckConnectionOnBorrow will make the reconnection possible when the database has been restarted
        // otherwise the database connection will fail
        database.setCheckConnectionOnBorrow(true);

        final DBI dbi = factory.build(environment, database, "oracle");

        final LearningOpportunityJDBI learningOpportunityJDBI = dbi.onDemand(LearningOpportunityJDBI.class);
        final LOContactJDBI loContactJDBI = dbi.onDemand(LOContactJDBI.class);
        final LearningOpportunityDAO learningOpportunityDAO = new LearningOpportunityDAO(learningOpportunityJDBI,loContactJDBI);
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

        final UpdateResultJDBI updateResultJDBI = dbi.onDemand(UpdateResultJDBI.class);
        final UpdateResultDAO updateResultDAO = new UpdateResultDAO(updateResultJDBI);
        final UpdateResultResource urr = new UpdateResultResource(updateResultDAO);

        environment.jersey().register(lor);
        environment.jersey().register(aor);
        environment.jersey().register(asr);
        environment.jersey().register(or);
        environment.jersey().register(urr);
        environment.jersey().register(new ResourceNotFoundExceptionMapper());
    }
}
