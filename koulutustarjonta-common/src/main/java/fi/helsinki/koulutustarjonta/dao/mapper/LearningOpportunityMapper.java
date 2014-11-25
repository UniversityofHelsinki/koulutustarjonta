package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.resolveI18N;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityMapper implements ResultSetMapper<LearningOpportunity> {
    @Override
    public LearningOpportunity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        LearningOpportunity lo = new LearningOpportunity(
                r.getString("id"), resolveI18N(r, "tutkintonimike"), resolveI18N(r, "opintoala"),
                resolveI18N(r, "tutkintoohjelma"), r.getInt("alkamisvuosi"), resolveI18N(r, "alkamiskausi"),
                r.getString("suunni_kesto"), resolveI18N(r, "suunni_tyyppi"),
                r.getInt("laajuus"), resolveI18N(r, "laajuus_tyyppi"),
                null, null, resolveI18N(r, "tavoitteet"), resolveI18N(r, "rakenne"),
                resolveI18N(r, "mahdollisuudet"), resolveI18N(r, "patevyys"), resolveI18N(r, "lisat_opkiel"),
                resolveI18N(r, "yhteistyo"), resolveI18N(r, "paaaineval"), resolveI18N(r, "kansval"),
                resolveI18N(r, "sijtyo"), resolveI18N(r, "sisalto"), resolveI18N(r, "tutkpaino"),
                resolveI18N(r, "opinnaytetyo"), null, null, null, null
        );
        return lo;
    }
}
