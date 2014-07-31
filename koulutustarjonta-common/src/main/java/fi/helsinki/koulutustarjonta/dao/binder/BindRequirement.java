package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.Requirement;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindRequirement.RequirementBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindRequirement {
    public static class RequirementBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindRequirement, Requirement>()
            {
                public void bind(SQLStatement q, BindRequirement bind, Requirement requirement)
                {
                    BindUtil.bindI18N(q, "kuvaus", requirement.getDescription());
                }
            };
        }
    }
}

