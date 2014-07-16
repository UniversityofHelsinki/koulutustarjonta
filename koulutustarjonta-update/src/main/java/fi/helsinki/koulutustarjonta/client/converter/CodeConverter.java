package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.Code;
import fi.helsinki.koulutustarjonta.domain.I18N;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class CodeConverter {

    public Code convert(JsonNode json) {
        Code code = new Code();
        code.setUri(json.get("koodiUri").textValue());
        code.setValue(json.get("koodiArvo").textValue());

        JsonNode metadata = json.get("metadata");
        Map<String, List<JsonNode>> texts = Lists.newArrayList(metadata).stream()
                .collect(Collectors.groupingBy(meta -> meta.get("kieli").textValue().toLowerCase()));
        Optional<JsonNode> fi = texts.containsKey("fi") ? Optional.of(texts.get("fi").get(0)) : Optional.empty();
        Optional<JsonNode> sv = texts.containsKey("sv") ? Optional.of(texts.get("sv").get(0)) : Optional.empty();
        Optional<JsonNode> en = texts.containsKey("en") ? Optional.of(texts.get("en").get(0)) : Optional.empty();

        code.setName(new I18N(
                fi.map(x -> x.get("nimi").textValue()).orElse(null),
                sv.map(x -> x.get("nimi").textValue()).orElse(null),
                en.map(x -> x.get("nimi").textValue()).orElse(null)));
        code.setShortName(new I18N(
                fi.map(x -> x.get("lyhytNimi").textValue()).orElse(null),
                sv.map(x -> x.get("lyhytNimi").textValue()).orElse(null),
                en.map(x -> x.get("lyhytNimi").textValue()).orElse(null)));
        code.setDescription(new I18N(
                fi.map(x -> x.get("kuvaus").textValue()).orElse(null),
                sv.map(x -> x.get("kuvaus").textValue()).orElse(null),
                sv.map(x -> x.get("kuvaus").textValue()).orElse(null)));

        return code;
    }
}

