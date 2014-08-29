package fi.helsinki.koulutustarjonta.client.converter;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityConverter extends BaseConverter {

    public LearningOpportunityConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
    }

    public LearningOpportunity convert(JsonNode apiCallResult) {
        LearningOpportunity lo = new LearningOpportunity();
        JsonNode content = apiCallResult.get("result");

        lo.setOid(content.get("oid").textValue());
        lo.setQualification(resolveQualification(content.get("tutkintonimikes")));
        lo.setEducationalField(resolveMetaLangName(content.get("opintoala")));
        lo.setDegreeProgram(resolveDegreeProgram(content.get("koulutusohjelma")));
        lo.setStartYear(content.get("koulutuksenAlkamisvuosi").intValue());
        lo.setStartSeason(resolveMetaLangName(content.get("koulutuksenAlkamiskausi")));
        lo.setPlannedDurationValue(content.get("suunniteltuKestoArvo").asInt());
        lo.setPlannedDurationUnit(resolveMetaLangName(content.get("suunniteltuKestoTyyppi")));
        lo.setCreditValue(content.get("opintojenLaajuusarvo").get("arvo").asInt());
        lo.setCreditUnit(resolveMetaLangName(content.get("opintojenLaajuusyksikko")));
        lo.setTeachingLanguages(resolveTeachingLanguages(content.get("opetuskielis")));

        //komo info
        JsonNode komoInfo = content.get("kuvausKomo");
        lo.setGoals(convertMetaTextsToI18N(komoInfo.get("TAVOITTEET")));
        lo.setStructure(convertMetaTextsToI18N(komoInfo.get("KOULUTUKSEN_RAKENNE")));
        lo.setPostgraduateStudies(convertMetaTextsToI18N(komoInfo.get("JATKOOPINTO_MAHDOLLISUUDET")));
        lo.setCompetency(convertMetaTextsToI18N(komoInfo.get("PATEVYYS")));

        lo.setTranslations(resolveTranslations(komoInfo.get("TAVOITTEET")));

        //komoto info
        JsonNode komotoInfo = content.get("kuvausKomoto");
        lo.setLanguageInfo(convertMetaTextsToI18N(komotoInfo.get("LISATIETOA_OPETUSKIELISTA")));
        lo.setCooperation(convertMetaTextsToI18N(komotoInfo.get("YHTEISTYO_MUIDEN_TOIMIJOIDEN_KANSSA")));
        lo.setSelectingMajorSubject(convertMetaTextsToI18N(komotoInfo.get("PAAAINEEN_VALINTA")));
        lo.setInternationalization(convertMetaTextsToI18N(komotoInfo.get("KANSAINVALISTYMINEN")));
        lo.setWorkLifePlacement(convertMetaTextsToI18N(komotoInfo.get("SIJOITTUMINEN_TYOELAMAAN")));
        lo.setContents(convertMetaTextsToI18N(komotoInfo.get("SISALTO")));
        lo.setResearch(convertMetaTextsToI18N(komotoInfo.get("TUTKIMUKSEN_PAINOPISTEET")));
        lo.setThesis(convertMetaTextsToI18N(komotoInfo.get("LOPPUKOEVAATIMUKSET")));

        return lo;
    }

    private List<String> resolveTranslations(JsonNode goals) {
        return Lists.newArrayList(goals.get("meta")).parallelStream()
                .map(elem -> elem.get("kieliArvo").textValue().toLowerCase())
                .collect(Collectors.toList());
    }

    private List<TeachingLanguage> resolveTeachingLanguages(JsonNode node) {
        return Lists.newArrayList(node.get("meta")).parallelStream()
                .map(elem -> resolveTeachingLanguage(elem))
                .collect(Collectors.toList());
    }

    private TeachingLanguage resolveTeachingLanguage(JsonNode langNode) {
        return new TeachingLanguage(langNode.get("arvo").textValue().toLowerCase(),
                new I18N(langNode.get("meta").get("kieli_fi").get("nimi").textValue(),
                        langNode.get("meta").get("kieli_sv").get("nimi").textValue(),
                        langNode.get("meta").get("kieli_en").get("nimi").textValue()));
    }

    private I18N resolveDegreeProgram(JsonNode node) {
        JsonNode texts = node.get("tekstis");
        return new I18N(
                texts.get("kieli_fi").textValue(),
                texts.get("kieli_sv").textValue(),
                texts.get("kieli_en").textValue()
        );
    }

    private I18N resolveMetaLangName(JsonNode node) {
        JsonNode texts = node.get("meta");
        return new I18N(
                texts.get("kieli_fi").get("nimi").textValue(),
                texts.get("kieli_sv").get("nimi").textValue(),
                texts.get("kieli_en").get("nimi").textValue()
        );
    }

    private I18N resolveQualification(JsonNode node) {
        JsonNode texts = node.get("meta").elements().next().get("meta");
        return new I18N(
                texts.get("kieli_fi").get("nimi").textValue(),
                texts.get("kieli_sv").get("nimi").textValue(),
                texts.get("kieli_en").get("nimi").textValue()
        );
    }

}
