package fi.helsinki.koulutustarjonta.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.converter.CodeConverter;
import fi.helsinki.koulutustarjonta.domain.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hannu Lyytikainen
 */
public class KoodistoClient {

    private static final Logger LOG = LoggerFactory.getLogger(KoodistoClient.class);

    private static final String CODE_PATH = "koodi";

    private final WebResource codeResource;
    private final CodeConverter codeConverter;

    public KoodistoClient(WebResource codeResource) {
        this.codeResource = codeResource;
        this.codeConverter = new CodeConverter();
    }

    public Code getCode(String codeUri) {
        LOG.debug(String.format("Retrieving code with uri %s", codeUri));
        String path = String.format("%s/%s/%s",
                parseCodeGroup(codeUri), CODE_PATH, codeUri);

        JsonNode json = codeResource.path(path).get(new GenericType<JsonNode>() {});
        return codeConverter.convert(json);
    }

    /**
     * Parse code group (koodisto) name from code uri.
     * @param codeUri code uri
     * @return code group name
     */
    private String parseCodeGroup(String codeUri) {
        return codeUri.split("_")[0];
    }

}
