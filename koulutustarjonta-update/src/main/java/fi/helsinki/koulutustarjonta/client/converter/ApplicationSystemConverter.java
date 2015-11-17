package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.config.OpintopolkuConfiguration;
import fi.helsinki.koulutustarjonta.domain.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemConverter extends BaseConverter {

    private OpintopolkuConfiguration opintopolku;

    public ApplicationSystemConverter(KoodistoClient koodistoClient, OpintopolkuConfiguration opintopolku) {
        super(koodistoClient);
        this.opintopolku = opintopolku;
    }

    public ApplicationSystem convert(JsonNode apiCallResult) {
        JsonNode root = apiCallResult.get("result");

        List<ApplicationPeriod> periods = Lists.newArrayList(root.get("hakuaikas"))
                .parallelStream()
                .map(elem -> convertApplicationPeriod(elem))
                .collect(Collectors.toList());

        Season applicationSeason = null;
        if (root.has("hakukausiUri")) {
            Code applicationSeasonCode = getCode(root.get("hakukausiUri").textValue());
            applicationSeason = new Season(applicationSeasonCode.getValue(), applicationSeasonCode.getName());

        }
        Season educationStartsSeason = null;
        if (root.has("koulutuksenAlkamiskausiUri")) {
            Code educationStartsSeasonCode = getCode(root.get("koulutuksenAlkamiskausiUri").textValue());
            educationStartsSeason = new Season(educationStartsSeasonCode.getValue(), educationStartsSeasonCode.getName());
        }

        return new ApplicationSystem(
                root.get("oid").textValue(),
                convertToI18N(root.get("nimi")),
                getCode(root.get("hakutapaUri").textValue()).getName(),
                root.get("hakukausiVuosi").intValue(),
                applicationSeason,
                root.get("koulutuksenAlkamisVuosi").intValue(),
                educationStartsSeason,
                getFormUrl(root),
                isSystemApplicationForm(root),
                periods
        );
    }

    private String getFormUrl(JsonNode root) {
        if (!isSystemApplicationForm(root) && hasApplicationForm(root) && isRedirectLink(root)) {
            return String.format("https://opintopolku.fi/hakuperusteet/ao/%s", root.get("oid").textValue());
        } else if (hasApplicationForm(root)) {
            return root.get("hakulomakeUri").textValue();
        } else {
            return null;
        }
    }

    private boolean hasApplicationForm(JsonNode root) {
        return root.hasNonNull("hakulomakeUri");
    }

    private boolean isSystemApplicationForm(JsonNode root) {
        return root.get("jarjestelmanHakulomake").booleanValue();
    }

    private boolean isRedirectLink(JsonNode root) { return root.get("hakulomakeUri").textValue().contains("redirect"); }

    private ApplicationPeriod convertApplicationPeriod(JsonNode node) {
        return new ApplicationPeriod(
                node.get("hakuaikaId").textValue(),
                convertToI18N(node.get("nimet")),
                new Date(node.get("alkuPvm").longValue()),
                new Date(node.get("loppuPvm").longValue())
        );
    }
}
