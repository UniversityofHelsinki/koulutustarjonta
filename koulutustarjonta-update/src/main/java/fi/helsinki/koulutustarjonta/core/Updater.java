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

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
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
            learningOpportunityDAO.removeOutdatedLearningOpportunities();
            applicationSystemDAO.removeOutdatedApplicationSystems();
            saveLearningOpportunities(learningOpportunityOids);
        }
    }

    private void saveOrganization(String organizationOid) throws DataUpdateException {
        Organization organization = tryToGetResource(organizationOid, Organization.class);
        organizationDAO.save(organization);
    }

    //Save learning opportunities if they are not outdated. Learning opportunity
    //corresponds to the 'koulutus' table in the database.
    private void saveLearningOpportunities(List<String> learningOpportunityOids) {
        learningOpportunityOids.forEach(loOid -> {
            LearningOpportunity learningOpportunity = tryToGetResource(loOid, LearningOpportunity.class);
            YearMonth currentYearMonth = YearMonth.now();
            Integer startingYear = learningOpportunity.getStartYear();
            String startingSeason = learningOpportunity.getStartSeason().getEn();
            YearMonth startingYearMonth = getStartingYearMonth(startingYear, startingSeason);

            if(currentYearMonth.isAfter(startingYearMonth)) {
                LOG.info("Ignoring an old learning opportunity: "+loOid);
            }
            else{
                learningOpportunityDAO.save(learningOpportunity);
            }
        });
    }

    //Save application option and a corresponding application system
    //if the application system is not outdated.
    //Application option corresponds to 'hakukohde' table and
    //Application system corresponds to 'haku' table
    private void saveApplicationOptions(List<String> applicationOptionOids) {
        applicationOptionOids.forEach(aoOid -> {
            ApplicationOption ao = tryToGetResource(aoOid, ApplicationOption.class);
            ApplicationSystem as = tryToGetResource(ao.getApplicationSystem(), ApplicationSystem.class);

            YearMonth currentYearMonth = YearMonth.now();
            Integer startingYear = as.getEducationStartYear();
            String startingSeason = as.getEducationStartSeason().getValue();
            YearMonth startingYearMonth = getStartingYearMonth(startingYear, startingSeason);

            if(currentYearMonth.isAfter(startingYearMonth)) {
                LOG.info("Ignoring an old application system: "+as.getOid());
            }
            else{
                handleAoPeriod(ao, as);
                applicationSystemDAO.save(as);
                applicationOptionDAO.save(ao);
            }

        });
    }

    private void handleAoPeriod(ApplicationOption ao, ApplicationSystem as) {
        if (ao.getApplicationPeriodId() == null) {
            ao.setApplicationPeriodId(as.getApplicationPeriods().get(0).getId());
        }
    }

    private YearMonth getStartingYearMonth(Integer startingYear, String startingSeason){
        if("Spring".equals(startingSeason)){
            return YearMonth.of(startingYear, 1);
        }
        //Autumn courses start in September, but they can be deleted in November.
        //So I set the starting date to be in October, because of the system that
        //checks for deletion
        else if("Autumn".equals(startingSeason)){
            return YearMonth.of(startingYear, 10);
        }
        else{
            LOG.warn("Missing starting season, defaulting to Autumn.");
            return YearMonth.of(startingYear, 10);
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
