package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.LearningOpportunityObjectGraphBuilder;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDAO {

    final LearningOpportunityJDBI jdbi;

    public LearningOpportunityDAO(LearningOpportunityJDBI jdbi) {
        this.jdbi = jdbi;
    }

    public void save(LearningOpportunity learningOpportunity) {
        int affected = jdbi.update(learningOpportunity);
        if (affected == 0) {
            insert(learningOpportunity);
        }
        else {
            jdbi.removeTeachingLanguagesFromLearningOpportunity(learningOpportunity.getOid());
            jdbi.addTeachingLanguagesToLearningOpportunity(learningOpportunity.getOid(),
            learningOpportunity.getTeachingLanguages()
                    .stream()
                    .map(x -> x.getLang())
                    .collect(Collectors.toList()));
        }
    }

    private void insert(LearningOpportunity learningOpportunity) {
        jdbi.insert(learningOpportunity);
        jdbi.insertTeachingLanguages(learningOpportunity.getTeachingLanguages());
        jdbi.addTeachingLanguagesToLearningOpportunity(learningOpportunity.getOid(),
                learningOpportunity.getTeachingLanguages()
                        .stream()
                        .map(x -> x.getLang())
                        .collect(Collectors.toList()));
    }

    public List<LearningOpportunity> findAll() {
        return LearningOpportunityObjectGraphBuilder.build(jdbi.findAllJoinRows());
    }

    public LearningOpportunity findById(String id) {
        return LearningOpportunityObjectGraphBuilder.build(jdbi.findJoinRowsById(id)).get(0);
    }
}
