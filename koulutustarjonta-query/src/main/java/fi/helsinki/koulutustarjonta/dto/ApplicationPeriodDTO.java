package fi.helsinki.koulutustarjonta.dto;

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
public class ApplicationPeriodDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("nimi")
    private I18NDTO name;
    @JsonProperty("alkaa")
    private Date starts;
    @JsonProperty("loppuu")
    private Date ends;
}
