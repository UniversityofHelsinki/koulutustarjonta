package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.ApplicationSystemJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.domain.Season;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemJoinRowMapper implements ResultSetMapper<ApplicationSystemJoinRow> {

    private final ApplicationPeriodMapper applicationPeriodMapper;

    public ApplicationSystemJoinRowMapper() {
        applicationPeriodMapper = new ApplicationPeriodMapper();
    }

    @Override
    public ApplicationSystemJoinRow map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Season applicationSeason = new Season(r.getString("hakukausi_arvo"),
                MapperUtil.resolveI18N(r, "hakukausi"));
        Season educationSeason = new Season(r.getString("koul_alk_kausi_arvo"),
                MapperUtil.resolveI18N(r, "koul_alk_kausi"));

        ApplicationSystem as = new ApplicationSystem(
                r.getString("id"), MapperUtil.resolveI18N(r, "nimi"),
                MapperUtil.resolveI18N(r, "hakutapa"), r.getInt("hakukausi_vuosi"),
                applicationSeason, r.getInt("koul_alk_vuosi"), educationSeason,
                r.getString("hakulomake_url"), null);
        ApplicationPeriod ap = applicationPeriodMapper.map(index, r, ctx);

        return new ApplicationSystemJoinRow(as, ap);
    }
}
