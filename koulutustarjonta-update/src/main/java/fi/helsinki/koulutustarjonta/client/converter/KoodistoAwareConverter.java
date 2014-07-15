package fi.helsinki.koulutustarjonta.client.converter;

import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Code;

/**
 * @author Hannu Lyytikainen
 */
public class KoodistoAwareConverter extends BaseConverter {

    private final KoodistoClient koodistoClient;

    protected KoodistoAwareConverter(KoodistoClient koodistoClient) {
        this.koodistoClient = koodistoClient;
    }

    public Code getCode(String uri) {
        return koodistoClient.getCode(uri);
    }

}
