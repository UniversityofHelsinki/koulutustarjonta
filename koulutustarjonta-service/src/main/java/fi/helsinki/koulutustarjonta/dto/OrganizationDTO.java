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
public class OrganizationDTO {
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("nimi")
    private I18NDTO name;
    @JsonProperty("yleiskuvaus")
    private I18NDTO outline;
    @JsonProperty("kustannukset")
    private I18NDTO expenses;
    @JsonProperty("kansainvaliset_koulutusohjelmat")
    private I18NDTO internationalStudyPrograms;
    @JsonProperty("opiskelijaliikkuvuus")
    private I18NDTO studentTransfer;
    @JsonProperty("oppimisymparisto")
    private I18NDTO studyEnvironment;
    @JsonProperty("saavutettavuus")
    private I18NDTO accessibility;
    @JsonProperty("vuosikello")
    private I18NDTO yearClock;
    @JsonProperty("vastuuhenkilot")
    private I18NDTO peopleInCharge;
    @JsonProperty("valintamenettely")
    private I18NDTO selectionProcedure;
    @JsonProperty("aiemmin_hankittu_osaaminen")
    private I18NDTO previouslyGainedExperience;
    @JsonProperty("kieliopinnot")
    private I18NDTO languageStudies;
    @JsonProperty("tyoharjoittelu")
    private I18NDTO internship;
    @JsonProperty("some")
    private SomeDTO some;
    @JsonProperty("yhteystiedot")
    private List<ContactInfoDTO> contactInfos;
    @JsonProperty("hakijapalvelut")
    private List<ContactInfoDTO> applicantServices;
    @JsonProperty("kuvauskielet")
    private List<String> translations;
}
