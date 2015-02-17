package fi.helsinki.koulutustarjonta.core;

import fi.helsinki.koulutustarjonta.core.converter.UpdateResultConverter;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateResultConverterTest {

    private UpdateResultConverter updateResultConverter = new UpdateResultConverter();

    @Test
    public void thatUpdateResultWithEmptyErrorsIsConverted() {
        long started = System.currentTimeMillis();
        Result result = new Result(started);

        UpdateResult updateResult = updateResultConverter.toUpdateResult(result);

        assertEquals(started, updateResult.getStarted().getTime());
        assertEquals("[]", updateResult.getErrors());
        assertEquals(UpdateResult.State.OK, updateResult.getState());
    }

    @Test
    public void thatUpdateResultWithErrorsIsConverted() {
        long started = System.currentTimeMillis();
        Result result = new Result(started);
        result.addError("Could not update 1");
        result.addError("Could not update 2");

        UpdateResult updateResult = updateResultConverter.toUpdateResult(result);

        assertEquals(started, updateResult.getStarted().getTime());
        assertEquals("[\"Could not update 1\",\"Could not update 2\"]", updateResult.getErrors());
        assertEquals(UpdateResult.State.ERROR, updateResult.getState());
    }
}
