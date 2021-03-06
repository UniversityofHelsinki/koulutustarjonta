package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hannu Lyytikainen
 */
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactInfoDTO {
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("kieli")
    private String lang;
    @JsonProperty("www")
    private String www;
    @JsonProperty("email")
    private String email;
    @JsonProperty("puhelin")
    private String phone;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("postiosoite")
    private AddressDTO postalAddress;
}
