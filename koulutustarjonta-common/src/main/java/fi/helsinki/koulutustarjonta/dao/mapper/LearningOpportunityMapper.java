package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.LOContact;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.*;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityMapper implements ResultSetMapper<LearningOpportunity> {

    private final LOContactMapper loContactMapper;

    public LearningOpportunityMapper() {
        this.loContactMapper = new LOContactMapper();
    }

    @Override
    public LearningOpportunity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        List<LOContact> info = null;
        return new LearningOpportunity(
                r.getString("id"),
                resolveI18N(r, "tutkintonimike"),
                resolveI18N(r, "opintoala"),
                resolveI18N(r, "tutkintoohjelma"),
                resolveI18N(r, "koulutusaste"),
                r.getInt("alkamisvuosi"),
                resolveI18N(r, "alkamiskausi"),
                r.getString("suunni_kesto"),
                resolveI18N(r, "suunni_tyyppi"),
                r.getString("laajuus"),
                resolveI18N(r, "laajuus_tyyppi"),
                TeachingLanguageMapper.map(r),
                resolveI18N(r, "tavoitteet"),
                resolveI18N(r, "rakenne"),
                resolveI18N(r, "mahdollisuudet"),
                resolveI18N(r, "patevyys"),
                resolveI18N(r, "lisat_opkiel"),
                resolveI18N(r, "yhteistyo"),
                resolveI18N(r, "paaaineval"),
                resolveI18N(r, "kansval"),
                resolveI18N(r, "sijtyo"),
                resolveI18N(r, "sisalto"),
                resolveI18N(r, "tutkpaino"),
                resolveI18N(r, "opinnaytetyo"),
                resolveI18N(r, "maksullisuus"),
                resolveStringList(r, "hakukohde_id"),
                resolveStringList(r, "tarjoaja_id"),
                resolveStringList(r, "id_vanhempi"),
                resolveStringList(r, "id_lapsi"),
                resolveI18NList(r, "aihe"),
                info,
                r.getBigDecimal("hinta"));
    }
}

