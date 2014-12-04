package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ApplicationOption;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindApplicationOption.ApplicationOptionBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindApplicationOption {
    static final Logger LOG = Logger.getLogger(BindApplicationOption.class);
    public static class ApplicationOptionBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindApplicationOption, ApplicationOption>()
            {

                public void bind(SQLStatement q, BindApplicationOption bind, ApplicationOption ao)
                {
                    q.bind("id", ao.getOid());
                    BindUtil.bindI18N(q, "nimi", ao.getName());
                    q.bind("aloituspaikat", ao.getStartingQuota());
                    BindUtil.bindI18N(q, "aloituspaikat", ao.getStartingQuotaDescription());
                    BindUtil.bindI18N(q, "hakukelp_kuvaus", ao.getRequirementDescription());
                    BindUtil.bindI18N(q, "lisatiedot", ao.getAdditionalInfo());
                    BindUtil.bindI18N(q, "valintaper", ao.getSelectionCriteria());
                    BindUtil.bindI18N(q, "sorakuvaus", ao.getSora());
                    q.bind("id_haku", ao.getApplicationSystem());
                    q.bind("id_hakuaika", ao.getApplicationPeriodId());
                }

            };
        }
    }
}

