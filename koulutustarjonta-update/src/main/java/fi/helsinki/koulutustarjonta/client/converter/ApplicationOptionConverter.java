package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;

import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionConverter extends KoodistoAwareConverter {

    private final ExamConverter examConverter;
    private final AttachmentConverter attachmentConverter;

    public ApplicationOptionConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
        this.examConverter = new ExamConverter(koodistoClient);
        this.attachmentConverter = new AttachmentConverter(koodistoClient);
    }

    public ApplicationOption convert(JsonNode apiCallResult) {
        ApplicationOption ao = new ApplicationOption();
        JsonNode content = apiCallResult.get("result");
        ao.setOid(content.get("oid").textValue());
        ao.setName(convertToI18N(content.get("hakukohteenNimet")));
        ao.setStartingQuota(content.get("aloituspaikatLkm").intValue());
        ao.setApplicationSuitabilityRequirements(
                Lists.newArrayList(content.get("hakukelpoisuusvaatimusUris")).stream()
                        .map(uri -> getCode(uri.textValue()).getName()).collect(Collectors.toList())
        );
        ao.setApplicationSuitabilityrequirementDescription(convertToI18N(content.get("hakukelpoisuusVaatimusKuvaukset")));
        ao.setAdditionalInfo(convertToI18N(content.get("lisatiedot")));
        ao.setSelectionCriteria(convertToI18N(content.get("valintaperusteKuvaukset")));
        ao.setSora(convertToI18N(content.get("soraKuvaukset")));
        ao.setExams(Lists.newArrayList(content.get("valintakokeet")).parallelStream()
                .map(exam -> examConverter.convert(exam))
                .collect(Collectors.toList()));
        ao.setAttachments(Lists.newArrayList(content.get("hakukohteenLiitteet")).parallelStream()
                .map(attach -> attachmentConverter.convert(attach))
                .collect(Collectors.toList()));
        return ao;
    }
}
