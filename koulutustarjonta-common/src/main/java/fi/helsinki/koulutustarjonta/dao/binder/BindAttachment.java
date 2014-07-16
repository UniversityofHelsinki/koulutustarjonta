package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.Attachment;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindAttachment.AttachmentBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindAttachment {
    public static class AttachmentBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindAttachment, Attachment>()
            {
                public void bind(SQLStatement q, BindAttachment bind, Attachment attachment)
                {
                    q.bind("id", attachment.getOid());
                    q.bind("kieli", attachment.getLang());
                    q.bind("nimi", attachment.getName());
                    q.bind("kuvaus", attachment.getDescription());
                    q.bind("erapaiva", attachment.getDue());
                    q.bind("osoite", attachment.getAddress().getStreet());
                    q.bind("postinumero", attachment.getAddress().getPostalCode());
                    q.bind("ptoimipaikka", attachment.getAddress().getPostOffice());
                }
            };
        }
    }
}

