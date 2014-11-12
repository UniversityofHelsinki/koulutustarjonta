package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamDTO {
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("kieli")
    private String lang;
    @JsonProperty("tyyppi")
    private String type;
    @JsonProperty("kuvaus")
    private String description;
    @JsonProperty("koetapahtumat")
    private List<ExamEventDTO> events;
}
