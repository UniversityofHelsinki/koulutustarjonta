package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpintopolkuConfiguration {
    @JsonProperty("baseUrl")
    private String baseUrl;
    @JsonProperty("baseUrl_sv")
    private String baseUrl_sv;
    @JsonProperty("baseUrl_en")
    private String baseUrl_en;
}
