package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ExamEvent;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindExamEvent.ExamEventBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindExamEvent {
    public static class ExamEventBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindExamEvent, ExamEvent>()
            {
                public void bind(SQLStatement q, BindExamEvent bind, ExamEvent event)
                {
                    q.bind("id", event.getOid());
                    q.bind("alkaa", event.getStarts());
                    q.bind("loppuu", event.getEnds());
                    BindUtil.bindText(q, "kuvaus", event.getInfo());
                    q.bind("osoite", event.getAddress().getStreet());
                    q.bind("postinumero", event.getAddress().getPostalCode());
                    q.bind("ptoimipaikka", event.getAddress().getPostOffice());
                }
            };
        }
    }
}

