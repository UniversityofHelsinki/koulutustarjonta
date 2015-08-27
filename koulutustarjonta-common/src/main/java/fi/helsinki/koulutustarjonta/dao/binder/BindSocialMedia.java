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
 * @author Ian Tuomi
 */
@BindingAnnotation(BindSocialMedia.OrganizationBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindSocialMedia {
    static final Logger LOG = Logger.getLogger(BindSocialMedia.class);
    public static class OrganizationBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindSocialMedia, Some>()
            {

                public void bind(SQLStatement q, BindSocialMedia bind, Some s)
                {
                    q.bind("id", s.getOid());
                    BindUtil.bindI18N(q, "facebook", s.getFacebook());
                    BindUtil.bindI18N(q, "google_plus", s.getGooglePlus());
                    BindUtil.bindI18N(q, "linkedin", s.getLinkedIn());
                    BindUtil.bindI18N(q, "twitter", s.getTwitter());
                    BindUtil.bindI18N(q, "some_other", s.getOther());
                    BindUtil.bindI18N(q, "instagram", s.getInstagram());
                    BindUtil.bindI18N(q, "youtube", s.getYoutube());
                }

            };
        }
    }
}

