package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.core.Updater;
import fi.helsinki.koulutustarjonta.domain.*;
import fi.helsinki.koulutustarjonta.exception.DataUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationConverter extends BaseConverter {

    private final AddressConverter addressConverter;
    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    public OrganizationConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
        this.addressConverter = new AddressConverter(koodistoClient);
    }

    public Organization convert(JsonNode node) throws DataUpdateException {
        if (!node.has("metadata")) {
            throw new DataUpdateException("Can not parse organization, metadata field missing");
        }
        JsonNode metadata = node.get("metadata").get("data");
        return new Organization(
                node.get("oid").textValue(),
                convertName(node.get("nimi")),
                convertToI18N(metadata.get("YLEISKUVAUS")),
                convertToI18N(metadata.get("KUSTANNUKSET")),
                convertToI18N(metadata.get("KANSAINVALISET_KOULUTUSOHJELMAT")),
                convertToI18N(metadata.get("OPISKELIJALIIKKUVUUS")),
                convertToI18N(metadata.get("OPPIMISYMPARISTO")),
                convertToI18N(metadata.get("ESTEETOMYYS")),
                convertToI18N(metadata.get("VUOSIKELLO")),
                convertToI18N(metadata.get("VASTUUHENKILOT")),
                convertToI18N(metadata.get("VALINTAMENETTELY")),
                convertToI18N(metadata.get("AIEMMIN_HANKITTU_OSAAMINEN")),
                convertToI18N(metadata.get("KIELIOPINNOT")),
                convertToI18N(metadata.get("TYOHARJOITTELU")),
                new Some(node.get("oid").textValue(),
                         convertToI18N(metadata.get("sosiaalinenmedia_1#1")),
                         convertToI18N(metadata.get("sosiaalinenmedia_2#1")),
                         convertToI18N(metadata.get("sosiaalinenmedia_3#1")),
                         convertToI18N(metadata.get("sosiaalinenmedia_4#1")),
                         convertToI18N(metadata.get("sosiaalinenmedia_5#1")),
                         convertToI18N(metadata.get("sosiaalinenmedia_6#1")),
                         convertToI18N(metadata.get("sosiaalinenmedia_7#1"))
                ),
                convertContactInfos(node.get("yhteystiedot"), ContactInfo.TYPE.CONTACT),
                convertContactInfos(node.get("metadata").get("yhteystiedot"), ContactInfo.TYPE.APPLICANT)
        );
    }

    private List<ContactInfo> convertContactInfos(JsonNode contactInfosNode, ContactInfo.TYPE type) {
        return Lists.newArrayList(contactInfosNode).stream()
                .collect(groupingBy(info -> info.get("kieli").textValue()))
                .entrySet()
                .stream()
                .map(entry -> convertContactInfo(entry.getKey(), type, entry.getValue()))
                .collect(toList());
    }

    private ContactInfo convertContactInfo(String langUri, ContactInfo.TYPE type, List<JsonNode> contactInfoNodes) {
        String lang = getCode(langUri).getValue();
        String oid = null;
        String www = null;
        String phone = null;
        String email = null;
        String fax = null;
        Address visitingAddress = null;
        Address postalAddress = null;
        for (JsonNode infoNode : contactInfoNodes) {
            if (oid == null) {
                oid = infoNode.get("yhteystietoOid").textValue(); // All contacts should have an ID, but this can be replaced later by another
            }
            if (infoNode.has("osoiteTyyppi")) {
                String addressType = infoNode.get("osoiteTyyppi").textValue();
                if (addressType.equals("kaynti") || addressType.equals("ulkomainen_kaynti")) {
                    // visiting address oid is the address identifier
                    oid = infoNode.get("yhteystietoOid").textValue();
                    visitingAddress = addressConverter.convertOrganizationAddress(infoNode);
                }
                else if (addressType.equals("posti") || addressType.equals("ulkomainen_posti")) {
                    postalAddress = addressConverter.convertOrganizationAddress(infoNode);
                }
            }
            else if (infoNode.has("tyyppi")) {
                String numbetType = infoNode.get("tyyppi").textValue();
                if (numbetType.equals("faksi")) {
                    fax = infoNode.get("numero").textValue();
                }
                else if (numbetType.equals("puhelin")) {
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
        LOG.debug("CONTACT INFO TIEDOT");
        LOG.debug("oid: "+oid + " type: "+type + lang + www + phone + email + fax + visitingAddress + postalAddress);
        return new ContactInfo(oid, type, lang, www, phone, email,
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
