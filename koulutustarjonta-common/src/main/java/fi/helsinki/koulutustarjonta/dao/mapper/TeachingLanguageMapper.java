package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import com.google.common.collect.Lists;

import static fi.helsinki.koulutustarjonta.dao.mapper.MapperUtil.resolveI18N;

/**
 * @author Hannu Lyytikainen
 */
public class TeachingLanguageMapper {

    public static List<TeachingLanguage> map(ResultSet r) throws SQLException {
        List<TeachingLanguage> res = Lists.newArrayList();

        for (String s : r.getString("kieli").split("\\|")) {
            res.add(sToTeachingLanguage(s));
        }

        return res;
    }

    public static TeachingLanguage sToTeachingLanguage(String src) {
        List<String> split = Lists.newArrayList(src.split("#"));

        if (split.size() != 4)
            return null;

        return new TeachingLanguage(split.get(0), new I18N(split.get(1), split.get(2), split.get(3)));
    }
}
