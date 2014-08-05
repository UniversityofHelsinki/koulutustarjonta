package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.Address;
import fi.helsinki.koulutustarjonta.domain.Attachment;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class AttachmentMapper implements ResultSetMapper<Attachment> {
    @Override
    public Attachment map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        if (r.getString("l_id") == null) {
            return null;
        }
        else {
            Attachment attachment = new Attachment();
            attachment.setOid(r.getString("l_id"));
            attachment.setLang(r.getString("l_kieli"));
            attachment.setName(r.getString("l_nimi"));
            attachment.setDue(r.getDate("l_erapaiva"));
            attachment.setDescription(r.getString("l_kuvaus"));
            Address address = new Address();
            address.setStreet(r.getString("l_osoite"));
            address.setPostalCode(r.getString("l_postinumero"));
            address.setPostOffice(r.getString("l_ptoimipaikka"));
            attachment.setAddress(address);
            return attachment;
        }
    }
}
