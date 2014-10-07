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
    private Integer startYear;
    private I18NDTO startSeason;
    private Integer plannedDurationValue;
    private I18NDTO plannedDurationUnit;
    private Integer creditValue;
    private I18NDTO creditUnit;
    private List<TeachingLanguageDTO> teachingLanguages;
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
    private List<String> applicationOptions;
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

    @JsonProperty("tutkintonimike")
    public I18NDTO getQualification() {
        return qualification;
    }

    public void setQualification(I18NDTO qualification) {
        this.qualification = qualification;
    }

    @JsonProperty("opintoala")
    public I18NDTO getEducationalField() {
        return educationalField;
    }

    public void setEducationalField(I18NDTO educationalField) {
        this.educationalField = educationalField;
    }

    @JsonProperty("tutkinto_ohjelma")
    public I18NDTO getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(I18NDTO degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    @JsonProperty("alkamisvuosi")
    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    @JsonProperty("alkamiskausi")
    public I18NDTO getStartSeason() {
        return startSeason;
    }

    public void setStartSeason(I18NDTO startSeason) {
        this.startSeason = startSeason;
    }

    @JsonProperty("suunniteltu_kesto_arvo")
    public Integer getPlannedDurationValue() {
        return plannedDurationValue;
    }

    public void setPlannedDurationValue(Integer plannedDurationValue) {
        this.plannedDurationValue = plannedDurationValue;
    }

    @JsonProperty("suunniteltu_kesto_yksikko")
    public I18NDTO getPlannedDurationUnit() {
        return plannedDurationUnit;
    }

    public void setPlannedDurationUnit(I18NDTO plannedDurationUnit) {
        this.plannedDurationUnit = plannedDurationUnit;
    }

    @JsonProperty("opintojen_laajuus_arvo")
    public Integer getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Integer creditValue) {
        this.creditValue = creditValue;
    }

    @JsonProperty("opintojen_laajuus_yksikko")
    public I18NDTO getCreditUnit() {
        return creditUnit;
    }

    public void setCreditUnit(I18NDTO creditUnit) {
        this.creditUnit = creditUnit;
    }

    @JsonProperty("opetuskielet")
    public List<TeachingLanguageDTO> getTeachingLanguages() {
        return teachingLanguages;
    }

    public void setTeachingLanguages(List<TeachingLanguageDTO> teachingLanguages) {
        this.teachingLanguages = teachingLanguages;
    }

    @JsonProperty("kuvauskielet")
    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    @JsonProperty("tavoitteet")
    public I18NDTO getGoals() {
        return goals;
    }

    public void setGoals(I18NDTO goals) {
        this.goals = goals;
    }

    @JsonProperty("lisatietoa_opetuskielista")
    public I18NDTO getLanguageInfo() {
        return languageInfo;
    }

    public void setLanguageInfo(I18NDTO languageInfo) {
        this.languageInfo = languageInfo;
    }

    @JsonProperty("paaaineen_valinta")
    public I18NDTO getSelectingMajorSubject() {
        return selectingMajorSubject;
    }

    public void setSelectingMajorSubject(I18NDTO selectingMajorSubject) {
        this.selectingMajorSubject = selectingMajorSubject;
    }

    @JsonProperty("sijoittuminen_tyoelamaan")
    public I18NDTO getWorkLifePlacement() {
        return workLifePlacement;
    }

    public void setWorkLifePlacement(I18NDTO workLifePlacement) {
        this.workLifePlacement = workLifePlacement;
    }

    @JsonProperty("patevyys")
    public I18NDTO getCompetency() {
        return competency;
    }

    public void setCompetency(I18NDTO competency) {
        this.competency = competency;
    }

    @JsonProperty("jatkoopintomahdollisuudet")
    public I18NDTO getPostgraduateStudies() {
        return postgraduateStudies;
    }

    public void setPostgraduateStudies(I18NDTO postgraduateStudies) {
        this.postgraduateStudies = postgraduateStudies;
    }

    @JsonProperty("sisalto")
    public I18NDTO getContents() {
        return contents;
    }

    public void setContents(I18NDTO contents) {
        this.contents = contents;
    }

    @JsonProperty("koulutuksen_rakenne")
    public I18NDTO getStructure() {
        return structure;
    }

    public void setStructure(I18NDTO structure) {
        this.structure = structure;
    }

    @JsonProperty("opinnaytetyo")
    public I18NDTO getThesis() {
        return thesis;
    }

    public void setThesis(I18NDTO thesis) {
        this.thesis = thesis;
    }

    @JsonProperty("kansainvalistyminen")
    public I18NDTO getInternationalization() {
        return internationalization;
    }

    public void setInternationalization(I18NDTO internationalization) {
        this.internationalization = internationalization;
    }

    @JsonProperty("yhteistyo_muiden_toimijoiden_kanssa")
    public I18NDTO getCooperation() {
        return cooperation;
    }

    public void setCooperation(I18NDTO cooperation) {
        this.cooperation = cooperation;
    }

    @JsonProperty("tutkimuksen_painopisteet")
    public I18NDTO getResearch() {
        return research;
    }

    public void setResearch(I18NDTO research) {

        this.research = research;
    }

    @JsonProperty("hakukohteet")
    public List<String> getApplicationOptions() {
        return applicationOptions;
    }

    public void setApplicationOptions(List<String> applicationOptions) {
        this.applicationOptions = applicationOptions;
    }
}
