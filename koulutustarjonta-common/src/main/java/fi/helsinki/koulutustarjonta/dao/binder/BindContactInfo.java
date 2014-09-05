package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.ContactInfo;
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
                    if (c.getVisitingAddress() == null) {
                        q.bindNull("kaynti_osoite", Types.VARCHAR);
                        q.bindNull("kaynti_postinumero", Types.VARCHAR);
                        q.bind("kaynti_postitoimipaikka", Types.VARCHAR);
                    }
                    else  {
                        q.bind("kaynti_osoite", c.getVisitingAddress().getStreet());
                        q.bind("kaynti_postinumero", c.getVisitingAddress().getPostalCode());
                        q.bind("kaynti_postitoimipaikka", c.getVisitingAddress().getPostOffice());
                    }

                    if (c.getPostalAddress() == null) {
                        q.bindNull("posti_osoite", Types.VARCHAR);
                        q.bindNull("posti_postinumero", Types.VARCHAR);
                        q.bindNull("posti_postitoimipaikka", Types.VARCHAR);
                    }
                    else {
                        q.bind("posti_osoite", c.getPostalAddress().getStreet());
                        q.bind("posti_postinumero", c.getPostalAddress().getPostalCode());
                        q.bind("posti_postitoimipaikka", c.getPostalAddress().getPostOffice());
                    }
                }
            };
        }
    }
}

