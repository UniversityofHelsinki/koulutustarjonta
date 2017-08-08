package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Attachment;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
public class AttachmentConverter extends BaseConverter {

    private final AddressConverter addressConverter;

    public AttachmentConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
        this.addressConverter = new AddressConverter(koodistoClient);
    }

    public Attachment convert(JsonNode attachmentNode) {
        Attachment attachment = new Attachment();
        attachment.setOid(attachmentNode.get("oid").textValue());
        attachment.setLang(resolveLang(attachmentNode.get("kieliUri").textValue()));
        attachment.setName(attachmentNode.get("liitteenNimi").textValue());
        if (attachmentNode.has("liitteenKuvaukset") && attachmentNode.get("liitteenKuvaukset").elements().hasNext()) {
            attachment.setDescription(attachmentNode.get("liitteenKuvaukset").elements().next().textValue());
        }
        attachment.setDue(new Date(attachmentNode.get("toimitettavaMennessa").longValue()));
        attachment.setAddress(addressConverter.convert(attachmentNode.get("liitteenToimitusOsoite")));
        JsonNode liitteenVastaanottaja = attachmentNode.get("liitteenVastaanottaja");
        if ( liitteenVastaanottaja != null) {
            attachment.setReceiver(liitteenVastaanottaja.textValue());
        }
        return attachment;
    }

}

