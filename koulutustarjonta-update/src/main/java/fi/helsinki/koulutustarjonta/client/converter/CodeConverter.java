package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.Code;
import fi.helsinki.koulutustarjonta.domain.I18N;

import java.util.List;
import java.util.Map;
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
        JsonNode fi = texts.containsKey("fi") ? texts.get("fi").get(0) : null;
        JsonNode sv = texts.containsKey("sv") ? texts.get("sv").get(0) : null;
        JsonNode en = texts.containsKey("en") ? texts.get("en").get(0) : null;

        code.setName(new I18N(fi.get("nimi").textValue(),
                sv.get("nimi").textValue(), en.get("nimi").textValue()));
        code.setShortName(new I18N(fi.get("lyhytNimi").textValue(),
                sv.get("lyhytNimi").textValue(), en.get("lyhytNimi").textValue()));
        code.setDescription(new I18N(fi.get("kuvaus").textValue(),
                sv.get("kuvaus").textValue(), en.get("kuvaus").textValue()));

        return code;
    }
}

