package fi.helsinki.koulutustarjonta.core.domain;

import java.util.List;

/**
 * Models one application option (hakukohde).
 *
 * @author Hannu Lyytikainen
 */
public class ApplicationOption {

    private String oid;
    private I18N name;
    private int startingQuota;//aloituspaikat
    private List<I18N> applicationSuitabilityRequirements;//hakukelpoisuusvaatimukset
    private I18N applicationSuitabilityrequirementDescription;//hakukelpoisuusvaatimus kuvaus
    private I18N additionalInfo;//lisatiedot
    private I18N selectionCriteria;//valintaperustekuvaus
    private I18N sora;//sora kuvaus
    private List<Exam> exams;//valintakokeet
    private List<Attachment> attachments;//liitteet
    private ApplicationSystem applicationSystem;//haku

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public I18N getName() {
        return name;
    }

    public void setName(I18N name) {
        this.name = name;
    }

    public int getStartingQuota() {
        return startingQuota;
    }

    public void setStartingQuota(int startingQuota) {
        this.startingQuota = startingQuota;
    }

    public List<I18N> getApplicationSuitabilityRequirements() {
        return applicationSuitabilityRequirements;
    }

    public void setApplicationSuitabilityRequirements(List<I18N> applicationSuitabilityRequirements) {
        this.applicationSuitabilityRequirements = applicationSuitabilityRequirements;
    }

    public I18N getApplicationSuitabilityrequirementDescription() {
        return applicationSuitabilityrequirementDescription;
    }

    public void setApplicationSuitabilityrequirementDescription(I18N applicationSuitabilityrequirementDescription) {
        this.applicationSuitabilityrequirementDescription = applicationSuitabilityrequirementDescription;
    }

    public I18N getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(I18N additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public I18N getSelectionCriteria() {
        return selectionCriteria;
    }

    public void setSelectionCriteria(I18N selectionCriteria) {
        this.selectionCriteria = selectionCriteria;
    }

    public I18N getSora() {
        return sora;
    }

    public void setSora(I18N sora) {
        this.sora = sora;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {

        this.exams = exams;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public ApplicationSystem getApplicationSystem() {
        return applicationSystem;
    }

    public void setApplicationSystem(ApplicationSystem applicationSystem) {
        this.applicationSystem = applicationSystem;
    }
}
