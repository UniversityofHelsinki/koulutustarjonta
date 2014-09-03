package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindContactInfo.ContactInfoBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindContactInfo {
    static final Logger LOG = Logger.getLogger(BindContactInfo.class);
    public static class ContactInfoBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindContactInfo, ContactInfo>()
            {

                public void bind(SQLStatement q, BindContactInfo bind, ContactInfo c)
                {
                    q.bind("id", c.getOid());
                    q.bind("tyyppi", c.getType().name());
                    q.bind("kieli", c.getLang());
                    q.bind("www", c.getWww());
                    q.bind("puhelin", c.getPhone());
                    q.bind("email", c.getEmail());
                    q.bind("fax", c.getFax());
                    q.bind("kaynti_osoite", c.getVisitingAddress().getStreet());
                    q.bind("kaynti_postinumero", c.getVisitingAddress().getPostalCode());
                    q.bind("kaynti_postitoimipaikka", c.getVisitingAddress().getPostOffice());
                    q.bind("posti_osoite", c.getPostalAddress().getStreet());
                    q.bind("posti_postinumero", c.getPostalAddress().getPostalCode());
                    q.bind("posti_postitoimipaikka", c.getPostalAddress().getPostOffice());
                }
            };
        }
    }
}

