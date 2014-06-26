package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityJoinRow {

    final LearningOpportunity learningOpportunity;
    final TeachingLanguage teachingLanguage;

    public LearningOpportunityJoinRow(LearningOpportunity learningOpportunity, TeachingLanguage teachingLanguage) {
        this.teachingLanguage = teachingLanguage;
        this.learningOpportunity = learningOpportunity;
    }

    public LearningOpportunity getLearningOpportunity() {
        return learningOpportunity;
    }

    public TeachingLanguage getTeachingLanguage() {
        return teachingLanguage;
    }
}
