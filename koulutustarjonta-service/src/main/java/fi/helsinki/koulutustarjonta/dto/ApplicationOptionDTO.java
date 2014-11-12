package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationOptionDTO {

    @JsonProperty("oid")
    private String oid;
    @JsonProperty("nimi")
    private I18NDTO name;
    @JsonProperty("aloituspaikat")
    private int startingQuota;
    @JsonProperty("aloituspaikat_kuvaus")
    private I18NDTO startingQuotaDescription;
    @JsonProperty("hakukelpoisuusvaatimus_kuvaus")
    private I18NDTO requirementDescription;
    @JsonProperty("lisatiedot")
    private I18NDTO additionalInfo;
    @JsonProperty("valintaperustekuvaus")
    private I18NDTO selectionCriteria;
    @JsonProperty("sorakuvaus")
    private I18NDTO sora;
    @JsonProperty("valintakokeet")
    private List<ExamDTO> exams;
    @JsonProperty("liitteet")
    private List<AttachmentDTO> attachments;
    @JsonProperty("hakukelpoisuusvaatimukset")
    private List<I18NDTO> requirements;
    @JsonProperty("haku")
    private String applicationSystem;
    @JsonProperty("kuvauskielet")
    private List<String> translations;
}
