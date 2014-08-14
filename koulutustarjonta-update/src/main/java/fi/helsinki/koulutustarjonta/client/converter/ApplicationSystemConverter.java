package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.domain.Code;
import fi.helsinki.koulutustarjonta.domain.Season;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemConverter extends KoodistoAwareConverter {

    public ApplicationSystemConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
    }

    public ApplicationSystem convert(JsonNode apiCallResult) {
        JsonNode root = apiCallResult.get("result");

        List<ApplicationPeriod> periods = Lists.newArrayList(root.get("hakuaikas"))
                .parallelStream()
                .map(elem -> convertApplicationPeriod(elem))
                .collect(Collectors.toList());

        Code applicationSeasonCode = getCode(root.get("hakukausiUri").textValue());
        Code educationStartsSeasonCode = getCode(root.get("koulutuksenAlkamiskausiUri").textValue());

        return new ApplicationSystem(
                root.get("oid").textValue(),
                convertToI18N(root.get("nimi")),
                getCode(root.get("hakutapaUri").textValue()).getName(),
                root.get("hakukausiVuosi").intValue(),
                new Season(applicationSeasonCode.getValue(), applicationSeasonCode.getName()),
                root.get("koulutuksenAlkamisVuosi").intValue(),
                new Season(educationStartsSeasonCode.getValue(), educationStartsSeasonCode.getName()),
                root.get("hakulomakeUri").textValue(),
                periods
        );
    }

    private ApplicationPeriod convertApplicationPeriod(JsonNode node) {
        return new ApplicationPeriod(
                node.get("hakuaikaId").textValue(),
                node.get("nimi").textValue(),
                new Date(node.get("alkuPvm").longValue()),
                new Date(node.get("loppuPvm").longValue())
        );
    }
}
