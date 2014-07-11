package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.domain.I18N;

/**
 * @author Hannu Lyytikainen
 */
public class I18NConverter {

    /**
     * Converts a json object that contains text and meta information into
     * a I18N object.
     * @param parentNode
     * @return
     */
    public I18N convertI18N(JsonNode parentNode) {
        if (parentNode == null) {
            return null;
        }
        else {
            JsonNode texts = parentNode.get("tekstis");

            return new I18N(
                    texts.get("kieli_fi") == null ? null : texts.get("kieli_fi").textValue(),
                    texts.get("kieli_sv") == null ? null : texts.get("kieli_sv").textValue(),
                    texts.get("kieli_en") == null ? null : texts.get("kieli_en").textValue()
            );
        }
    }


}
