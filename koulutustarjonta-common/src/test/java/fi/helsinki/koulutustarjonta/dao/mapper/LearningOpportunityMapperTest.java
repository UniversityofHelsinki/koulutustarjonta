package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityMapperTest {

    @Test
    public void testMap() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        StatementContext ctx = mock(StatementContext.class);
        when(rs.getString(eq("id"))).thenReturn("1.2.3.4");
        when(rs.getString(eq("tavoitteet_fi"))).thenReturn("tavoitteet fi");

        LearningOpportunityMapper mapper = new LearningOpportunityMapper();

        LearningOpportunity lo = mapper.map(0, rs, ctx);
        assertNotNull(lo);
        assertEquals("1.2.3.4", lo.getOid());
        assertEquals("tavoitteet fi", lo.getGoals().getFi());
    }

}
