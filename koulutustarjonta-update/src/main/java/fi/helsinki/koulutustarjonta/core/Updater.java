package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.dao.ApplicationOptionDAO;
import fi.helsinki.koulutustarjonta.dao.ApplicationSystemDAO;
import fi.helsinki.koulutustarjonta.dao.LearningOpportunityDAO;
import fi.helsinki.koulutustarjonta.dao.OrganizationDAO;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    final OrganizationDAO organizationDAO;

    public Updater(TarjontaClient tarjontaClient, OrganisaatioClient organisaatioClient, LearningOpportunityDAO learningOpportunityDAO,
                   ApplicationOptionDAO applicationOptionDAO, ApplicationSystemDAO applicationSystemDAO, OrganizationDAO organizationDAO) {
        this.tarjontaClient = tarjontaClient;
        this.organisaatioClient = organisaatioClient;
        this.learningOpportunityDAO = learningOpportunityDAO;
        this.applicationOptionDAO = applicationOptionDAO;
        this.applicationSystemDAO = applicationSystemDAO;
        this.organizationDAO = organizationDAO;
    }

    public void update() {
        LOG.debug("Update data");
        List<String> organizationOids = organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368");
        organizationOids.forEach(organizationOid -> {
            Organization organization = null;
            List<String> learningOpportunityOids = tarjontaClient.getLearningOpportunityOidsByProvider(organizationOid);
            LOG.debug(String.format("Found %d learning opportunities for organization %s", learningOpportunityOids.size(), organizationOid));
            List<String> applicationOptionOids = tarjontaClient.getApplicationOptionOidsByProvider(organizationOid);
            LOG.debug(String.format("Found %d application options for organization %s", applicationOptionOids.size(), organizationOid));
            if (learningOpportunityOids.size() > 0 || applicationOptionOids.size() > 0) {
                try {
                    organization = organisaatioClient.getOrganization(organizationOid);
                    organizationDAO.save(organization);

                    applicationOptionOids.forEach(aoOid -> {
                        ApplicationOption ao = tarjontaClient.getApplicationOption(aoOid);
                        applicationSystemDAO.save(ao.getApplicationSystem());
                        applicationOptionDAO.save(ao);
                    });

                    learningOpportunityOids.forEach(loOid -> {
                        LearningOpportunity learningOpportunity = tarjontaClient.getLearningOpportunity(loOid);
                        learningOpportunityDAO.save(learningOpportunity);
                    });
                } catch (DataUpdateException e) {
                    LOG.warn(String.format("Could not retreive organization %s: %s", organizationOid, e.getMessage()));
                }
            }
        });
        LOG.debug("Data update completed");
    }
}
