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
public class ExamEventDTO {
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("alkaa")
    private Date starts;
    @JsonProperty("loppuu")
    private Date ends;
    @JsonProperty("kuvaus")
    private String info;
    @JsonProperty("osoite")
    private AddressDTO address;
}
