package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.core.converter.UpdateResultConverter;
import fi.helsinki.koulutustarjonta.dao.*;
import fi.helsinki.koulutustarjonta.domain.*;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import fi.helsinki.koulutustarjonta.exception.ResourceException;
import org.apache.commons.lang.exception.ExceptionUtils;
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
    final UpdateResultDAO updateResultDAO;
    final UpdateResultConverter updateResultConverter;

    public Updater(TarjontaClient tarjontaClient, OrganisaatioClient organisaatioClient, LearningOpportunityDAO learningOpportunityDAO,
                   ApplicationOptionDAO applicationOptionDAO, ApplicationSystemDAO applicationSystemDAO, OrganizationDAO organizationDAO,
                   UpdateResultDAO updateResultDAO, UpdateResultConverter updateResultConverter) {
        this.tarjontaClient = tarjontaClient;
        this.organisaatioClient = organisaatioClient;
        this.learningOpportunityDAO = learningOpportunityDAO;
        this.applicationOptionDAO = applicationOptionDAO;
        this.applicationSystemDAO = applicationSystemDAO;
        this.organizationDAO = organizationDAO;
        this.updateResultDAO = updateResultDAO;
        this.updateResultConverter = updateResultConverter;
    }

    public void update() {
        LOG.debug("Update data");
        long started = System.currentTimeMillis();
        Result result = new Result(started);

        try {
            handleAllOrganizations(result);
        } catch (Exception exception) {
            result.addError(ExceptionUtils.getStackTrace(exception));
        }

        UpdateResult updateResult = updateResultConverter.toUpdateResult(result);
        updateResultDAO.save(updateResult);

        LOG.debug(String.format("Data update completed, update took %d seconds", (System.currentTimeMillis() - started) / 1000));
    }

    private void handleAllOrganizations(Result result) {
        List<String> organizationOids = organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368");
        organizationOids.forEach(organizationOid -> {
            try {
                handleOrganization(organizationOid);
            } catch (ResourceException resourceException) {
                result.addError(String.format("Failed to get resource %s with oid %s", resourceException.getClazz(),
                        resourceException.getOid()));
            } catch (Exception exception) {
                result.addError(ExceptionUtils.getFullStackTrace(exception));
            }
        });
    }

    private void handleOrganization(String organizationOid) throws DataUpdateException {
        List<String> learningOpportunityOids = tarjontaClient.getLearningOpportunityOidsByProvider(organizationOid);
        LOG.debug(String.format("Found %d learning opportunities for organization %s", learningOpportunityOids.size(), organizationOid));
        List<String> applicationOptionOids = tarjontaClient.getApplicationOptionOidsByProvider(organizationOid);
        LOG.debug(String.format("Found %d application options for organization %s", applicationOptionOids.size(), organizationOid));
        if (learningOpportunityOids.size() > 0 || applicationOptionOids.size() > 0) {
            saveOrganization(organizationOid);
            saveApplicationOptions(applicationOptionOids);
            saveLearningOpportunities(learningOpportunityOids);
        }
    }

    private void saveOrganization(String organizationOid) throws DataUpdateException {
        Organization organization = tryToGetResource(organizationOid, Organization.class);
        organizationDAO.save(organization);
    }

    private void saveLearningOpportunities(List<String> learningOpportunityOids) {
        learningOpportunityOids.forEach(loOid -> {
            LearningOpportunity learningOpportunity = tryToGetResource(loOid, LearningOpportunity.class);
            learningOpportunityDAO.save(learningOpportunity);
        });
    }

    private void saveApplicationOptions(List<String> applicationOptionOids) {
        applicationOptionOids.forEach(aoOid -> {
            ApplicationOption ao = tryToGetResource(aoOid, ApplicationOption.class);
            ApplicationSystem as = tryToGetResource(ao.getApplicationSystem(), ApplicationSystem.class);

            handleAoPeriod(ao, as);

            applicationSystemDAO.save(as);
            applicationOptionDAO.save(ao);
        });
    }

    private void handleAoPeriod(ApplicationOption ao, ApplicationSystem as) {
        if (ao.getApplicationPeriodId() == null) {
            ao.setApplicationPeriodId(as.getApplicationPeriods().get(0).getId());
        }
    }

    private <T> T tryToGetResource(String oid, Class<T> clazz) {
        try {
            if (clazz.equals(ApplicationOption.class)) {
                return clazz.cast(tarjontaClient.getApplicationOption(oid));
            } else if (clazz.equals(ApplicationSystem.class)) {
                return clazz.cast(tarjontaClient.getApplicationSystem(oid));
            } else if (clazz.equals(LearningOpportunity.class)) {
                return clazz.cast(tarjontaClient.getLearningOpportunity(oid));
            } else {
                return clazz.cast(organisaatioClient.getOrganization(oid));
            }
        } catch (Exception exception) {
            throw new ResourceException(oid, clazz);
        }
    }
}
