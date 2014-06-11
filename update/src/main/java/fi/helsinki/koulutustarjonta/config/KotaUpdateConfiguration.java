package fi.helsinki.koulutustarjonta.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.helsinki.koulutustarjonta.client.TarjontaClientFactory;
import io.dropwizard.Configuration;

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
}
