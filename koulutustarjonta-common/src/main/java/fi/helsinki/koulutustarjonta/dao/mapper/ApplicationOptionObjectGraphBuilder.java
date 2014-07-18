package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.ApplicationOptionJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Exam;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionObjectGraphBuilder {

    public static List<ApplicationOption> build(List<ApplicationOptionJoinRow> joinRows) {
        List<ApplicationOption> aos = joinRows.stream()
                .collect(groupingBy(row -> row.getApplicationOption().getOid()))
                .values()
                .stream()
                .map(rows -> resolveApplicationOption(rows))
                .collect(toList());

        return aos;
    }

    private static ApplicationOption resolveApplicationOption(List<ApplicationOptionJoinRow> joinRows) {
        ApplicationOption ao = joinRows.get(0).getApplicationOption();

        List<Exam> exams = joinRows.stream()
                .collect(groupingBy(row -> row.getExam().getOid()))
                .values()
                .stream()
                .map(rows -> resolveExam(rows))
                .collect(toList());

        ao.setExams(exams);
        return ao;
    }

    private static Exam resolveExam(List<ApplicationOptionJoinRow> joinRows) {
        Exam exam = joinRows.get(0).getExam();

        List<ExamEvent> events = joinRows.stream()
                .collect(groupingBy(row -> row.getExamEvent().getOid()))
                .values()
                .stream()
                .map(rows -> resolveExamEvent(rows))
                .collect(toList());

        exam.setEvents(events);
        return exam;
    }

    private static ExamEvent resolveExamEvent(List<ApplicationOptionJoinRow> joinRows) {
        return joinRows.get(0).getExamEvent();
    }

}
