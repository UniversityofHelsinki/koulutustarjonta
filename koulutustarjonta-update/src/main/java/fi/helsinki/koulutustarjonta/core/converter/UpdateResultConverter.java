package fi.helsinki.koulutustarjonta.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.koulutustarjonta.core.Result;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;

import java.util.Date;

public class UpdateResultConverter {

    public UpdateResult toUpdateResult(Result result) {
        UpdateResult updateResult = new UpdateResult();
        updateResult.setStarted(new Date(result.getStarted()));
        updateResult.setState(result.hasErrors() ? UpdateResult.State.ERROR : UpdateResult.State.OK);
        try {
            tryToWriteErrors(result, updateResult);
        } catch (JsonProcessingException e) {
            updateResult.setErrors("Could not get errors as json.");
        }
        return updateResult;
    }

    private void tryToWriteErrors(Result result, UpdateResult updateResult) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        updateResult.setErrors(mapper.writeValueAsString(result.getErrors()));
    }
}
