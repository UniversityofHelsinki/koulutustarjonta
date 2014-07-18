package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Attachment;
import fi.helsinki.koulutustarjonta.domain.Exam;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@Getter
public class ApplicationOptionJoinRow {

    public ApplicationOptionJoinRow(ApplicationOption applicationOption, Exam exam, ExamEvent examEvent, Attachment attachment) {
        this.applicationOption = applicationOption;
        this.exam = exam;
        this.examEvent = examEvent;
        this.attachment = attachment;
    }

    ApplicationOption applicationOption;
    Exam exam;
    ExamEvent examEvent;
    Attachment attachment;
}
