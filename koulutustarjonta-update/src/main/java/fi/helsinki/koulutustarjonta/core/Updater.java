package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    final TarjontaClient tarjontaClient;
    final LearningOpportunityDAO service;

    public Updater(TarjontaClient tarjontaClient, LearningOpportunityDAO service) {
        this.tarjontaClient = tarjontaClient;
        this.service = service;
    }

    public void update() {
        LOG.debug("Update data");
        LearningOpportunity learningOpportunity = tarjontaClient.getLearningOpportunity("1.2.246.562.17.17939899864");

        service.save(learningOpportunity);

        LOG.debug("Update finished");
    }
}
