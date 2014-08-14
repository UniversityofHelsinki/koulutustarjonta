package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationSystem {
    private String oid;
    private I18N name;
    private I18N applicationMethod;
    private int applicationYear;
    private Season applicationSeason;
    private int educationStartYear;
    private Season educationStartSeason;
    private String applicationFormUrl;
    private List<ApplicationPeriod> applicationPeriods;
}
