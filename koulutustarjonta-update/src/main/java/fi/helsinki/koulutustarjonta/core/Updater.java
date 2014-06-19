package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    final TarjontaClient tarjontaClient;
    final LearningOpportunityDAO dao;

    public Updater(TarjontaClient tarjontaClient, LearningOpportunityDAO dao) {
        this.tarjontaClient = tarjontaClient;
        this.dao = dao;
    }

    public void update() {
        LOG.debug("Update data");
        LearningOpportunity learningOpportunity = tarjontaClient.getLearningOpportunity("1.2.246.562.17.17939899864");

        int affected = dao.update(learningOpportunity);
        if (affected == 0) {
            dao.insert(learningOpportunity);
        }

        LOG.debug("Update finished");
    }
}
