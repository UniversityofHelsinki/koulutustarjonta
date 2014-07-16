package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.domain.I18N;

/**
 * @author Hannu Lyytikainen
 */
public abstract class BaseConverter {


    /**
     * Converts a json object that contains text and meta information into
     * a I18N object.
     * @param parentNode
     * @return
     */
    public I18N convertMetaTextsToI18N(JsonNode parentNode) {
        if (parentNode == null) {
            return null;
        }
        else {
            JsonNode texts = parentNode.get("tekstis");
            return convertToI18N(texts);
        }
    }

    /**
     * Convert a json node into I18N. Node contains
     * texts indentified with lang uris.
     *
     * @param node json node
     * @return I18N object
     */
    public I18N convertToI18N(JsonNode node) {
        if (node == null) {
            return null;
        }
        else {
            return new I18N(
                    node.get("kieli_fi") == null ? null : node.get("kieli_fi").textValue(),
                    node.get("kieli_sv") == null ? null : node.get("kieli_sv").textValue(),
                    node.get("kieli_en") == null ? null : node.get("kieli_en").textValue()
            );
        }
    }

    /**
     * Resolve lang code by language's koodisto uri.
     *
     * @param langUri koodisto uri
     * @return language code
     */
    public String resolveLang(String langUri) {
        if (langUri.equals("kieli_en")) {
            return "en";
        }
        else if (langUri.equals("kieli_sv")) {
            return "sv";
        }
        else {
            return "fi";
        }
    }
}
