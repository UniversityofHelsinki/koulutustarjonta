package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.domain.Some;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindOrganization.OrganizationBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindOrganization {
    static final Logger LOG = Logger.getLogger(BindOrganization.class);
    public static class OrganizationBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindOrganization, Organization>()
            {

                public void bind(SQLStatement q, BindOrganization bind, Organization o)
                {
                    q.bind("id", o.getOid());
                    BindUtil.bindI18N(q, "nimi", o.getName());
                    BindUtil.bindI18N(q, "kustannukset", o.getExpenses());
                    BindUtil.bindI18N(q, "kv_koulohj", o.getInternationalStudyPrograms());
                    BindUtil.bindI18N(q, "opliikkuvuus", o.getStudentTransfer());
                    BindUtil.bindI18N(q, "oppimisymparisto", o.getStudyEnvironment());
                    BindUtil.bindI18N(q, "yleiskuvaus", o.getOutline());
                    BindUtil.bindI18N(q, "saavutettavuus", o.getAccessibility());
                    BindUtil.bindI18N(q, "vuosikello", o.getYearClock());
                    BindUtil.bindI18N(q, "vastuuhenkilot", o.getPeopleInCharge());
                    BindUtil.bindI18N(q, "valintamenettely", o.getSelectionProcedure());
                    BindUtil.bindI18N(q, "aik_kokemus", o.getPreviouslyGainedExperience());
                    BindUtil.bindI18N(q, "kieliopinnot", o.getLanguageStudies());
                    BindUtil.bindI18N(q, "tyoharjoittelu", o.getInternship());
                    Some s = o.getSome();
                    BindUtil.bindI18N(q, "facebook", s.getFacebook());
                    BindUtil.bindI18N(q, "twitter", s.getTwitter());
                    BindUtil.bindI18N(q, "google_plus", s.getGooglePlus());
                    BindUtil.bindI18N(q, "linkedin", s.getLinkedIn());
                }

            };
        }
    }
}

