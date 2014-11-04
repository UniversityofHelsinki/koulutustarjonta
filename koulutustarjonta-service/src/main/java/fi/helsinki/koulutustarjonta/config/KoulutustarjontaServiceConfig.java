package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Hannu Lyytikainen
 */
@Getter
public class KoulutustarjontaServiceConfig extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty
    private String apiEndpoint;


}
