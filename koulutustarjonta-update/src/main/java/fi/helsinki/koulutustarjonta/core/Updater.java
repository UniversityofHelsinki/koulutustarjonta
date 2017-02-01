package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.OrganisaatioClient;
import fi.helsinki.koulutustarjonta.client.TarjontaClient;
import fi.helsinki.koulutustarjonta.core.converter.UpdateResultConverter;
import fi.helsinki.koulutustarjonta.dao.*;
import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.domain.*;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import fi.helsinki.koulutustarjonta.exception.ResourceException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            LOG.error("Error handling all organizations", exception);
        }

        UpdateResult updateResult = updateResultConverter.toUpdateResult(result);
        LOG.info(updateResult.toString());
        updateResultDAO.save(updateResult);

        LOG.debug(String.format("Data update completed, update took %d seconds", (System.currentTimeMillis() - started) / 1000));
    }

    private void handleAllOrganizations(Result result) {
        List<String> organizationOids = organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368");
        organizationOids.forEach(organizationOid -> {
            try {
                handleOrganization(organizationOid);
            } catch (ResourceException resourceException) {
                LOG.error("Error handling organization " + organizationOid, resourceException);
                result.addError(String.format("Failed to get resource %s with oid %s", resourceException.getClazz(),
                        resourceException.getOid()));
            } catch (Exception exception) {
                LOG.error("Error handling organization " + organizationOid, exception);
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
        deleteObsoleteLearningOpportunities(learningOpportunityOids);

    }

    //Deletes learning opportunities, which Oid is not part of the list given as parameter
    private void deleteObsoleteLearningOpportunities(List<String> learningOpportunityOids) {
        List<String> toBeDeleted = new ArrayList<String>();
        //Convert to set for faster search
        Set<String> learningOpportunityOidsSet = new java.util.HashSet<String>(learningOpportunityOids);
        List<LearningOpportunity> allLearningOpportunities = this.learningOpportunityDAO.findAll();
        allLearningOpportunities.forEach(lo -> {
            if(!learningOpportunityOidsSet.contains(lo.getOid())){
                toBeDeleted.add(lo.getOid());
            }
        });
        //Also deletes all other tables that refer to the LearningOpportunity
        LOG.debug("Starting to delete obsolete Learning opportunities");
        toBeDeleted.forEach(currentOid -> {
            LOG.debug(String.format("Deleting Learning opportunity with Oid: %s", currentOid));
            learningOpportunityDAO.delete(currentOid);
        });
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
            LOG.error(String.format("Error getting resource %s with OID %s",clazz.toString(), oid), exception);
            throw new ResourceException(oid, clazz);
        }
    }
}
