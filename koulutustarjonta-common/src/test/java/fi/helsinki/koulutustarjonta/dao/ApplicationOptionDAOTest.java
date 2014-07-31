package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionDAOTest extends BaseDAOTest {

    DBI dbi;
    ApplicationOptionDAO dao;
    ApplicationOption fixture1 = Fixture.applicationOption();

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
    public void testSave() {
        dao.save(fixture1);

    }

}
