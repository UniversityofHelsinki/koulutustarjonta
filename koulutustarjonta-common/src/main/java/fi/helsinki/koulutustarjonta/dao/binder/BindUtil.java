package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.I18N;
import org.skife.jdbi.v2.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Hannu Lyytikainen
 */
public class BindUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BindUtil.class);
    private static final I18N EMPTY_I18N = new I18N(null, null, null);
    private static final int CLOB_LIMIT = 1000;

    public static void bindI18N(SQLStatement q, String fieldPrefix, I18N i18n) {
        Optional<I18N> optionalI18n = Optional.ofNullable(i18n);
        bindText(q, String.format("%s_fi", fieldPrefix), optionalI18n.orElse(EMPTY_I18N).getFi());
        bindText(q, String.format("%s_sv", fieldPrefix), optionalI18n.orElse(EMPTY_I18N).getSv());
        bindText(q, String.format("%s_en", fieldPrefix), optionalI18n.orElse(EMPTY_I18N).getEn());
    }

    public static void bindText(SQLStatement q, String field, String text) {
        if (text != null && text.length() >= CLOB_LIMIT) {
            bindClob(q, field, text);
        } else {
            q.bind(field, text);
        }
    }

    public static void bindClob(SQLStatement q, String field, String text) {
        Clob clob = null;
        try {
            clob = q.getContext().getConnection().createClob();
            clob.setString(1, text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        q.bind(field, clob);
    }

}
