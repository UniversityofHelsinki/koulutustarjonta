package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.Address;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class ExamEventMapper implements ResultSetMapper<ExamEvent> {

    @Override
    public ExamEvent map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        if (r.getString("ak_id") == null) {
            return null;
        }
        else {
            ExamEvent event = new ExamEvent();
            event.setOid(r.getString("ak_id"));
            event.setStarts(r.getDate("ak_alkaa"));
            event.setEnds(r.getDate("ak_loppuu"));
            event.setInfo(r.getString("ak_kuvaus"));
            Address address = new Address();
            address.setStreet(r.getString("ak_osoite"));
            address.setPostalCode(r.getString("ak_postinumero"));
            address.setPostOffice(r.getString("ak_ptoimipaikka"));
            event.setAddress(address);
            return event;
        }
    }
}
