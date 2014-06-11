package fi.helsinki.koulutustarjonta.client;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fi.helsinki.koulutustarjonta.client.converter.DegreeConverter;
import fi.helsinki.koulutustarjonta.client.mock.TarjontaMock;
import fi.helsinki.koulutustarjonta.core.domain.Degree;

import java.util.Map;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaClient {

    private WebResource koulutusResource;
    private DegreeConverter degreeConverter;


    public TarjontaClient(WebResource koulutusResource) {
        this.koulutusResource = koulutusResource;
        this.degreeConverter = new DegreeConverter();
    }

    public Degree getDegree(String oid) {
        Map<String, Object> koulutus = koulutusResource.path(oid).get(new GenericType<Map<String, Object>>() {});
        Degree degree = degreeConverter.convert(koulutus);

        // mostly use mock for poc
        TarjontaMock tarjontaMock = new TarjontaMock();
        Degree mockDegree = tarjontaMock.getDegree();
        mockDegree.setGoals(degree.getGoals());

        return mockDegree;

    }

}
