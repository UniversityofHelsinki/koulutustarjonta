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
        List<String> organizationOids = organisaatioClient.resolveFacultyOids("1.2.246.562.10.39218317368");
        organizationOids.forEach(organizationOid -> {
            int maxTries = 3;
            int tries = 1;
            // We try to handle each organization again in case of failures. This was implemented, because of
            // seemingly random errors possiibly related to network connection issues. There is a 30 second pause
            // between each try
            while (tries <= maxTries) {
                try {
                    handleOrganization(organizationOid);
                    break; // No retries if the call is succesful
                } catch (ResourceException resourceException) {
                    tries++;
                    if (tries > maxTries) {
                        LOG.error("Error handling organization " + organizationOid, resourceException);
                        result.addError(String.format("Failed to get resource %s with oid %s", resourceException.getClazz(),
                                resourceException.getOid()));
                        try{
                            Thread.sleep(30000);
                        }
                        catch (InterruptedException e){
                            LOG.error(e.getMessage());
                        }
                    }
                    else {
                        LOG.warn("Handling organization failed, trying again. Organization oid: " + organizationOid +
                                " Exception was: " + resourceException);
                    }

                } catch (Exception exception) {
                    tries++;
                    if (tries > maxTries) {
                        LOG.error("Error handling organization " + organizationOid, exception);
                        result.addError(ExceptionUtils.getFullStackTrace(exception));
                        try{
                            Thread.sleep(30000);
                        }
                        catch (InterruptedException e){
                            LOG.error(e.getMessage());
                        }
                    }
                    else {
                        LOG.warn("Handling organization failed, trying again. Organization oid: " + organizationOid +
                        " Exception was: " + exception);
                    }
                }
            }
        });
    }

    private void handleOrganization(String organizationOid) throws DataUpdateException {
        // Get learning opportunities (koulutus) by organization
        List<String> learningOpportunityOids = tarjontaClient.getLearningOpportunityOidsByProvider(organizationOid);
        LOG.debug(String.format("Found %d learning opportunities for organization %s", learningOpportunityOids.size(), organizationOid));
        // Get application options (hakukohde) by organization
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
            ApplicationOption ao = tryToGetResource(aoOid, ApplicationOption.class);
            ApplicationSystem as = tryToGetResource(ao.getApplicationSystem(), ApplicationSystem.class);

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
