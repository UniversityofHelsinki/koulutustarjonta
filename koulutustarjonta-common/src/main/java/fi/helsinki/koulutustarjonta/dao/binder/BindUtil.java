package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.I18N;
import org.skife.jdbi.v2.SQLStatement;

import java.util.Optional;

/**
 * @author Hannu Lyytikainen
 */
public class BindUtil {

    private static final I18N EMPTY_I18N = new I18N(null, null, null);

    public static void bindI18N(SQLStatement q, String field, I18N i18n) {
        Optional<I18N> optionalI18n = Optional.ofNullable(i18n);
        q.bind(String.format("%s_fi", field), optionalI18n.orElse(EMPTY_I18N).getFi());
        q.bind(String.format("%s_sv", field), optionalI18n.orElse(EMPTY_I18N).getSv());
        q.bind(String.format("%s_en", field), optionalI18n.orElse(EMPTY_I18N).getEn());
    }


}
