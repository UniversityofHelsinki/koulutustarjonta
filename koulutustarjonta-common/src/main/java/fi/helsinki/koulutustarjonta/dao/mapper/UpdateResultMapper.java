package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static fi.helsinki.koulutustarjonta.domain.UpdateResult.State;

/**
 * @author Sebastian Monte
 */
public class UpdateResultMapper implements ResultSetMapper<UpdateResult> {
    @Override
    public UpdateResult map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new UpdateResult(r.getInt("id"),
                r.getTimestamp("aloitettu"),
                State.valueOf(r.getString("tila")),
                r.getString("virheet"));
    }
}
