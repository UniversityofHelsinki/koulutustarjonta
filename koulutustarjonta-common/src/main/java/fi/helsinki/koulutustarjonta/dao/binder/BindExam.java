package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.Exam;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindExam.ExamBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindExam {
    public static class ExamBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindExam, Exam>()
            {
                public void bind(SQLStatement q, BindExam bind, Exam exam)
                {
                    q.bind("id", exam.getOid());
                    q.bind("kieli", exam.getLang());
                    q.bind("tyyppi", exam.getType());
                    BindUtil.bindText(q, "kuvaus", exam.getDescription());
                }
            };
        }
    }
}

