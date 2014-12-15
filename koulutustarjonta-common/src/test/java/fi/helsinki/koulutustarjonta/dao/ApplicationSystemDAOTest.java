package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationSystemJDBI;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystemDAOTest extends BaseDAOTest {


    ApplicationSystemDAO dao;
    final String asOid1 = "as_oid_1";
    final String asOid2 = "as_oid_2";
    final String populatedAsOid = "haku_id1";
    final ApplicationSystem withApplicationForm = Fixture.applicationSystemWithApplicationForm(asOid1);
    final ApplicationSystem withoutApplicationForm = Fixture.applicationSystemWithoutApplicationForm(asOid2);

    @Before
    public void init() {
        dao = new ApplicationSystemDAO(dbi.onDemand(ApplicationSystemJDBI.class));
    }

    @After
    public void destroy() {
        Handle h = dbi.open();
        h.execute("DELETE FROM hakuaika WHERE id_haku = ?", withApplicationForm.getOid());
        h.execute("DELETE FROM haku WHERE id = ?", withApplicationForm.getOid());
        h.execute("DELETE FROM hakuaika WHERE id_haku = ?", withoutApplicationForm.getOid());
        h.execute("DELETE FROM haku WHERE id = ?", withoutApplicationForm.getOid());
        dbi.close(h);
    }

    @Test
    public void testFindByOid() throws ResourceNotFound {
        ApplicationSystem as = dao.findByOid(populatedAsOid);
        verifyApplicationSystem(as);
    }

    @Test
    public void findAll() {
        List<ApplicationSystem> systems = dao.findAll().stream()
                .filter(system -> system.getOid().equals(populatedAsOid))
                .collect(Collectors.toList());
        assertNotNull(systems);
        assertEquals(1, systems.size());
        verifyApplicationSystem(systems.get(0));
    }

    @Test(expected = ResourceNotFound.class)
    public void testNotFound() throws ResourceNotFound {
        dao.findByOid("invalid_oid");
    }

    @Test
    public void testInsertWithApplicationForm() throws ResourceNotFound {
        dao.save(withApplicationForm);
        ApplicationSystem as = dao.findByOid(withApplicationForm.getOid());
        applicationSystemsEqual(withApplicationForm, as);
    }

    @Test
    public void testInsertWithoutApplicationForm() throws ResourceNotFound {
        dao.save(withoutApplicationForm);
        ApplicationSystem as = dao.findByOid(withoutApplicationForm.getOid());
        applicationSystemsEqual(withoutApplicationForm, as);
    }

    @Test
    public void testUpdate() throws ResourceNotFound {
        ApplicationSystem fixture2 = Fixture.applicationSystemWithApplicationForm(populatedAsOid);
        dao.save(fixture2);
        ApplicationSystem as = dao.findByOid(fixture2.getOid());
        applicationSystemsEqual(fixture2, as);
    }

    private void applicationSystemsEqual(ApplicationSystem expected, ApplicationSystem actual) {
        assertNotNull(actual);
        assertEquals(expected.getOid(), actual.getOid());
        assertEquals(expected.getOpintopolkuFormUrl(), actual.getOpintopolkuFormUrl());
        i18NEquals(expected.getName(), actual.getName());
        i18NEquals(expected.getApplicationMethod(), actual.getApplicationMethod());
        assertEquals(expected.getApplicationYear(), actual.getApplicationYear());
        assertEquals(expected.getApplicationSeason().getValue(), actual.getApplicationSeason().getValue());
        i18NEquals(expected.getApplicationSeason().getName(), actual.getApplicationSeason().getName());
        assertEquals(expected.getEducationStartYear(), actual.getEducationStartYear());
        assertEquals(expected.getEducationStartSeason().getValue(), actual.getEducationStartSeason().getValue());
        i18NEquals(expected.getEducationStartSeason().getName(), actual.getEducationStartSeason().getName());
        assertNotNull(actual.getApplicationPeriods());
        assertEquals(expected.getApplicationPeriods().size(), actual.getApplicationPeriods().size());
        assertEquals(expected.getApplicationPeriods().get(0).getId(), actual.getApplicationPeriods().get(0).getId());
        assertEquals(expected.getApplicationPeriods().get(0).getName().getFi(), actual.getApplicationPeriods().get(0).getName().getFi());
        assertEquals(expected.getApplicationPeriods().get(0).getName().getSv(), actual.getApplicationPeriods().get(0).getName().getSv());
        assertEquals(expected.getApplicationPeriods().get(0).getName().getEn(), actual.getApplicationPeriods().get(0).getName().getEn());
        assertEquals(expected.getApplicationPeriods().get(0).getStarts().getTime(), actual.getApplicationPeriods().get(0).getStarts().getTime());
        assertEquals(expected.getApplicationPeriods().get(0).getEnds().getTime(), actual.getApplicationPeriods().get(0).getEnds().getTime());
    }

    private void verifyApplicationSystem(ApplicationSystem as) {
        assertNotNull(as);
        assertEquals(populatedAsOid, as.getOid());
        assertEquals("haku nimi fi", as.getName().getFi());
        assertEquals("haku nimi sv", as.getName().getSv());
        assertEquals("haku nimi en", as.getName().getEn());
        assertEquals("hakutapa fi", as.getApplicationMethod().getFi());
        assertEquals("hakutapa sv", as.getApplicationMethod().getSv());
        assertEquals("hakutapa en", as.getApplicationMethod().getEn());
        assertEquals(2015, as.getApplicationYear());
        assertEquals("K", as.getApplicationSeason().getValue());
        assertEquals("hakukausi fi", as.getApplicationSeason().getName().getFi());
        assertEquals("hakukausi sv", as.getApplicationSeason().getName().getSv());
        assertEquals("hakukausi en", as.getApplicationSeason().getName().getEn());
        assertEquals(2016, as.getEducationStartYear());
        assertEquals("S", as.getEducationStartSeason().getValue());
        assertEquals("koul alk kausi fi", as.getEducationStartSeason().getName().getFi());
        assertEquals("koul alk kausi sv", as.getEducationStartSeason().getName().getSv());
        assertEquals("koul alk kausi en", as.getEducationStartSeason().getName().getEn());
        assertEquals("hakulomake url", as.getApplicationFormUrl());
        assertNull(as.getOpintopolkuFormUrl());
        assertNotNull(as.getApplicationPeriods());
        assertEquals(1, as.getApplicationPeriods().size());
        ApplicationPeriod ap = as.getApplicationPeriods().get(0);
        assertNotNull(ap);
        assertEquals("hakuaika_id1", ap.getId());
        assertEquals("hakuaika nimi fi", ap.getName().getFi());
        assertEquals("hakuaika nimi sv", ap.getName().getSv());
        assertEquals("hakuaika nimi en", ap.getName().getEn());
        Calendar starts = getZeroCalendar();
        starts.set(Calendar.YEAR, 2015);
        starts.set(Calendar.MONTH, Calendar.JANUARY);
        starts.set(Calendar.DATE, 1);
        starts.set(Calendar.HOUR_OF_DAY, 15);
        Calendar ends = getZeroCalendar();
        ends.set(Calendar.YEAR, 2015);
        ends.set(Calendar.MONTH, Calendar.FEBRUARY);
        ends.set(Calendar.DATE, 1);
        ends.set(Calendar.HOUR_OF_DAY, 15);
        assertEquals(starts.getTime(), ap.getStarts());
        assertEquals(ends.getTime(), ap.getEnds());
    }

}
