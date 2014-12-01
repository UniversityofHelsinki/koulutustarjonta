package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Requirement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionConverter extends BaseConverter {

    private final ExamConverter examConverter;
    private final AttachmentConverter attachmentConverter;

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

        if (content.has("hakuaikaId")) {
            ao.setApplicationPeriod(content.get("hakuaikaId").textValue());
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

    private JsonNode resolveApplicationOptionContent(JsonNode apiCallResult) {
        return apiCallResult.get("result");
    }

}
