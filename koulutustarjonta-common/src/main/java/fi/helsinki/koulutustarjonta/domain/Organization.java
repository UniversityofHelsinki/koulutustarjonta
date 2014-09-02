package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
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
}
