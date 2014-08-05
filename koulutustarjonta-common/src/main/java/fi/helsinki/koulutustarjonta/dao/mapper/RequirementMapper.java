package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.Requirement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class RequirementMapper implements ResultSetMapper<Requirement> {
    @Override
    public Requirement map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        if (r.getString("hk_id") == null) {
            return null;
        }
        else {
            Requirement req = new Requirement();
            req.setId(r.getLong("hk_id"));
            req.setDescription(MapperUtil.resolveI18N(r, "hk_kuvaus"));
            return req;
        }
    }
}
