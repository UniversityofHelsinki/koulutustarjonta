package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.Address;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class ContactInfoMapper implements ResultSetMapper<ContactInfo> {
    @Override
    public ContactInfo map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        if (r.getString("y_id") == null) {
            return null;
        } else {
            return new ContactInfo(r.getString("y_id"), ContactInfo.TYPE.valueOf(r.getString("y_tyyppi")),
                    r.getString("y_kieli"), r.getString("y_www"), r.getString("y_puhelin"),
                    r.getString("y_email"), r.getString("y_fax"),
                    new Address(r.getString("y_kaynti_osoite"), r.getString("y_kaynti_postinumero"),
                            r.getString("y_kaynti_postitoimipaikka")),
                    new Address(r.getString("y_posti_osoite"), r.getString("y_posti_postinumero"),
                            r.getString("y_posti_postitoimipaikka"))
            );
        }
    }
}
