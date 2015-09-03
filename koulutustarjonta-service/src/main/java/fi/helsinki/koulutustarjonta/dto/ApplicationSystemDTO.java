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
public class ApplicationSystemDTO {
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("nimi")
    private I18NDTO name;
    @JsonProperty("hakutapa")
    private I18NDTO applicationMethod;
    @JsonProperty("hakuvuosi")
    private int applicationYear;
    @JsonProperty("hakukausi")
    private SeasonDTO applicationSeason;
    @JsonProperty("koulutuksen_alkamisvuosi")
    private int educationStartYear;
    @JsonProperty("koulutuksen_alkamiskausi")
    private SeasonDTO educationStartSeason;
    @JsonProperty("hakulomake_url")
    private I18NDTO formUrl;
    @JsonProperty("hakuajat")
    private List<ApplicationPeriodDTO> applicationPeriods;
    @JsonProperty("kuvauskielet")
    private List<String> translations;
}
