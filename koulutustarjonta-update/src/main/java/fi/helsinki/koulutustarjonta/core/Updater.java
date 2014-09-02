package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.ApplicationSystemDAO;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    final TarjontaClient tarjontaClient;
    final OrganisaatioClient organisaatioClient;
    final LearningOpportunityDAO learningOpportunityDAO;
    final ApplicationOptionDAO applicationOptionDAO;
    final ApplicationSystemDAO applicationSystemDAO;

    public Updater(TarjontaClient tarjontaClient, OrganisaatioClient organisaatioClient, LearningOpportunityDAO learningOpportunityDAO,
                   ApplicationOptionDAO applicationOptionDAO, ApplicationSystemDAO applicationSystemDAO) {
        this.tarjontaClient = tarjontaClient;
        this.organisaatioClient = organisaatioClient;
        this.learningOpportunityDAO = learningOpportunityDAO;
        this.applicationOptionDAO = applicationOptionDAO;
        this.applicationSystemDAO = applicationSystemDAO;
    }

    public void update() {
        LOG.debug("Update data");
        Organization organization = organisaatioClient.getOrganization("1.2.246.562.10.94639300915");
        LOG.debug("Organization update finished");
        LearningOpportunity learningOpportunity = tarjontaClient.getLearningOpportunity("1.2.246.562.17.17939899864");
        learningOpportunityDAO.save(learningOpportunity);
        LOG.debug("Learning opportunity update finished");
        LOG.debug("Update application options");
        ApplicationOption ao = tarjontaClient.getApplicationOption("1.2.246.562.20.92573201339");
        applicationSystemDAO.save(ao.getApplicationSystem());
        applicationOptionDAO.save(ao);
        LOG.debug("Application option update finished");
    }
}
