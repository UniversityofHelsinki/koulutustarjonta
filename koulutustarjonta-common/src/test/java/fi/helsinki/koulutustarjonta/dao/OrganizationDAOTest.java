package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.OrganizationJDBI;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationDAOTest extends BaseDAOTest {

    OrganizationDAO dao;
    final Organization fixture = Fixture.organization();

    @Before
    public void init() {
        dao = new OrganizationDAO(dbi.onDemand(OrganizationJDBI.class));
    }

    @After
    public void destroy() {
        Handle h = dbi.open();
        h.execute("DELETE FROM organisaatio WHERE id = ?", fixture.getOid());
        dbi.close(h);
    }

    @Test
    public void testSave() {
        dao.save(fixture);
    }


}
