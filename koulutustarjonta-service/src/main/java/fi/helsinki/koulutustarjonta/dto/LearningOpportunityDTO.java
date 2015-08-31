package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fi.helsinki.koulutustarjonta.domain.I18N;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LearningOpportunityDTO {

    @JsonProperty("oid")
    private String oid;
    @JsonProperty("tutkintonimike")
    private I18NDTO qualification;
    @JsonProperty("opintoala")
    private I18NDTO educationalField;
    @JsonProperty("koulutusaste")
    private I18NDTO educationLevel;
    @JsonProperty("tutkinto_ohjelma")
    private I18NDTO degreeProgram;;
    @JsonProperty("alkamisvuosi")
    private Integer startYear;
    @JsonProperty("alkamiskausi")
    private I18NDTO startSeason;
    @JsonProperty("suunniteltu_kesto_arvo")
    private String plannedDurationValue;
    @JsonProperty("suunniteltu_kesto_yksikko")
    private I18NDTO plannedDurationUnit;
    @JsonProperty("opintojen_laajuus_arvo")
    private Integer creditValue;
    @JsonProperty("opintojen_laajuus_yksikko")
    private I18NDTO creditUnit;
    @JsonProperty("opetuskielet")
    private List<TeachingLanguageDTO> teachingLanguages;
    @JsonProperty("kuvauskielet")
    private List<String> translations;
    @JsonProperty("tavoitteet")
    private I18NDTO goals;
    @JsonProperty("lisatietoa_opetuskielista")
    private I18NDTO languageInfo;
    @JsonProperty("paaaineen_valinta")
    private I18NDTO selectingMajorSubject;
    @JsonProperty("sijoittuminen_tyoelamaan")
    private I18NDTO workLifePlacement;
    @JsonProperty("patevyys")
    private I18NDTO competency;
    @JsonProperty("jatkoopintomahdollisuudet")
    private I18NDTO postgraduateStudies;
    @JsonProperty("sisalto")
    private I18NDTO contents;
    @JsonProperty("koulutuksen_rakenne")
    private I18NDTO structure;
    @JsonProperty("opinnaytetyo")
    private I18NDTO thesis;
    @JsonProperty("kansainvalistyminen")
    private I18NDTO internationalization;
    @JsonProperty("yhteistyo_muiden_toimijoiden_kanssa")
    private I18NDTO cooperation;
    @JsonProperty("tutkimuksen_painopisteet")
    private I18NDTO research;
    @JsonProperty("hakukohteet")
    private List<String> applicationOptions;
    @JsonProperty("tarjoaja")
    private String provider;
    @JsonProperty("sisaltyy_koulutuksiin")
    private List<String> parents;
    @JsonProperty("sisaltaa_koulutukset")
    private List<String> children;
    @JsonProperty("aiheet")
    private List<I18NDTO> keywords;
}
