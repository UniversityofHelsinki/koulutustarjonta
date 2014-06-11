package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.client.TarjontaClient;

/**
 * @author Hannu Lyytikainen
 */
public class Updater {

    private TarjontaClient tarjontaClient;

    public Updater(TarjontaClient tarjontaClient) {
        this.tarjontaClient = tarjontaClient;
    }

    public void update() {
        tarjontaClient.getDegree("1.2.246.562.17.17939899864");
    }
}
