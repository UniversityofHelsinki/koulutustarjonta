package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.*;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@Getter
public class ApplicationOptionJoinRow {
    private ApplicationOption applicationOption;
    private Exam exam;
    private ExamEvent examEvent;
    private Attachment attachment;
    private Requirement requirement;

    public ApplicationOptionJoinRow(ApplicationOption applicationOption, Exam exam, ExamEvent examEvent,
                                    Attachment attachment, Requirement requirement) {
        this.applicationOption = applicationOption;
        this.exam = exam;
        this.examEvent = examEvent;
        this.attachment = attachment;
        this.requirement = requirement;
    }
}
