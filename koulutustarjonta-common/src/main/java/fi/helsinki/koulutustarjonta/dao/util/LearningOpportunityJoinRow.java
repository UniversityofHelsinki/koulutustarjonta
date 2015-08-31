package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
public class LearningOpportunityJoinRow {

    private final LearningOpportunity learningOpportunity;
    private final TeachingLanguage teachingLanguage;
    private final String applicationOptionOid;
    private final String parentOid;
    private final String childOid;
    private final I18N keyword;
}
