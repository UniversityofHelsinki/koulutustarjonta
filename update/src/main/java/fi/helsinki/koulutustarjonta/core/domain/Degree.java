package fi.helsinki.koulutustarjonta.core.domain;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class Degree {

    private String oid;
    private I18N qualification;//tutkintonimike
    private I18N educationalField;//opintoala
    private I18N degreeProgram;//tutkintoOhjelma;
    private String startYear;//alkamiskausi
    private I18N startSeason;//alkamisvuosi
    private String plannedDurationValue;//suunniteltu kesto yksikko
    private I18N plannedDurationUnit;//suunniteltu kesto arvo
    private String creditValue;//laajuus arvo
    private I18N creditUnit;//laajuus yksikko
    private List<I18N> teachingLanguages;//opetuskielet
    private List<String> translations;//kuvauskielet
    private I18N goals;//tavoitteet
    private I18N languageInfo;//lisaa tietoa opetuskielista
    private I18N selectingMajorSubject;//paaaineen valinta
    private I18N workLifePlacement;//sijoittuminen tyoelamaan
    private I18N competency;//patevyys
    private I18N postgraduateStudies;//jatko-opinnot
    private I18N contents;//sisalto
    private I18N structure;//rakenne
    private I18N thesis;//opinnaytetyo
    private I18N internationalization;//kansainvalistyminen
    private I18N cooperation;//yhteistyo muiden toimijoiden kanssa
    private I18N research;//tutkimuksen painopisteet
    private List<ApplicationOption> applicationOptions;//hakukohteet
    private Organization provider;//tarjoaja

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public I18N getQualification() {
        return qualification;
    }

    public void setQualification(I18N qualification) {
        this.qualification = qualification;
    }

    public I18N getEducationalField() {
        return educationalField;
    }

    public void setEducationalField(I18N educationalField) {
        this.educationalField = educationalField;
    }

    public I18N getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(I18N degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public I18N getStartSeason() {
        return startSeason;
    }

    public void setStartSeason(I18N startSeason) {
        this.startSeason = startSeason;
    }

    public String getPlannedDurationValue() {
        return plannedDurationValue;
    }

    public void setPlannedDurationValue(String plannedDurationValue) {
        this.plannedDurationValue = plannedDurationValue;
    }

    public I18N getPlannedDurationUnit() {
        return plannedDurationUnit;
    }

    public void setPlannedDurationUnit(I18N plannedDurationUnit) {
        this.plannedDurationUnit = plannedDurationUnit;
    }

    public String getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(String creditValue) {
        this.creditValue = creditValue;
    }

    public I18N getCreditUnit() {
        return creditUnit;
    }

    public void setCreditUnit(I18N creditUnit) {
        this.creditUnit = creditUnit;
    }

    public List<I18N> getTeachingLanguages() {
        return teachingLanguages;
    }

    public void setTeachingLanguages(List<I18N> teachingLanguages) {
        this.teachingLanguages = teachingLanguages;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public I18N getGoals() {
        return goals;
    }

    public void setGoals(I18N goals) {
        this.goals = goals;
    }

    public I18N getLanguageInfo() {
        return languageInfo;
    }

    public void setLanguageInfo(I18N languageInfo) {
        this.languageInfo = languageInfo;
    }

    public I18N getSelectingMajorSubject() {
        return selectingMajorSubject;
    }

    public void setSelectingMajorSubject(I18N selectingMajorSubject) {
        this.selectingMajorSubject = selectingMajorSubject;
    }

    public I18N getWorkLifePlacement() {
        return workLifePlacement;
    }

    public void setWorkLifePlacement(I18N workLifePlacement) {
        this.workLifePlacement = workLifePlacement;
    }

    public I18N getCompetency() {
        return competency;
    }

    public void setCompetency(I18N competency) {
        this.competency = competency;
    }

    public I18N getPostgraduateStudies() {
        return postgraduateStudies;
    }

    public void setPostgraduateStudies(I18N postgraduateStudies) {
        this.postgraduateStudies = postgraduateStudies;
    }

    public I18N getContents() {
        return contents;
    }

    public void setContents(I18N contents) {
        this.contents = contents;
    }

    public I18N getStructure() {
        return structure;
    }

    public void setStructure(I18N structure) {
        this.structure = structure;
    }

    public I18N getThesis() {
        return thesis;
    }

    public void setThesis(I18N thesis) {
        this.thesis = thesis;
    }

    public I18N getInternationalization() {
        return internationalization;
    }

    public void setInternationalization(I18N internationalization) {
        this.internationalization = internationalization;
    }

    public I18N getCooperation() {
        return cooperation;
    }

    public void setCooperation(I18N cooperation) {
        this.cooperation = cooperation;
    }

    public I18N getResearch() {
        return research;
    }

    public void setResearch(I18N research) {

        this.research = research;
    }

    public List<ApplicationOption> getApplicationOptions() {
        return applicationOptions;
    }

    public void setApplicationOptions(List<ApplicationOption> applicationOptions) {
        this.applicationOptions = applicationOptions;
    }

    public Organization getProvider() {
        return provider;
    }

    public void setProvider(Organization provider) {
        this.provider = provider;
    }
}
