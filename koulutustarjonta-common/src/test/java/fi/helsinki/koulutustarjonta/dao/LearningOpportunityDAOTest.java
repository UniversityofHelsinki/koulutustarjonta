package fi.helsinki.koulutustarjonta.dao;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDAOTest {

    DBI dbi;
    LearningOpportunityDAO dao;

    final String oid1 = "1.2.3";
    final String oid2 = "1.2.3.4";
    final List<String> oids = Lists.newArrayList(oid1, oid2);


    @Before
    public void setup() {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");

        dbi = new DBI(url, user, passwd);
        dbi.open().execute("INSERT INTO KOULUTUS (id, tavoitteet_fi) " +
                               "VALUES (?, ?)", oid1, "tavoitteet fi");

        dao = dbi.onDemand(LearningOpportunityDAO.class);
    }

    @After
    public void close() {
        Handle h = dbi.open();
        oids.stream().forEach((oid) -> h.execute(String.format("DELETE FROM KOULUTUS WHERE id = '%s'", oid)));
    }

    @Test
    public void testFindById() {
        LearningOpportunity lo = dao.findById(oid1);
        assertNotNull(lo);
        assertEquals(oid1, lo.getOid());
        assertEquals("tavoitteet fi", lo.getGoals().getFi());
    }

    @Test
    public void testInsert() {
        LearningOpportunity lo = new LearningOpportunity();
        lo.setOid("1.2.3.4");
        lo.setGoals(new I18N("goals in finnish", null, null));
        dao.insert(lo);

        LearningOpportunity fetched = dao.findById("1.2.3.4");
        assertNotNull(fetched);
        assertEquals("1.2.3.4", fetched.getOid());
        assertEquals("goals in finnish", lo.getGoals().getFi());
    }

}
