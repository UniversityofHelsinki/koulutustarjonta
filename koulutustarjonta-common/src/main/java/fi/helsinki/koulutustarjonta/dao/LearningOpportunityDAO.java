package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.LearningOpportunityObjectMapper;
import fi.helsinki.koulutustarjonta.dao.util.LearningOpportunityJoinRow;
import fi.helsinki.koulutustarjonta.domain.I18N;
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

    public LearningOpportunityDAO(LearningOpportunityJDBI jdbi) {
        this.jdbi = jdbi;
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
            jdbi.commit();
        } catch (Exception e) {
            LOG.warn("Failed to save learning opportunity, rolling back");
            jdbi.rollback();
            throw e;
        }
    }

    public List<LearningOpportunity> findAll() {
        return LearningOpportunityObjectMapper.build(jdbi.findAllJoinRows());
    }

    public LearningOpportunity findById(String id) throws ResourceNotFound {
        List<LearningOpportunityJoinRow> rows = jdbi.findJoinRowsById(id);
        if (rows.size() == 0) {
            throw new ResourceNotFound(LearningOpportunity.class, id);
        }
        else {
            return LearningOpportunityObjectMapper.build(rows).get(0);
        }
    }
}
