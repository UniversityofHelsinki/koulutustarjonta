package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Application option, links a learning opportunity to an application system
 *
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class ApplicationOption {

    private String oid;
    private I18N name;
    private int startingQuota;
    private I18N startingQuotaDescription;
    private I18N requirementDescription;
    private I18N additionalInfo;
    private I18N selectionCriteria;
    private I18N sora;
    private List<Exam> exams;
    private List<Attachment> attachments;
    private List<Requirement> requirements;
    private String applicationSystem;
    private ApplicationPeriod applicationPeriod;// for saving
    private String applicationPeriodId;// for fetching
    private Integer firstTimePositions;
}
