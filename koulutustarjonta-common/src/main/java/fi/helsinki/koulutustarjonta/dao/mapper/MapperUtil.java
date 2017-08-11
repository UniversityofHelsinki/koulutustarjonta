package fi.helsinki.koulutustarjonta.dao.mapper;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.I18N;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class MapperUtil {

    public static Integer resolveInteger(ResultSet r, String field) throws SQLException {
        Integer i = new Integer(r.getInt(field));
        if (r.wasNull()) {
            return null;
        } else {
            return i;
        }
    }

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


    public static List<I18N> resolveI18NList(ResultSet r, String field) throws SQLException {
        List<I18N> i = Lists.newArrayList();
        String src = r.getString(field);

        if (src == null)
            return i;

        for (String s : src.split("\\|")) {
            String[] al = s.split("#");

            if (al.length != 3)
                continue;

            i.add(new I18N(al[0], al[1], al[2]));
        }

        return i;
    }

    public static List<String> resolveStringList(ResultSet r, String field) throws SQLException {
        String s = r.getString(field);

        if (s == null) {
            return Lists.newArrayList();
        } else {
            return Lists.newArrayList(s.split("\\|"));
        }
    }
}
