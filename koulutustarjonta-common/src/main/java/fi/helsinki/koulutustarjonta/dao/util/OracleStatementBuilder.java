package fi.helsinki.koulutustarjonta.dao.util;

import org.skife.jdbi.v2.DefaultStatementBuilder;
import org.skife.jdbi.v2.StatementContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Hannu Lyytikainen
 */
public class OracleStatementBuilder extends DefaultStatementBuilder {

    /**
     * Create a new OracleStatementBuilder which gives the Oracle specific generated keys
     * column name to the statement when required.
     *
     * @param conn Used to prepare the statement
     * @param sql  Translated SQL statement
     * @param ctx  Unused
     *
     * @return a new PreparedStatement
     */
    @Override
    public PreparedStatement create(Connection conn, String sql, StatementContext ctx) throws SQLException
    {
        if (ctx.isReturningGeneratedKeys()) {
            return conn.prepareStatement(sql, new String[] {"id"});
        }
        else {
            return conn.prepareStatement(sql);
        }
    }
}
