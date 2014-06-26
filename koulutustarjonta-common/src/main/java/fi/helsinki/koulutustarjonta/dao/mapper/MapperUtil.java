package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.I18N;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class MapperUtil {

    public static I18N resolveI18N(ResultSet r, String field) throws SQLException {
        String fi = r.getString(String.format("%s_fi", field));
        String sv = r.getString(String.format("%s_sv", field));
        String en = r.getString(String.format("%s_en", field));
        if (fi == null && sv == null && en == null) {
            return null;
        } else {
            return new I18N(fi, sv, en);
        }
    }
}
