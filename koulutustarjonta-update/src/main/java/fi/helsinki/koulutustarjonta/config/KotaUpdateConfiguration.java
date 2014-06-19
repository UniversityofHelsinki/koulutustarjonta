package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.helsinki.koulutustarjonta.client.TarjontaClientFactory;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Hannu Lyytikainen
 */
public class KotaUpdateConfiguration extends Configuration {

    @Valid
    @NotNull
    private TarjontaClientFactory tarjontaClientFactory = new TarjontaClientFactory();

    @JsonProperty("tarjontaClient")
    public TarjontaClientFactory getTarjontaClientFactory() {
        return tarjontaClientFactory;
    }

    @JsonProperty("tarjontaClient")
    public void setTarjontaClientFactory(TarjontaClientFactory tarjontaClientFactory) {
        this.tarjontaClientFactory = tarjontaClientFactory;
    }

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
