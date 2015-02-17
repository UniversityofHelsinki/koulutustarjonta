package fi.helsinki.koulutustarjonta.core;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private long started;
    private List<String> errors = new ArrayList<>();

    public Result(long started) {
        this.started = started;
    }

    public void addError(String message) {
        errors.add(message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return ImmutableList.copyOf(errors);
    }

    public long getStarted() {
        return started;
    }

}
