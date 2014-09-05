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

    public Code getCode(String codeUriAndVersion) {
        String codeUri = parseCodeUri(codeUriAndVersion);
        String version = parseVersion(codeUriAndVersion);
        String path = String.format("%s/%s/%s",
                parseCodeGroup(codeUri), CODE_PATH, codeUri);
        WebResource resource = codeResource.path(path);

        if (version != null) {
            resource = resource.queryParam("koodistoVersio", version);
        }
        JsonNode json = resource.get(new GenericType<JsonNode>() {});
        return codeConverter.convert(json);
    }

    /**
     * Parse code uri form code uri and version string,
     * such as posti_00180#1
     *
     * @param uriAndVersion
     * @return
     */
    private String parseCodeUri(String uriAndVersion) {
        return uriAndVersion.split("#")[0];
    }

    /**
     * Parse code version form code uri and version string,
     * such as posti_00180#1
     *
     * @param uriAndVersion
     * @return
     */
    private String parseVersion(String uriAndVersion) {
        String[] parts = uriAndVersion.split("#");
        if (parts.length > 1) {
            return parts[1];
        }
        else {
            return null;
        }
    }

    /**
     * Parse code group (koodisto) name from code uri, for example posti_00180.
     * @param codeUri code uri
     * @return code group name
     */
    private String parseCodeGroup(String codeUri) {
        return codeUri.split("_")[0];
    }

}
