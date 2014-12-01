package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Models one application option (hakukohde).
 *
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class ApplicationOption {

    private String oid;
    private I18N name;
    private int startingQuota;//aloituspaikat
    private I18N startingQuotaDescription;//aloituspaikkakuvaus
    private I18N requirementDescription;//hakukelpoisuusvaatimus kuvaus
    private I18N additionalInfo;//lisatiedot
    private I18N selectionCriteria;//valintaperustekuvaus
    private I18N sora;//sora kuvaus
    private List<Exam> exams;//valintakokeet
    private List<Attachment> attachments;//liitteet
    private List<Requirement> requirements;//hakukelpoisuusvaatimukset
    private String applicationSystem;//haku
    private String applicationPeriod;//hakuaika
}
