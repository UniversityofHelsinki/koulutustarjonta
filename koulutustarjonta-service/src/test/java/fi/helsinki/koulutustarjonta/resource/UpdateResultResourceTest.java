package fi.helsinki.koulutustarjonta.resource;

import com.sun.jersey.api.NotFoundException;
import fi.helsinki.koulutustarjonta.dao.UpdateResultDAO;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import fi.helsinki.koulutustarjonta.dto.UpdateResultDTO;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Sebastian Monte
 */
public class UpdateResultResourceTest {

    private final UpdateResultDAO dao = mock(UpdateResultDAO.class);

    private UpdateResultResource resource = new UpdateResultResource(dao);

    @Test
    public void thatLatestUpdateResultsAreFound() {
        List<UpdateResult> expectedUpdateResults = getExpectedUpdateResultDTOs();
        when(dao.findLatest(10)).thenReturn(expectedUpdateResults);

        List<UpdateResultDTO> updateResultDTOs = resource.getUpdateResults();

        assertTrue(updateResultDTOs.size() == 1);

        UpdateResultDTO updateResultDTO = updateResultDTOs.get(0);

        assertEquals(getStartedDate(), updateResultDTO.getStarted());
        assertEquals("OK", updateResultDTO.getState());
        assertEquals("[]", updateResultDTO.getErrors());
    }

    @Test
    public void thatLastUpdateStateIsReturned() {
        when(dao.findLast()).thenReturn(getLastUpdateResult());

        String state = resource.getLastUpdateState();

        assertEquals("Last-Run-status: OK", state);
    }

    @Test(expected = NotFoundException.class)
    public void thatNotFoundExceptionIsReturnedWhenUpdateResultsAreNotFound() {
        when(dao.findLast()).thenReturn(null);

        resource.getLastUpdateState();
    }

    private UpdateResult getLastUpdateResult() {
        UpdateResult updateResult = new UpdateResult();
        updateResult.setStarted(new java.sql.Date(getStartedDate().getTime()));
        updateResult.setState(UpdateResult.State.OK);
        updateResult.setErrors("[]");
        return updateResult;
    }

    public List<UpdateResult> getExpectedUpdateResultDTOs() {
        UpdateResult updateResult = new UpdateResult();
        updateResult.setStarted(new java.sql.Date(getStartedDate().getTime()));
        updateResult.setState(UpdateResult.State.OK);
        updateResult.setErrors("[]");
        return Arrays.asList(updateResult);
    }

    private Date getStartedDate() {
        return new DateTime().withYear(2015).withMonthOfYear(1).withDayOfMonth(1).withTimeAtStartOfDay().toDate();
    }
}
