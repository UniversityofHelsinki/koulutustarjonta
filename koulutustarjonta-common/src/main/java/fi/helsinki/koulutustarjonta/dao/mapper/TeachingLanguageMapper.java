package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.resolveI18N;

/**
 * @author Hannu Lyytikainen
 */
public class TeachingLanguageMapper {

    public static TeachingLanguage map(ResultSet r) throws SQLException {
        return new TeachingLanguage(r.getString("opetuskieli_kieli"), resolveI18N(r, "opetuskieli_selite"));
    }
}
