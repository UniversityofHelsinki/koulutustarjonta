package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
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
    final ApplicationOptionDAO applicationOptionDAO;



    public Updater(TarjontaClient tarjontaClient, LearningOpportunityDAO dao, ApplicationOptionDAO applicationOptionDAO) {
        this.tarjontaClient = tarjontaClient;
        this.dao = dao;
        this.applicationOptionDAO = applicationOptionDAO;
    }

    public void update() {
        LOG.debug("Update data");
        LearningOpportunity learningOpportunity = tarjontaClient.getLearningOpportunity("1.2.246.562.17.17939899864");
        dao.save(learningOpportunity);
        LOG.debug("Learning opportunity update finished");
        LOG.debug("Update application options");
        ApplicationOption ao = tarjontaClient.getApplicationOption("1.2.246.562.20.92573201339");
        applicationOptionDAO.save(ao);
        LOG.debug("Application option update finished");
    }
}
