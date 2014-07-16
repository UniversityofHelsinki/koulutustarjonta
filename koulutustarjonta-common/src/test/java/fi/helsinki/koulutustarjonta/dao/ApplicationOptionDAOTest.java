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
    String oid1 = "1.2.3";


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
        h.execute(String.format("DELETE FROM hakukohde WHERE id = '%s'", oid1));
    }


    @Test
    public void testSave() {
        ApplicationOption ao = Fixture.applicationOption(oid1);
        dao.save(ao);

    }

}
