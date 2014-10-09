package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.LearningOpportunityObjectGraphBuilder;
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
        jdbi.upsert(learningOpportunity);
        jdbi.insertTeachingLanguages(learningOpportunity.getTeachingLanguages());
        jdbi.removeTeachingLanguagesFromLearningOpportunity(learningOpportunity.getOid());
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
    }

    public List<LearningOpportunity> findAll() {
        return LearningOpportunityObjectGraphBuilder.build(jdbi.findAllJoinRows());
    }

    public LearningOpportunity findById(String id) {
        return LearningOpportunityObjectGraphBuilder.build(jdbi.findJoinRowsById(id)).get(0);
    }
}
