package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LOContact;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityConverter extends BaseConverter {

    private static final Logger log = Logger.getLogger(LearningOpportunityConverter.class);

    public LearningOpportunityConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
    }

    public LearningOpportunity convert(JsonNode apiCallResult) {
        LearningOpportunity lo = new LearningOpportunity();

        JsonNode content = apiCallResult.get("result");
        lo.setOid(content.get("oid").textValue());
        lo.setQualification(resolveQualification(content.get("tutkintonimikes")));
        lo.setEducationalField(resolveMetaLangName(content.get("opintoala")));
        lo.setEducationLevel(resolveMetaLangName(content.get("koulutusaste")));
        lo.setDegreeProgram(resolveDegreeProgram(content.get("koulutusohjelma")));
        lo.setStartYear(resolveStartingYear(apiCallResult));
        lo.setStartSeason(resolveMetaLangName(content.get("koulutuksenAlkamiskausi")));
        lo.setPlannedDurationValue(content.get("suunniteltuKestoArvo").textValue());
        lo.setPlannedDurationUnit(resolveMetaLangName(content.get("suunniteltuKestoTyyppi")));
        lo.setCreditValue(content.get("opintojenLaajuusarvo").get("arvo").textValue());
        lo.setCreditUnit(resolveMetaLangName(content.get("opintojenLaajuusyksikko")));
        lo.setTeachingLanguages(resolveTeachingLanguages(content.get("opetuskielis")));
        lo.setProvider(resolveProviders(content));

        //komo info
        JsonNode komoInfo = content.get("kuvausKomo");
        lo.setGoals(convertMetaTextsToI18N(komoInfo.get("TAVOITTEET")));

        lo.setStructure(convertMetaTextsToI18N(komoInfo.get("KOULUTUKSEN_RAKENNE")));
        lo.setPostgraduateStudies(convertMetaTextsToI18N(komoInfo.get("JATKOOPINTO_MAHDOLLISUUDET")));
        lo.setCompetency(convertMetaTextsToI18N(komoInfo.get("PATEVYYS")));

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
        lo.setChargeable(convertMetaTextsToI18N(komotoInfo.get("MAKSULLISUUS")));

        lo.setKeywords(resolveKeywords(content.get("aihees")));


        JsonNode contacts = content.get("yhteyshenkilos");
        if(contacts != null) {
            List<LOContact> contactInfos = resolveContactInfos(contacts.elements());
            lo.setContactInfos(contactInfos);
        }

        JsonNode hintaJson= content.get("hintaString");
        BigDecimal price = BigDecimal.ZERO;

        if(hintaJson != null) {
            String hintaString = hintaJson.asText();
            if(hintaString != null && !hintaString.isEmpty()) {
                try {
                    price = new BigDecimal(hintaString);
                } catch (Exception e) {
                    log.warn(String.format("Error converting hintaString '%s'", hintaString), e); ;
                }
            }
        }
        lo.setPrice(price);

        return lo;
    }

    private List<LOContact> resolveContactInfos(Iterator<JsonNode> yhteyshenkilos) {
        List<LOContact> contacts = new ArrayList<>();
        yhteyshenkilos.forEachRemaining(node -> {
            JsonNode kielet = node.get("kielet");
            List<String> languages = new ArrayList<>();
            if(kielet != null) {
                kielet.iterator().forEachRemaining(lang -> languages.add(lang.asText()));
            }
            contacts.add(LOContact.builder()
                    .name(node.get("nimi") == null ? "" : node.get("nimi").asText())
                    .title(node.get("titteli") == null ? "" : node.get("titteli").asText())
                    .email(node.get("sahkoposti") == null ? "" : node.get("sahkoposti").asText())
                    .phoneNumber(node.get("puhelin") == null ? "" : node.get("puhelin").asText())
                    .languages(languages)
                    .contactType(node.get("henkiloTyyppi") == null ? "" : node.get("henkiloTyyppi").asText())
                    .build());
        });
        return contacts;
    }



    public String resolveKomoOid(JsonNode apiCallResult) {
        return resolveContent(apiCallResult).get("komoOid").textValue();
    }

    public int resolveStartingYear(JsonNode apiCallResult) {
        return resolveContent(apiCallResult).get("koulutuksenAlkamisvuosi").intValue();
    }

    public String resolveStartingSeasonCode(JsonNode apiCallResult) {
        return resolveCode(resolveContent(apiCallResult).get("koulutuksenAlkamiskausi"));
    }

    private JsonNode resolveContent(JsonNode apiCallResult) {
        return apiCallResult.get("result");
    }

    private List<String> resolveProviders(JsonNode node) {
        return Lists.newArrayList(node.get("opetusTarjoajat")).parallelStream()
                .map(elem -> elem.textValue())
                .collect(Collectors.toList());
    }

    private List<TeachingLanguage> resolveTeachingLanguages(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            return Lists.newArrayList(node.get("meta")).parallelStream()
                    .map(elem -> resolveTeachingLanguage(elem))
                    .collect(Collectors.toList());
        }
    }

    private TeachingLanguage resolveTeachingLanguage(JsonNode langNode) {

        if (langNode == null) {
            return null;
        }
        else {
            return new TeachingLanguage(langNode.get("arvo").textValue().toLowerCase(),
                    resolveMetaLangName(langNode));
        }
    }

    private I18N resolveDegreeProgram(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            JsonNode texts = node.get("tekstis");
            return convertToI18N(texts);
        }
    }

    private List<I18N> resolveKeywords(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            return Lists.newArrayList(node.get("meta")).parallelStream()
                    .map(elem -> resolveMetaLangName(elem))
                    .collect(Collectors.toList());
        }
    }

    private I18N resolveMetaLangName(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            JsonNode texts = node.get("meta");
            return new I18N(
                    texts.get("kieli_fi").get("nimi").textValue().replace('|', ' ').replace('#', ' '),
                    texts.get("kieli_sv").get("nimi").textValue().replace('|', ' ').replace('#', ' '),
                    texts.get("kieli_en").get("nimi").textValue().replace('|', ' ').replace('#', ' ')
            );
        }
    }

    private String resolveCode(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            return String.format("%s#%s",
                    node.get("uri").textValue(),
                    String.valueOf(node.get("versio").intValue()));
        }
    }

    private I18N resolveQualification(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            JsonNode texts = node.get("meta").elements().next().get("meta");
            return new I18N(
                    texts.get("kieli_fi").get("nimi").textValue(),
                    texts.get("kieli_sv").get("nimi").textValue(),
                    texts.get("kieli_en").get("nimi").textValue()
            );
        }
    }
}
