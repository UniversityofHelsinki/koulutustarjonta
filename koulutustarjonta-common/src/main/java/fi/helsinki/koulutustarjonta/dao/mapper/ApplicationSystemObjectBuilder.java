package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.ApplicationSystemJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemObjectBuilder {

    public static ApplicationSystem buildOne(List<ApplicationSystemJoinRow> rows) {
        return build(rows).get(0);
    }

    public static List<ApplicationSystem> build(List<ApplicationSystemJoinRow> rows) {
        return rows.parallelStream()
                .collect(groupingBy(row -> row.getApplicationSystem().getOid()))
                .values()
                .parallelStream()
                .map(r -> resolveApplicationSystem(r))
                .collect(toList());
    }

    private static ApplicationSystem resolveApplicationSystem(List<ApplicationSystemJoinRow> rows) {
        ApplicationSystem as = rows.get(0).getApplicationSystem();
        List<ApplicationPeriod> aps = rows.stream()
                .filter(row -> row.getApplicationPeriod() != null)
                .collect(groupingBy(row -> row.getApplicationPeriod().getId()))
                .values()
                .parallelStream()
                .map(r -> resolveApplicationPeriod(r))
                .collect(toList());
        as.setApplicationPeriods(aps);
        return as;
    }

    private static ApplicationPeriod resolveApplicationPeriod(List<ApplicationSystemJoinRow> rows) {
        return rows.get(0).getApplicationPeriod();
    }
}
