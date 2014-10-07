package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
public class LearningOpportunityJoinRow {

    private final LearningOpportunity learningOpportunity;
    private final TeachingLanguage teachingLanguage;
    private final String applicationOptionOid;
}
