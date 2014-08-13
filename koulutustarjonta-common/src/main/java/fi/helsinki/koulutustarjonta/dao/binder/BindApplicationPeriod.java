package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindApplicationPeriod.ApplicationPeriodBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindApplicationPeriod {
    static final Logger LOG = Logger.getLogger(BindApplicationPeriod.class);
    public static class ApplicationPeriodBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindApplicationPeriod, ApplicationPeriod>()
            {

                public void bind(SQLStatement q, BindApplicationPeriod bind, ApplicationPeriod ap)
                {
                    q.bind("id", ap.getId());
                    q.bind("nimi", ap.getName());
                    q.bind("alkaa", ap.getStarts());
                    q.bind("loppuu", ap.getEnds());
                }
            };
        }
    }
}

