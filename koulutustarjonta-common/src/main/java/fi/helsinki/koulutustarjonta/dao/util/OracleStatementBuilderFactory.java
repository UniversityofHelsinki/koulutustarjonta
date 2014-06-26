package fi.helsinki.koulutustarjonta.dao.util;

import org.skife.jdbi.v2.tweak.StatementBuilder;
import org.skife.jdbi.v2.tweak.StatementBuilderFactory;

import java.sql.Connection;

/**
 * @author Hannu Lyytikainen
 */
public class OracleStatementBuilderFactory implements StatementBuilderFactory {
    @Override
    public StatementBuilder createStatementBuilder(Connection conn) {
        return new OracleStatementBuilder();
    }
}
