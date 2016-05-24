package fi.helsinki.koulutustarjonta.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Builder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class LOContactDTO {
    @JsonProperty("nimi")
    private String name;

    @JsonProperty("kielet")
    private List<String> languages;

    @JsonProperty("sahkoposti")
    private String email;

    @JsonProperty("puhelin")
    private String phoneNumber;

    @JsonProperty("titteli")
    private String title;

    @JsonProperty("henkiloTyyppi")
    private String contactType;
}