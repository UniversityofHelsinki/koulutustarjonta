package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Code;
import fi.helsinki.koulutustarjonta.domain.I18N;

/**
 * @author Hannu Lyytikainen
 */
public abstract class BaseWrapper {

    private final KoodistoClient koodistoClient;

    protected BaseWrapper(KoodistoClient koodistoClient) {
        this.koodistoClient = koodistoClient;
    }

    public Code getCode(String uri) {
        return koodistoClient.getCode(uri);
    }

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
        String fi = null;
        String sv = null;
        String en = null;

        for (String field : Lists.newArrayList(node.fieldNames())) {

            String lang = getCode(field).getValue();
            if (lang.equalsIgnoreCase("fi")) {
                fi = node.get(field).textValue();
            } else if (lang.equalsIgnoreCase("sv")) {
                sv = node.get(field).textValue();
            } else if (lang.equalsIgnoreCase("en")) {
                en = node.get(field).textValue();
            }
        }
        return new I18N(fi, sv, en);
    }

    /**
     * Resolve lang code by language's koodisto uri.
     *
     * @param langUri koodisto uri
     * @return language code
     */
    public String resolveLang(String langUri) {
        return getCode(langUri).getValue();
    }
}
