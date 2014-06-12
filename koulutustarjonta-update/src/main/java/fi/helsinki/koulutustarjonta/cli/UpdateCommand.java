package fi.helsinki.koulutustarjonta.cli;

import fi.helsinki.koulutustarjonta.KotaUpdateApplication;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.config.KotaUpdateConfiguration;
import fi.helsinki.koulutustarjonta.core.Updater;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


        TarjontaClient tarjontaClient = configuration.getTarjontaClientFactory().build(environment);

        Updater updater = new Updater(tarjontaClient);
        updater.update();


    }
}
