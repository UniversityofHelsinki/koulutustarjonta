package fi.helsinki.koulutustarjonta.matchers;

import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.mockito.ArgumentMatcher;

public class UpdateResultMatchers {

    public static ArgumentMatcher<UpdateResult> errorsEqual(final String s) {
        return new ArgumentMatcher<UpdateResult>() {
            @Override
            public boolean matches(Object argument) {
                UpdateResult updateResult = (UpdateResult) argument;
                return updateResult.getErrors().equals(s);
            }
        };
    }

    public static ArgumentMatcher<UpdateResult> errorsContain(final String s) {
        return new ArgumentMatcher<UpdateResult>() {
            @Override
            public boolean matches(Object argument) {
                UpdateResult updateResult = (UpdateResult) argument;
                return updateResult.getErrors().contains(s);
            }
        };
    }

    public static ArgumentMatcher<UpdateResult> stateEquals(final UpdateResult.State state) {
        return new ArgumentMatcher<UpdateResult>() {
            @Override
            public boolean matches(Object argument) {
                UpdateResult updateResult = (UpdateResult) argument;
                return updateResult.getState().equals(state);
            }
        };
    }

    @SafeVarargs
    public static final ArgumentMatcher<UpdateResult> allOf(final ArgumentMatcher<UpdateResult>... matchers) {
        return new ArgumentMatcher<UpdateResult>() {
            @Override
            public boolean matches(Object argument) {
                for (ArgumentMatcher<UpdateResult> matcher : matchers) {
                    if (!matcher.matches(argument)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }
}
