package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpintopolkuConfiguration {
    @JsonProperty("baseUrl")
    private String baseUrl;

}
