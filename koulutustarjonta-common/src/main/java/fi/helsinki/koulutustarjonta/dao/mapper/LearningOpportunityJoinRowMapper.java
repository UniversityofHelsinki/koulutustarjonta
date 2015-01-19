package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.LearningOpportunityJoinRow;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.resolveI18N;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityJoinRowMapper implements ResultSetMapper<LearningOpportunityJoinRow> {
    @Override
    public LearningOpportunityJoinRow map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        TeachingLanguage teachingLanguage = TeachingLanguageMapper.map(r);
        LearningOpportunity lo = new LearningOpportunity(
                r.getString("id"), resolveI18N(r, "tutkintonimike"), resolveI18N(r, "opintoala"),
                resolveI18N(r, "tutkintoohjelma"), resolveI18N(r, "koulutusaste"), r.getInt("alkamisvuosi"), resolveI18N(r, "alkamiskausi"),
                r.getString("suunni_kesto"), resolveI18N(r, "suunni_tyyppi"),
                r.getInt("laajuus"), resolveI18N(r, "laajuus_tyyppi"),
                null, resolveI18N(r, "tavoitteet"), resolveI18N(r, "rakenne"),
                resolveI18N(r, "mahdollisuudet"), resolveI18N(r, "patevyys"), resolveI18N(r, "lisat_opkiel"),
                resolveI18N(r, "yhteistyo"), resolveI18N(r, "paaaineval"), resolveI18N(r, "kansval"),
                resolveI18N(r, "sijtyo"), resolveI18N(r, "sisalto"), resolveI18N(r, "tutkpaino"),
                resolveI18N(r, "opinnaytetyo"), null, r.getString("id_organisaatio"), null, null
        );
        return new LearningOpportunityJoinRow(lo, teachingLanguage, r.getString("hakukohde_id"),
                r.getString("id_vanhempi"), r.getString("id_lapsi"));
    }

}

