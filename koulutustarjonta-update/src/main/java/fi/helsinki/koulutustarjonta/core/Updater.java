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

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;


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
        //Get all organizations under the defined parentOid, there are also other organizations that are not
        //under this organization
        List<String> organizationOids = tryToGetResource("1.2.246.562.10.39218317368", organisaatioClient :: resolveFacultyOids);
        organizationOids.forEach(organizationOid -> {
            try {
                handleOrganization(organizationOid);
            } catch (ResourceException resourceException) {
                LOG.error("Error handling organization " + organizationOid, resourceException);
                result.addError(String.format("Failed to get resource %s with oid %s", resourceException.getClazz(),
                        resourceException.getOid()));
            } catch (Exception exception) {
                LOG.error("Error handling organization " + organizationOid, exception);
                result.addError(String.format("Failed to get resource organization with oid %s. Exception was: %s",
                        organizationOid, exception));
            }
        });
    }

    private void handleOrganization(String organizationOid) {
        // Get learning opportunities (koulutus) by organization
        List<String> learningOpportunityOids = tryToGetResource(organizationOid, tarjontaClient :: getLearningOpportunityOidsByProvider);

        //Get learning opportunities with wrong education level and remove them from the list
        List<String> learningOpportunityOidsWithWrongEducationLevel = tryToGetResource(learningOpportunityOids, tarjontaClient :: getLearningOpportunityOidsWithWrongEducationLevel);
        LOG.info("Learning opportunities with wrong education level: " + learningOpportunityOidsWithWrongEducationLevel);
        learningOpportunityOids.removeIf(oid -> learningOpportunityOidsWithWrongEducationLevel.contains(oid));

        LOG.debug(String.format("Found %d learning opportunities for organization %s", learningOpportunityOids.size(), organizationOid));

        // Get application options (hakukohde) by organization and remove those related to removed learning opportunities
        List<String> applicationOptionOids = tryToGetResource(organizationOid, tarjontaClient :: getApplicationOptionOidsByProvider);
        applicationOptionOids = tarjontaClient.removeApplicationOptionOidsRelatedToLearningOpportunitiesWithWrongEducationLevel(applicationOptionOids, learningOpportunityOidsWithWrongEducationLevel);

        LOG.debug(String.format("Found %d application options for organization %s", applicationOptionOids.size(), organizationOid));

        // Below, data is saved to the database. The order matters, because of the dependencies
        // in the SQL data model
        if (learningOpportunityOids.size() > 0 || applicationOptionOids.size() > 0) {
            saveOrganization(organizationOid);
            saveApplicationOptions(applicationOptionOids);
            learningOpportunityDAO.removeOutdatedLearningOpportunities();
            applicationSystemDAO.removeOutdatedApplicationSystems();
            saveLearningOpportunities(learningOpportunityOids);
        }
    }

    private void saveOrganization(String organizationOid) {
            Organization organization = tryToGetResource(organizationOid, organisaatioClient :: getOrganization);
            organizationDAO.save(organization);
    }

    //Save learning opportunities if they are not outdated. Learning opportunity
    //corresponds to the 'koulutus' table in the database.
    private void saveLearningOpportunities(List<String> learningOpportunityOids) {
        learningOpportunityOids.forEach(loOid -> {
            LearningOpportunity learningOpportunity = tryToGetResource(loOid, tarjontaClient :: getLearningOpportunity);

            //Check that learning opportunity is not expired
            LocalDate currentDate = LocalDate.now();
            Integer startingYear = learningOpportunity.getStartYear();
            String startingSeason = learningOpportunity.getStartSeason().getEn();
            //Learning opportunity should not be shown after this date
            LocalDate expiryDate = getExpiryDate(startingYear, startingSeason);
            LOG.info("expiryDate: "+expiryDate.toString());
            if(currentDate.isAfter(expiryDate)) {
                LOG.info("Ignoring an old learning opportunity: "+loOid);
            }
            else{
                LOG.info("Saving learning opportunity: "+loOid);
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
            ApplicationOption ao = tryToGetResource(aoOid, tarjontaClient :: getApplicationOption);
            ApplicationSystem as = tryToGetResource(ao.getApplicationSystem(), tarjontaClient :: getApplicationSystem);

            //Check that application option is not expired
            LocalDate currentDate = LocalDate.now();
            Integer startingYear = as.getEducationStartYear();
            Season educationStartSeason = as.getEducationStartSeason();
            String startSeason = null;
            if (educationStartSeason != null) {
                startSeason = educationStartSeason.getValue();
            }
            //application option should not be shown after this date
            LocalDate expiryDate = getExpiryDate(startingYear, startSeason);

            if(currentDate.isAfter(expiryDate)) {
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

    private LocalDate getExpiryDate(int startingYear, String startingSeason){
        //NOTE: If you change the dates here, remember to change them also in
        //.sql.stg files both for application system and learning opportunity
        //TODO: change it so that the value is read from configuration file
        if("Spring".equals(startingSeason)){
            //Spring learning opportunities and application options expire in February
            return LocalDate.of(startingYear, 2, 1);
        }
        //Expiry date for autumn educations
        else if("Autumn".equals(startingSeason)){
            return LocalDate.of(startingYear, 10, 28);
        }
        else{
            LOG.warn("Missing starting season, defaulting to Autumn.");
            return LocalDate.of(startingYear, 10, 28);
        }
    }

    // Tries to get a resource of type T using the function specified as a parameter.
    // The functionParam is in most cases the oid  
    public static <T, P> T tryToGetResource(P functionParam, Function<P, T> function) {
        int maxTries = 3;
        int tries = 1;
        // We try to handle each organization again in case of failures. This was implemented, because of
        // seemingly random errors possiibly related to network connection issues. There is a 30 second pause
        // between each try
        while (tries <= maxTries) {
            try {
                T returnedResource = function.apply(functionParam);
                if (returnedResource != null) {
                    return returnedResource;
                } else {
                    LOG.error("Getting resource returned null. Oid: " + functionParam);
                    throw new NullPointerException("Resource returned a null value");
                }
            }
            catch (Exception exception) {
                tries++;
                if (tries > maxTries) {
                    LOG.error("Error getting resource with oid: " + functionParam, exception);
                    throw exception;
                }
                else {
                    LOG.warn("Trying again after waiting. Error getting resource oid: " + functionParam +
                            " Exception was: " + exception);
                    try{
                        Thread.sleep(tries*10000); // 10 seconds * number of tries
                    }
                    catch (InterruptedException e){
                        LOG.error(e.getMessage());
                    }
                }
            }
        }
        //This situation should not happen, because the while loop should cover all situations
        LOG.error("Error getting resource with oid: " + functionParam);
        return null;
    }
}
