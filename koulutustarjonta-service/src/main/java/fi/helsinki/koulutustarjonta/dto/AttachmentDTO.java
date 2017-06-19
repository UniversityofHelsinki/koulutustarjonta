package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentDTO {
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("kieli")
    private String lang;
    @JsonProperty("tyyppi")
    private String name;
    @JsonProperty("kuvaus")
    private String description;
    @JsonProperty("erapaiva")
    private Date due;
    @JsonProperty("toimitusosoite")
    private AddressDTO address;
    @JsonProperty("vastaanottaja")
    private String receiver;
}
