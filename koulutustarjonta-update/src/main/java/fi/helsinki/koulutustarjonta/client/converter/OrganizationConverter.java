package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.*;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationConverter extends BaseConverter {

    private final AddressConverter addressConverter;

    public OrganizationConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
        this.addressConverter = new AddressConverter(koodistoClient);
    }

    public Organization convert(JsonNode node) {
        JsonNode metadata = node.get("metadata").get("data");
        return new Organization(
                node.get("oid").textValue(),
                convertName(node.get("nimi")),
                convertToI18N(metadata.get("YLEISKUVAUS")),
                convertToI18N(metadata.get("KUSTANNUKSET")),
                convertToI18N(metadata.get("KANSAINVALISET_KOULUTUSOHJELMAT")),
                convertToI18N(metadata.get("OPISKELIJALIIKKUVUUS")),
                convertToI18N(metadata.get("OPPIMISYMPARISTO")),
                new Some(convertToI18N(metadata.get("FACEBOOK")),
                        convertToI18N(metadata.get("TWITTER")),
                        convertToI18N(metadata.get("GOOGLE_PLUS")),
                        convertToI18N(metadata.get("LINKED_IN"))),
                convertContactInfos(node.get("yhteystiedot")),
                convertContactInfos(node.get("metadata").get("yhteystiedot"))
        );
    }

    private List<ContactInfo> convertContactInfos(JsonNode contactInfosNode) {
        return Lists.newArrayList(contactInfosNode).stream()
                .collect(groupingBy(info -> info.get("kieli").textValue()))
                .entrySet()
                .stream()
                .map(entry -> convertContactInfo(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    private ContactInfo convertContactInfo(String langUri, List<JsonNode> contactInfoNodes) {
        String lang = getCode(langUri).getValue();
        String oid = null;
        String www = null;
        String phone = null;
        String email = null;
        String fax = null;
        Address visitingAddress = null;
        Address postalAddress = null;
        for (JsonNode infoNode : contactInfoNodes) {
            if (infoNode.has("osoiteTyyppi")) {
                String addressType = infoNode.get("osoiteTyyppi").textValue();
                if (addressType.equals("kaynti")) {
                    // visiting address oid is the address identifier
                    oid = infoNode.get("yhteystietoOid").textValue();
                    visitingAddress = addressConverter.convertOrganizationAddress(infoNode);
                }
                else if (addressType.equals("posti")) {
                    postalAddress = addressConverter.convertOrganizationAddress(infoNode);
                }
            }
            else if (infoNode.has("tyyppi")) {
                String type = infoNode.get("tyyppi").textValue();
                if (type.equals("faksi")) {
                    fax = infoNode.get("numero").textValue();
                }
                else if (type.equals("puhelin")) {
                    phone = infoNode.get("numero").textValue();
                }
            }
            else if (infoNode.has("email")) {
                email = infoNode.get("email").textValue();
            }
            else if (infoNode.has("www")) {
                www = infoNode.get("www").textValue();
            }
        }
        return new ContactInfo(oid, lang, www, phone, email,
                fax, visitingAddress, postalAddress);
    }



    private I18N convertName (JsonNode nameNode) {
        return new I18N(
                nameNode.get("fi") == null ? null : nameNode.get("fi").textValue(),
                nameNode.get("sv") == null ? null : nameNode.get("sv").textValue(),
                nameNode.get("en") == null ? null : nameNode.get("en").textValue()
        );
    }
}
