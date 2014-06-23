package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDAOTest {

    LearningOpportunityDAO dao;

    @Before
    public void setup() {
        DBI dbi = new DBI("jdbc:h2:mem:test;MODE=Oracle;" +
                "INIT=runscript from 'src/test/resources/db/create_tables_test.sql'\\;runscript from 'src/test/resources/db/populate_test_db.sql'");
        dao = dbi.onDemand(LearningOpportunityDAO.class);
    }

    @Test
    public void testFindById() {
        LearningOpportunity lo = dao.findById("1.2.3");
        assertNotNull(lo);
        assertEquals("1.2.3", lo.getOid());
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
