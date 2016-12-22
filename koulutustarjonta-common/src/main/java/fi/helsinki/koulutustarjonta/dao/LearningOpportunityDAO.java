package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.LOContactJDBI;
import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LOContact;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDAO {

    private static final Logger LOG = LoggerFactory.getLogger(LearningOpportunityDAO.class);

    final LearningOpportunityJDBI jdbi;
    final LOContactJDBI contactJDBI;

    public LearningOpportunityDAO(LearningOpportunityJDBI jdbi, LOContactJDBI loContactJDBI) {
        this.jdbi = jdbi;
        this.contactJDBI = loContactJDBI;
    }
    public void delete(String learningOpportunityOid) {
        jdbi.delete(learningOpportunityOid);
    }
    public void save(LearningOpportunity learningOpportunity) {
        LOG.debug(String.format("Saving learning opportunity %s", learningOpportunity.getOid()));
        jdbi.begin();
        try {
            jdbi.upsert(learningOpportunity);
            jdbi.insertTeachingLanguages(learningOpportunity.getTeachingLanguages());
            jdbi.removeTeachingLanguagesFromLearningOpportunity(learningOpportunity.getOid());
            jdbi.removeKeywordsFromLearningOpportunity(learningOpportunity.getOid());

            for (I18N i : learningOpportunity.getKeywords()) {
                LOG.debug(String.format("Saving field %s", i.getFi()));
                jdbi.addKeywordToLearningOpportunity(learningOpportunity.getOid(), i.getFi(), i.getSv(), i.getEn());
                LOG.debug(String.format("Done saving field %s", i.getFi()));
            }
            jdbi.addTeachingLanguagesToLearningOpportunity(learningOpportunity.getOid(),
                    learningOpportunity.getTeachingLanguages()
                            .stream()
                            .map(x -> x.getLang())
                            .collect(Collectors.toList())
            );
            if (learningOpportunity.getApplicationOptions() != null && !learningOpportunity.getApplicationOptions().isEmpty()) {
                LOG.debug(String.format("Adding application options to learning opportunity %s", learningOpportunity.getApplicationOptions().toString()));
                jdbi.addApplicationOptions(learningOpportunity.getOid(), learningOpportunity.getApplicationOptions());
                jdbi.removeDeletedApplicationOptions(learningOpportunity.getOid(), learningOpportunity.getApplicationOptions());
            }
            if (learningOpportunity.getParents() != null) {
                jdbi.addParents(learningOpportunity.getOid(), learningOpportunity.getParents());
            }
            if (learningOpportunity.getChildren() != null) {
                jdbi.addChildren(learningOpportunity.getOid(), learningOpportunity.getChildren());
            }
            if (learningOpportunity.getProvider() != null) {
                //First, remove current providers and then add the new ones
                jdbi.removeProviders(learningOpportunity.getOid());
                jdbi.addProviders(learningOpportunity.getOid(), learningOpportunity.getProvider());
            }

            jdbi.commit();
        } catch (Exception e) {
            LOG.warn("Failed to save learning opportunity, rolling back", e);
            jdbi.rollback();

            throw e;
        }

        contactJDBI.begin();
        try {
            contactJDBI.removeByLearningOpportunityID(learningOpportunity.getOid());
            if (learningOpportunity.getContactInfos() != null && !learningOpportunity.getContactInfos().isEmpty()) {
                learningOpportunity.getContactInfos()
                        .forEach(loContact -> {
                            LOG.debug(String.format("Saving LO contact info %s", loContact.toString()));
                            contactJDBI.insert(loContact, learningOpportunity.getOid());

                        });
            }
            contactJDBI.commit();
        } catch (Exception e) {
            LOG.warn("Failed to save learning opportunity, rolling back", e);
            contactJDBI.rollback();
            throw e;
        }
    }

    public List<LearningOpportunity> findAll() {
        return jdbi.findAllJoinRows();
    }

    public LearningOpportunity findById(String id) throws ResourceNotFound {
        List<LearningOpportunity> rows = jdbi.findJoinRowsById(id);

        if (rows == null || rows.size() != 1)
            throw new ResourceNotFound(LearningOpportunity.class, id);
        else{
            LearningOpportunity learningOpportunity = rows.get(0);
            List<LOContact> byLOId = contactJDBI.findByLOId(learningOpportunity.getOid());
            LOG.debug(byLOId.toString());
            learningOpportunity.setContactInfos(byLOId);
            return learningOpportunity;
        }
    }

}
