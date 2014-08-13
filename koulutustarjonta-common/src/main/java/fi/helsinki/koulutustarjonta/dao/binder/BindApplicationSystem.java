package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindApplicationSystem.ApplicationSystemBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindApplicationSystem {
    static final Logger LOG = Logger.getLogger(BindApplicationSystem.class);
    public static class ApplicationSystemBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindApplicationSystem, ApplicationSystem>()
            {

                public void bind(SQLStatement q, BindApplicationSystem bind, ApplicationSystem as)
                {
                    q.bind("id", as.getOid());
                    BindUtil.bindI18N(q, "nimi", as.getName());
                    BindUtil.bindI18N(q, "hakutapa", as.getApplicationMethod());
                    q.bind("hakukausi_vuosi", as.getApplicationYear());
                    q.bind("hakukausi", as.getApplicationSeason());
                    q.bind("koul_alk_vuosi", as.getEducationStartYear());
                    q.bind("koul_alk_kausi", as.getEducationStartSeason());
                    q.bind("hakulomake_url", as.getApplicationFormUrl());
                }
            };
        }
    }
}

