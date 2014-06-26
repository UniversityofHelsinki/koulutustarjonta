package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.LearningOpportunityJoinRow;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityObjectGraphBuilder {
    public static List<LearningOpportunity> build(List<LearningOpportunityJoinRow> joinRows) {

        Map<String, List<LearningOpportunityJoinRow>> grouped = joinRows.stream()
                .collect(Collectors.groupingBy(row -> row.getLearningOpportunity().getOid()));

        return grouped.values().stream()
                .map(list -> combineRows(list))
                .collect(Collectors.<LearningOpportunity>toList());
    }

    private static LearningOpportunity combineRows(List<LearningOpportunityJoinRow> rows) {
        LearningOpportunity lo = rows.get(0).getLearningOpportunity();
        lo.setTeachingLanguages(rows.stream().map(row -> row.getTeachingLanguage()).collect(Collectors.toList()));
        return lo;
    }
}
