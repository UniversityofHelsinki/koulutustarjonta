package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.jdbi.UpdateResultJDBI;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Sebastian Monte
 *
 * There is currently only a single test database server that is used by these tests and
 * by the application itself. We have no guarantee of the state of the database; there may
 * be existing records that mess with our tests. Therefore, these tests only validate that the existing
 * queries do not produce any errros, so that at least the produced SQL is valid.
 */
public class UpdateResultDAOTest extends BaseDAOTest {

    private UpdateResultDAO dao;

    @Before
    public void init() {
        dao = new UpdateResultDAO(dbi.onDemand(UpdateResultJDBI.class));
    }

    @Test
    public void thatAllUpdateResultsAreFound() {
        List<UpdateResult> updateResults = dao.findLatest(Integer.MAX_VALUE);
        assertFalse(updateResults.isEmpty());
    }

    @Test
    public void thatLastUpdateResultIsFound() {
        UpdateResult last = dao.findLast();
        assertNotNull(last);
    }

    @Test
    public void thatUpdateResultIsInserted() {
        int originalCount = dao.findLatest(Integer.MAX_VALUE).size();

        UpdateResult updateResult = new UpdateResult();
        updateResult.setErrors("[\"TESTIVIRHE\"]");
        updateResult.setState(UpdateResult.State.ERROR);
        updateResult.setStarted(new DateTime().withYear(2000).toDate());

        dao.save(updateResult);

        assertEquals(originalCount + 1, dao.findLatest(Integer.MAX_VALUE).size());
    }
}
