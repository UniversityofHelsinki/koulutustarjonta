package fi.helsinki.koulutustarjonta.dao;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;
import fi.helsinki.koulutustarjonta.domain.I18N;
import oracle.jdbc.pool.OracleDataSource;
import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * @author Hannu Lyytikainen
 */
public class BaseDAOTest {

    OracleDataSource ds;
    DBI dbi;

    @Before
    public void baseInit() throws IOException, SQLException {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");

        ds = new OracleDataSource();
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(passwd);

        executeSqlFile(ds, "db/populate_test_db.sql");

        dbi = new DBI(url, user, passwd);

    }

    @After
    public void baseDestroy() throws IOException, SQLException {
        executeSqlFile(ds, "db/delete_test_data.sql");
        ds.close();
    }

    private void executeSqlFile(DataSource dataSource, String file) throws IOException {

        String populate = Resources.toString(Resources.getResource(file), Charsets.UTF_8).trim();
        Iterable<String> populateCommands = Splitter.on(';')
                .trimResults()
                .omitEmptyStrings()
                .split(populate);
        populateCommands.forEach(command ->
        {
            try {
                dataSource.getConnection().prepareStatement(command).execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    public void i18NEquals(I18N expected, I18N actual) {
        assertEquals(expected.getFi(), actual.getFi());
        assertEquals(expected.getSv(), actual.getSv());
        assertEquals(expected.getEn(), actual.getEn());

    }

    public Calendar getZeroCalendar() {
        Calendar zeroCal = Calendar.getInstance();
        zeroCal.set(Calendar.YEAR, 0);
        zeroCal.set(Calendar.MONTH, Calendar.JANUARY);
        zeroCal.set(Calendar.DATE, 0);
        zeroCal.set(Calendar.HOUR_OF_DAY, 0);
        zeroCal.set(Calendar.MINUTE, 0);
        zeroCal.set(Calendar.SECOND, 0);
        zeroCal.set(Calendar.MILLISECOND, 0);
        return zeroCal;
    }
}
