package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
public class ApplicationOptionJoinRow {
    private final ApplicationOption applicationOption;
    private final Exam exam;
    private final ExamEvent examEvent;
    private final Attachment attachment;
    private final Requirement requirement;
    private final ApplicationPeriod applicationPeriod;
}
