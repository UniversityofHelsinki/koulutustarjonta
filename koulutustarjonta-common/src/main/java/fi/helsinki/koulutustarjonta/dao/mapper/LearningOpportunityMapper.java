package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityMapper implements ResultSetMapper<LearningOpportunity> {
    @Override
    public LearningOpportunity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        LearningOpportunity lo = new LearningOpportunity();
        lo.setOid(r.getString("id"));
        lo.setGoals(new I18N(r.getString("tavoitteet_fi"), null, null));
        return lo;
    }
}
