package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.Exam;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class ExamMapper implements ResultSetMapper<Exam> {

    @Override
    public Exam map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Exam exam = new Exam();
        exam.setOid(r.getString("v_id"));
        exam.setLang(r.getString("v_kieli"));
        exam.setType(r.getString("v_tyyppi"));
        exam.setDescription(r.getString("v_kuvaus"));
        return exam;
    }
}
