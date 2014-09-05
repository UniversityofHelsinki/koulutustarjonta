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
    @JsonProperty("some")
    private SomeDTO some;
    @JsonProperty("yhteystiedot")
    private List<ContactInfoDTO> contactInfos;
    @JsonProperty("hakijapalvelut")
    private List<ContactInfoDTO> applicantServices;
}
