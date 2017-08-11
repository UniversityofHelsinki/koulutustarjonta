package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.LOContact;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(BindLOContact.LOContactBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindLOContact {
    static final Logger LOG = Logger.getLogger(BindLOContact.class);
    public static class LOContactBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation){
            return new Binder<BindLOContact, LOContact>() {

                public void bind(SQLStatement q, BindLOContact bind, LOContact c){
                    q.bind("id", c.getId());
                    q.bind("nimi", c.getName());
                    q.bind("tyyppi", c.getContactType());
                    q.bind("titteli", c.getTitle());
                    q.bind("puhelinnumero", c.getPhoneNumber());
                    q.bind("email", c.getEmail());
                    q.bind("kieli", StringUtils.join(c.getLanguages(), '|'));
                }
            };
        }
    }
}
