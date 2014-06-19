package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindLearningOpportunity.LearningOpportunityBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindLearningOpportunity {
    static final Logger LOG = Logger.getLogger(BindLearningOpportunity.class);
    public static class LearningOpportunityBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindLearningOpportunity, LearningOpportunity>()
            {
                public void bind(SQLStatement q, BindLearningOpportunity bind, LearningOpportunity lo)
                {
                    LOG.debug("Bind LearningOpportunity to SQLStatement");
                    q.bind("id", lo.getOid());
                    q.bind("tavoitteet_fi", lo.getGoals().getFi());
                }
            };
        }
    }
}

