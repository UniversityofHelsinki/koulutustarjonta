package fi.helsinki.koulutustarjonta.cli;

import fi.helsinki.koulutustarjonta.KotaUpdateApplication;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.config.KotaUpdateConfiguration;
import fi.helsinki.koulutustarjonta.core.Updater;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

/**
 * @author Hannu Lyytikainen
 */
public class UpdateCommand extends EnvironmentCommand<KotaUpdateConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCommand.class);

    public UpdateCommand(KotaUpdateApplication application) {
        super(application, "update", "Runs education data update");
    }

    @Override
    protected void run(Environment environment, Namespace namespace,
                       KotaUpdateConfiguration configuration) throws Exception {
        LOGGER.info("Running update command");

        TimeZone tz = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(tz);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "oracle");
        final LearningOpportunityDAO dao = jdbi.onDemand(LearningOpportunityDAO.class);

        TarjontaClient tarjontaClient = configuration.getTarjontaClientFactory().build(environment);

        Updater updater = new Updater(tarjontaClient, dao);
        updater.update();


    }
}
