package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Requirement;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionConverter extends BaseConverter {

    private final ExamConverter examConverter;
    private final AttachmentConverter attachmentConverter;
    static final Logger LOG = Logger.getLogger(ApplicationOptionConverter.class);

    public ApplicationOptionConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
        this.examConverter = new ExamConverter(koodistoClient);
        this.attachmentConverter = new AttachmentConverter(koodistoClient);
    }

    public ApplicationOption convert(JsonNode apiCallResult) {
        ApplicationOption ao = new ApplicationOption();
        JsonNode content = resolveApplicationOptionContent(apiCallResult);
        ao.setOid(content.get("oid").textValue());
        ao.setName(convertToI18N(content.get("hakukohteenNimet")));
        ao.setStartingQuota(content.get("aloituspaikatLkm").intValue());
        ao.setStartingQuotaDescription(convertToI18N(content.get("aloituspaikatKuvaukset")));
        ao.setRequirements(
                Lists.newArrayList(content.get("hakukelpoisuusvaatimusUris")).stream()
                        .map(uri -> new Requirement(getCode(uri.textValue()).getName()))
                        .collect(Collectors.toList())
        );
        ao.setRequirementDescription(convertToI18N(content.get("hakukelpoisuusVaatimusKuvaukset")));
        ao.setAdditionalInfo(convertToI18N(content.get("lisatiedot")));
        ao.setSelectionCriteria(convertToI18N(content.get("valintaperusteKuvaukset")));
        ao.setSora(convertToI18N(content.get("soraKuvaukset")));
        ao.setExams(Lists.newArrayList(content.get("valintakokeet")).parallelStream()
                .map(exam -> examConverter.convert(exam))
                .collect(Collectors.toList()));
        ao.setAttachments(Lists.newArrayList(content.get("hakukohteenLiitteet")).parallelStream()
                .map(attach -> attachmentConverter.convert(attach))
                .collect(Collectors.toList()));
        ao.setApplicationSystem(content.get("hakuOid").textValue());
        ao.setAoFormUrl(null);

        if (content.has("hakuaikaId")) {
            ao.setApplicationPeriodId(content.get("hakuaikaId").textValue());
        }

        if (content.has("ensikertalaistenAloituspaikat")) {
            ao.setFirstTimePositions(content.get("ensikertalaistenAloituspaikat").intValue());
        }

        // This string is checked for in class ApplicationOptionModelMapper. And if AoFormUrl equals 'ataruFormUrl',
        // then a correct URL is set, otherwise another kind of URL is set. This kind of a workaround is used, because
        // for some reason, the URL is not saved in the database and is only defined after a query to the API is made.
        // It would probably make more sense to save the correct URL in the database, but the process of defining it was
        // using several different classes and refactoring the logic to be included here might have introduced some errors.
        // As a result, I implemented this quick workaround, because the planned lifetime of this application is near its end.
        if (content.has("ataruLomakeAvain")) {
            ao.setAoFormUrl("ataruFormUrl");
        }

        return ao;
    }

    /**
     * Resolves oids from list returned by application option search by learning opportunity.
     *
     * @param apiCallResult
     * @return oid list
     */
    public List<String> resolveOids(JsonNode apiCallResult) {
        return Lists.newArrayList(apiCallResult.get("result"))
                .stream()
                .map(node -> node.get("oid").textValue())
                .collect(Collectors.toList());
    }

    /**
     * Checks if application option state is "JULKAISTU"
     * @param apiCallResult
     * @return
     */
    public boolean isApplicationOptionReleased(JsonNode apiCallResult) {
        JsonNode content = resolveApplicationOptionContent(apiCallResult);
        return content.get("tila").textValue().equals("JULKAISTU");

    }

    /**
     * Resolves an application option oid from a resource returned by OPH API.
     * @param apiResult json doc
     * @return oid
     */
    public String resolveOid(JsonNode apiResult) {
        return resolveApplicationOptionContent(apiResult).get("oid").textValue();
    }

    private JsonNode resolveApplicationOptionContent(JsonNode apiCallResult) {
        return apiCallResult.get("result");
    }

    private String createFormUrl(String oid) {
        return String.format("https://opintopolku.fi/hakuperusteet/ao/%s", oid);
    }

}
