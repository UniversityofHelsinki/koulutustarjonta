package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Address;
import fi.helsinki.koulutustarjonta.domain.Code;

/**
 * @author Hannu Lyytikainen
 */
public class AddressConverter extends BaseConverter {

    public AddressConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
    }

    /**
     * OPH tarjonta service API data model
     *
     * @param node json node
     * @return address
     */
    public Address convert(JsonNode node) {
        Address address = new Address();
        address.setStreet(node.get("osoiterivi1").textValue());
        if (node.has("postinumeroArvo")) {
            address.setPostalCode(node.get("postinumeroArvo").textValue());
        } else {
            Code postalCode = getCode(node.get("postinumero").textValue());
            address.setPostalCode(postalCode.getValue());
        }
        address.setPostOffice(node.get("postitoimipaikka").textValue());
        return address;
    }

    /**
     * OPH organisaatio API data model
     *
     * @param node json node
     * @return address
     */
    public Address convertOrganizationAddress(JsonNode node) {
        return new Address(
                node.hasNonNull("osoite") ? node.get("osoite").textValue() : null,
                node.hasNonNull("postinumeroUri") ? getCode(node.get("postinumeroUri").textValue()).getValue() : null,
                node.hasNonNull("postitoimipaikka") ? node.get("postitoimipaikka").textValue() : null);
    }
}
