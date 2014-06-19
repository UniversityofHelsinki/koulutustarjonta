package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDTO {

    private String oid;
    private I18NDTO qualification;
    private I18NDTO educationalField;
    private I18NDTO degreeProgram;;
    private String startYear;
    private I18NDTO startSeason;
    private String plannedDurationValue;
    private I18NDTO plannedDurationUnit;
    private String creditValue;
    private I18NDTO creditUnit;
    private List<I18NDTO> teachingLanguages;
    private List<String> translations;
    private I18NDTO goals;
    private I18NDTO languageInfo;
    private I18NDTO selectingMajorSubject;
    private I18NDTO workLifePlacement;
    private I18NDTO competency;
    private I18NDTO postgraduateStudies;
    private I18NDTO contents;
    private I18NDTO structure;
    private I18NDTO thesis;
    private I18NDTO internationalization;
    private I18NDTO cooperation;
    private I18NDTO research;
    //private List<ApplicationOption> applicationOptions;
    //private Organization provider;

    public LearningOpportunityDTO() {
    }

    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @JsonProperty("tavoitteet")
    public I18NDTO getGoals() {
        return goals;
    }

    public void setGoals(I18NDTO goals) {
        this.goals = goals;
    }
}
