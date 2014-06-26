package fi.helsinki.koulutustarjonta.dao.binder;

        import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindTeachingLanguage.TeachingLanguageBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindTeachingLanguage {
    public static class TeachingLanguageBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindTeachingLanguage, TeachingLanguage>()
            {
                public void bind(SQLStatement q, BindTeachingLanguage bind, TeachingLanguage tl)
                {
                    q.bind("id", tl.getLang());
                    q.bind("kieli", tl.getLang());
                    q.bind("selite_fi", tl.getName().getFi());
                    q.bind("selite_sv", tl.getName().getSv());
                    q.bind("selite_en", tl.getName().getEn());
                }
            };
        }
    }
}
