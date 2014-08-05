package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.domain.Address;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.domain.Exam;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionDAOTest extends BaseDAOTest {

    DBI dbi;
    ApplicationOptionDAO dao;

    final String aoOidPopulated = "2.1.2.3";
    final String aoOid1 = "ao oid 1";
    final ApplicationOption fixture1 = Fixture.applicationOption(aoOid1);

    @Before
    public void init() {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");
        dbi = new DBI(url, user, passwd);
        dao = new ApplicationOptionDAO(dbi.onDemand(ApplicationOptionJDBI.class));
    }

    @After
    public void destroy() {
        Handle h = dbi.open();
        h.execute("DELETE FROM liite WHERE id_hakukohde = ?", fixture1.getOid());
        fixture1.getExams().forEach(
                exam -> h.execute("DELETE FROM valintakoe_ak WHERE id_valintakoe = ?", exam.getOid()));
        h.execute("DELETE FROM valintakoe WHERE id_hakukohde = ?", fixture1.getOid());
        h.execute("DELETE FROM hakukohde WHERE id = ?", fixture1.getOid());
    }

    @Test
    public void testFindByOid() throws ResourceNotFound {
        ApplicationOption ao =  dao.findByOid(aoOidPopulated);
        assertNotNull(ao);
        assertEquals(aoOidPopulated, ao.getOid());
        assertEquals("nimi fi", ao.getName().getFi());
        assertEquals("nimi sv", ao.getName().getSv());
        assertEquals("nimi en", ao.getName().getEn());
        assertEquals(11, ao.getStartingQuota());
        assertEquals("sora fi", ao.getSora().getFi());
        assertEquals("sora sv", ao.getSora().getSv());
        assertEquals("sora en", ao.getSora().getEn());
        assertEquals("lisatiedot fi", ao.getAdditionalInfo().getFi());
        assertEquals("lisatiedot sv", ao.getAdditionalInfo().getSv());
        assertEquals("lisatiedot en", ao.getAdditionalInfo().getEn());
        assertEquals("valintaper fi", ao.getSelectionCriteria().getFi());
        assertEquals("valintaper sv", ao.getSelectionCriteria().getSv());
        assertEquals("valintaper en", ao.getSelectionCriteria().getEn());
        assertEquals("kuvaus fi", ao.getRequirementDescription().getFi());
        assertEquals("kuvaus sv", ao.getRequirementDescription().getSv());
        assertEquals("kuvaus en", ao.getRequirementDescription().getEn());
        assertNotNull(ao.getExams());
        Exam exam = ao.getExams().get(0);
        assertNotNull(exam);
        assertEquals("valintakoe_id1", exam.getOid());
        assertEquals("fi", exam.getLang());
        assertEquals("koetyyppi", exam.getType());
        assertEquals("kokeen kuvaus", exam.getDescription());
        assertNotNull(exam.getEvents());
        ExamEvent event = exam.getEvents().get(0);
        assertNotNull(event);
        assertEquals("ajankohta_id1", event.getOid());
        assertEquals("valintakoeajankohta kuvaus", event.getInfo());
        Address eventAddress = event.getAddress();
        assertEquals("katuosoite", eventAddress.getStreet());
        assertEquals("postinumero", eventAddress.getPostalCode());
        assertEquals("postitoimipaikka", eventAddress.getPostOffice());



    }

    @Test
    public void testSave() {
        dao.save(fixture1);
    }



}
