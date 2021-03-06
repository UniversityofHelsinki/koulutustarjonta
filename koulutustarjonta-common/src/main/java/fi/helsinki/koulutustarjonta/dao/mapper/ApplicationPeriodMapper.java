package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationPeriodMapper implements ResultSetMapper<ApplicationPeriod> {
    @Override
    public ApplicationPeriod map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        if (r.getString("ha_id") == null) {
            return null;
        }
        else {
            return new ApplicationPeriod(r.getString("ha_id"), MapperUtil.resolveI18N(r, "ha_nimi"),
                    r.getTimestamp("ha_alkaa"), r.getTimestamp("ha_loppuu"));
        }
    }
}
