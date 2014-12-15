package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class KotaUpdateConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("tarjontaClient")
    private TarjontaClientFactory tarjontaClientFactory = new TarjontaClientFactory();

    @Valid
    @NotNull
    @JsonProperty("koodistoClient")
    private KoodistoClientFactory koodistoClientFactory = new KoodistoClientFactory();

    @Valid
    @NotNull
    @JsonProperty("organisaatioClient")
    private OrganisaatioClientFactory organisaatioClientFactory = new OrganisaatioClientFactory();

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("sundial")
    private SundialConfiguration sundial = new SundialConfiguration();

    @Valid
    @NotNull
    @JsonProperty("updateCron")
    private String updateCron;

}
