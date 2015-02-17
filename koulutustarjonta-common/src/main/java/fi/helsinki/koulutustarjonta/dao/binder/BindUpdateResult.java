package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Sebastian Monte
 */
@BindingAnnotation(BindUpdateResult.UpdateResultBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindUpdateResult {
    static final Logger LOG = Logger.getLogger(BindUpdateResult.class);

    public static class UpdateResultBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return new Binder<BindUpdateResult, UpdateResult>() {

                public void bind(SQLStatement q, BindUpdateResult bind, UpdateResult updateResult) {
                    q.bind("aloitettu", updateResult.getStarted());
                    q.bind("tila", updateResult.getState().toString());
                    q.bind("virheet", updateResult.getErrors());
                }
            };
        }
    }
}

