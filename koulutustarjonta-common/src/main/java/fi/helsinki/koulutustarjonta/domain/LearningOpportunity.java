package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningOpportunity {

    private String oid;
    private I18N qualification;//tutkintonimike
    private I18N educationalField;//opintoala
    private I18N degreeProgram;//tutkintoOhjelma
    private I18N educationLevel;//koulutusaste
    private Integer startYear;//alkamisvuosi
    private I18N startSeason;//alkamiskausi
    private String plannedDurationValue;//suunniteltu kesto arvo
    private I18N plannedDurationUnit;//suunniteltu kesto yksikko
    private String creditValue;//laajuus arvo
    private I18N creditUnit;//laajuus yksikko
    private List<TeachingLanguage> teachingLanguages;//opetuskielet
    private I18N goals;//tavoitteet
    private I18N structure;//rakenne
    private I18N postgraduateStudies;//jatko-opinnot
    private I18N competency;//patevyys
    private I18N languageInfo;//lisaa tietoa opetuskielista
    private I18N cooperation;//yhteistyo muiden toimijoiden kanssa
    private I18N selectingMajorSubject;//paaaineen valinta
    private I18N internationalization;//kansainvalistyminen
    private I18N workLifePlacement;//sijoittuminen tyoelamaan
    private I18N contents;//sisalto
    private I18N research;//tutkimuksen painopisteet
    private I18N thesis;//opinnaytetyo
    private List<String> applicationOptions;//hakukohteet
    private List<String> provider;//tarjoaja
    private List<String> parents;
    private List<String> children;
    private List<I18N> keywords;
}
