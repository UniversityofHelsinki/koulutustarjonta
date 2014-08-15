package fi.helsinki.koulutustarjonta.dto;

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
public class SeasonDTO {
    @JsonProperty("arvo")
    private String value;
    @JsonProperty("nimi")
    private I18NDTO name;
}
