package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.LearningOpportunityJoinRow;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Builds LearninOpportunity objects from JOIN database
 * query results.
 *
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityObjectMapper {
    public static List<LearningOpportunity> build(List<LearningOpportunityJoinRow> joinRows) {
        return joinRows.stream()
                .collect(Collectors.groupingBy(row -> row.getLearningOpportunity().getOid()))
                .values().stream()
                .map(list -> resolveLearningOpportunity(list))
                .collect(Collectors.<LearningOpportunity>toList());
    }

    private static LearningOpportunity resolveLearningOpportunity(List<LearningOpportunityJoinRow> rows) {
        LearningOpportunity lo = rows.get(0).getLearningOpportunity();
        lo.setTeachingLanguages(rows.stream()
                .collect(Collectors.groupingBy(row -> row.getTeachingLanguage().getLang()))
                .values()
                .stream()
                .map(list -> resolveTeachingLanguage(list))
                .collect(toList()));
        lo.setApplicationOptions(rows
                .stream()
                .map(row -> row.getApplicationOptionOid())
                .distinct()
                .collect(toList()));

        lo.setParents(rows
        .stream()
        .filter(row -> row.getParentOid() != null)
        .map(row -> row.getParentOid())
        .distinct()
        .collect(toList()));

        lo.setChildren(rows
                .stream()
                .filter(row -> row.getChildOid() != null)
                .map(row -> row.getChildOid())
                .distinct()
                .collect(toList()));
        return lo;
    }

    private static TeachingLanguage resolveTeachingLanguage(List<LearningOpportunityJoinRow> rows) {
        return rows.get(0).getTeachingLanguage();
    }
}
