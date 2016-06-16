package fi.helsinki.koulutustarjonta.dao;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;
import fi.helsinki.koulutustarjonta.domain.I18N;
import oracle.jdbc.pool.OracleDataSource;
import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * @author Hannu Lyytikainen
 */
public class BaseDAOTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseDAOTest.class);
    OracleDataSource ds;
    DBI dbi;

    @Before
    public void baseInit() throws Exception {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");

        LOG.info(String.format("DB url %s", url));
        LOG.info(String.format("DB user %s", user));


        TimeZone tz = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(tz);

        ds = new OracleDataSource();
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(passwd);

        executeSqlFile(ds, "db/populate_test_db.sql");

        dbi = new DBI(url, user, passwd);

    }

    @After
    public void baseDestroy() throws Exception {
        executeSqlFile(ds, "db/delete_test_data.sql");
        ds.close();
    }

    private void executeSqlFile(DataSource dataSource, String file) throws Exception {

        String populate = Resources.toString(Resources.getResource(file), Charsets.UTF_8).trim();
        Iterable<String> populateCommands = Splitter.on(';')
                .trimResults()
                .omitEmptyStrings()
                .split(populate);
        populateCommands.forEach(command ->
        {
            LOG.debug(String.format("Executing sql command \n%s", command));
            try {
                dataSource.getConnection().prepareStatement(command).execute();
            } catch (Exception e) {

                LOG.error(String.format("Error executing sql file, command %s", command), e);
                throw new RuntimeException(e);
            }
        });
    }

    public void i18NEquals(I18N expected, I18N actual) {
        if (expected == null && actual == null )
            return;

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
