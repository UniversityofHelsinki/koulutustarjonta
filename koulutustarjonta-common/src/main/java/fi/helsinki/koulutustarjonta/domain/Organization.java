package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
@ToString
public class Organization {

    private final String oid;
    private final I18N name;
    private final I18N outline;
    private final I18N expenses;
    private final I18N internationalStudyPrograms;
    private final I18N studentTransfer;
    private final I18N studyEnvironment;
    private final Some some;
    private final List<ContactInfo> contactInfos;
    private final List<ContactInfo> applicantServices;

    /**
     * Add contact information list.
     * @param contactInfoList
     * @return a new organization with new contact information
     */
    public Organization addContactInfos(List<ContactInfo> contactInfoList) {
        return new Organization(oid, name, outline, expenses, internationalStudyPrograms,
                studentTransfer, studyEnvironment, some, contactInfoList, applicantServices);
    }

    /**
     * Add applicant service contact information.
     * @param applicantServiceList
     * @return a new organization with new applicant service contact information
     */
    public Organization addApplicantServices(List<ContactInfo> applicantServiceList) {
        return new Organization(oid, name, outline, expenses, internationalStudyPrograms,
                studentTransfer, studyEnvironment, some, contactInfos, applicantServiceList);
    }
}
