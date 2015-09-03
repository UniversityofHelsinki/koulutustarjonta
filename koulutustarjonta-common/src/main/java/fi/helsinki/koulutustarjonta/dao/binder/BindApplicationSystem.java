package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;

import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.sql.Types;


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
                    if (as.getApplicationSeason() == null) {
                        q.bindNull("hakukausi_arvo", Types.VARCHAR);
                        BindUtil.bindI18N(q, "hakukausi", null);
                    }
                    else {
                        q.bind("hakukausi_arvo", as.getApplicationSeason().getValue());
                        BindUtil.bindI18N(q, "hakukausi", as.getApplicationSeason().getName());
                    }
                    q.bind("koul_alk_vuosi", as.getEducationStartYear());
                    if (as.getEducationStartSeason() == null) {
                        q.bindNull("koul_alk_kausi_arvo", Types.VARCHAR);
                        BindUtil.bindI18N(q, "koul_alk_kausi", null);
                    }
                    else {
                        q.bind("koul_alk_kausi_arvo", as.getEducationStartSeason().getValue());
                        BindUtil.bindI18N(q, "koul_alk_kausi", as.getEducationStartSeason().getName());
                    }
                    q.bind("hakulomake_url", as.getFormUrl());
                }
            };
        }
    }
}

