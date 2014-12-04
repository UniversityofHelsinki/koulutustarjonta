package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.ApplicationOptionJDBI;
import fi.helsinki.koulutustarjonta.domain.*;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionDAOTest extends BaseDAOTest {

    DBI dbi;
    ApplicationOptionDAO dao;

    final String aoOidPopulated = "hakukohde_id1";
    final String aoOid1 = "ao oid 1";
    final ApplicationOption fixture1 = Fixture.applicationOption(aoOid1);

    @Before
    public void init() {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");
        dbi = new DBI(url, user, passwd);
        dao = new ApplicationOptionDAO(dbi.onDemand(ApplicationOptionJDBI.class));
    }

    @After
    public void destroy() {
        Handle h = dbi.open();
        h.execute("DELETE FROM hakukelp WHERE id_hakukohde = ?", fixture1.getOid());
        h.execute("DELETE FROM liite WHERE id_hakukohde = ?", fixture1.getOid());
        fixture1.getExams().forEach(
                exam -> h.execute("DELETE FROM valintakoe_ak WHERE id_valintakoe = ?", exam.getOid()));
        h.execute("DELETE FROM valintakoe WHERE id_hakukohde = ?", fixture1.getOid());
        h.execute("DELETE FROM hakukohde WHERE id = ?", fixture1.getOid());
        dbi.close(h);
    }

    @Test
    public void testFindByOid() throws ResourceNotFound {
        ApplicationOption ao =  dao.findByOid(aoOidPopulated);
        assertNotNull(ao);
        assertEquals(aoOidPopulated, ao.getOid());
        assertEquals("nimi fi", ao.getName().getFi());
        assertEquals("nimi sv", ao.getName().getSv());
        assertEquals("nimi en", ao.getName().getEn());
        assertEquals(11, ao.getStartingQuota());
        assertEquals("11 fi", ao.getStartingQuotaDescription().getFi());
        assertEquals("11 sv", ao.getStartingQuotaDescription().getSv());
        assertEquals("11 en", ao.getStartingQuotaDescription().getEn());
        assertEquals("sora fi", ao.getSora().getFi());
        assertEquals("sora sv", ao.getSora().getSv());
        assertEquals("sora en", ao.getSora().getEn());
        assertEquals("lisatiedot fi", ao.getAdditionalInfo().getFi());
        assertEquals("lisatiedot sv", ao.getAdditionalInfo().getSv());
        assertEquals("lisatiedot en", ao.getAdditionalInfo().getEn());
        assertEquals("valintaper fi", ao.getSelectionCriteria().getFi());
        assertEquals("valintaper sv", ao.getSelectionCriteria().getSv());
        assertEquals("valintaper en", ao.getSelectionCriteria().getEn());
        assertEquals("kuvaus fi", ao.getRequirementDescription().getFi());
        assertEquals("kuvaus sv", ao.getRequirementDescription().getSv());
        assertEquals("kuvaus en", ao.getRequirementDescription().getEn());
        assertNotNull(ao.getExams());
        Exam exam = ao.getExams().get(0);
        assertNotNull(exam);
        assertEquals("valintakoe_id1", exam.getOid());
        assertEquals("fi", exam.getLang());
        assertEquals("koetyyppi", exam.getType());
        assertEquals("kokeen kuvaus", exam.getDescription());
        assertNotNull(exam.getEvents());
        ExamEvent event = exam.getEvents().get(0);
        assertNotNull(event);
        assertEquals("ajankohta_id1", event.getOid());
        assertEquals("valintakoeajankohta kuvaus", event.getInfo());
        Address eventAddress = event.getAddress();
        assertEquals("katuosoite", eventAddress.getStreet());
        assertEquals("postinumero", eventAddress.getPostalCode());
        assertEquals("postitoimipaikka", eventAddress.getPostOffice());
        Calendar startsCal = Calendar.getInstance();
        startsCal.set(Calendar.YEAR, 2015);
        startsCal.set(Calendar.MONTH, Calendar.JANUARY);
        startsCal.set(Calendar.DATE, 3);
        startsCal.set(Calendar.HOUR_OF_DAY, 15);
        startsCal.set(Calendar.MINUTE, 0);
        startsCal.set(Calendar.SECOND, 0);
        startsCal.set(Calendar.MILLISECOND, 0);
        Date starts = startsCal.getTime();
        assertEquals(starts, event.getStarts());
        Calendar endsCal = Calendar.getInstance();
        endsCal.set(Calendar.YEAR, 2015);
        endsCal.set(Calendar.MONTH, Calendar.JANUARY);
        endsCal.set(Calendar.DATE, 3);
        endsCal.set(Calendar.HOUR_OF_DAY, 18);
        endsCal.set(Calendar.MINUTE, 0);
        endsCal.set(Calendar.SECOND, 0);
        endsCal.set(Calendar.MILLISECOND, 0);
        Date ends = endsCal.getTime();
        assertEquals(ends, event.getEnds());
        assertNotNull(ao.getAttachments());
        Attachment attachment = ao.getAttachments().get(0);
        assertNotNull(attachment);
        assertEquals("liite_id1", attachment.getOid());
        assertEquals("fi", attachment.getLang());
        assertEquals("liite nimi", attachment.getName());
        Calendar dueCal = getZeroCalendar();
        dueCal.set(Calendar.YEAR, 2015);
        dueCal.set(Calendar.MONTH, Calendar.FEBRUARY);
        dueCal.set(Calendar.DATE, 1);
        dueCal.set(Calendar.HOUR_OF_DAY, 12);
        assertEquals(dueCal.getTime(), attachment.getDue());
        assertEquals("liite kuvaus", attachment.getDescription());
        Address attachmentAddress = attachment.getAddress();
        assertNotNull(attachmentAddress);
        assertEquals("liite katuosoite", attachmentAddress.getStreet());
        assertEquals("liite postinumero", attachmentAddress.getPostalCode());
        assertEquals("liite ptoimipaikka", attachmentAddress.getPostOffice());
        assertNotNull(ao.getRequirements());
        Requirement requirement = ao.getRequirements().get(0);
        assertEquals(999999L, requirement.getId());
        assertEquals("hakukelp fi", requirement.getDescription().getFi());
        assertEquals("hakukelp sv", requirement.getDescription().getSv());
        assertEquals("hakukelp en", requirement.getDescription().getEn());
        assertEquals("haku_id1", ao.getApplicationSystem());
        ApplicationPeriod ap = ao.getApplicationPeriod();
        assertNotNull(ap);
        assertEquals("hakuaika_id1", ap.getId());
        assertEquals("hakuaika nimi fi", ap.getName().getFi());
        assertEquals("hakuaika nimi sv", ap.getName().getSv());
        assertEquals("hakuaika nimi en", ap.getName().getEn());
        Calendar apStarts = getZeroCalendar();
        apStarts.set(Calendar.YEAR, 2015);
        apStarts.set(Calendar.MONTH, Calendar.JANUARY);
        apStarts.set(Calendar.DATE, 1);
        apStarts.set(Calendar.HOUR_OF_DAY, 15);
        Calendar apEnds = getZeroCalendar();
        apEnds.set(Calendar.YEAR, 2015);
        apEnds.set(Calendar.MONTH, Calendar.FEBRUARY);
        apEnds.set(Calendar.DATE, 1);
        apEnds.set(Calendar.HOUR_OF_DAY, 15);
        assertEquals(apStarts.getTime(), ap.getStarts());
        assertEquals(apEnds.getTime(), ap.getEnds());
    }

    @Test(expected = ResourceNotFound.class)
    public void testNotFound() throws ResourceNotFound {
        dao.findByOid("invalid_oid");
    }

    @Test
    public void testFindAll() {
        List<ApplicationOption> aos = dao.findAll();
        assertNotNull(aos);
        assertTrue(aos.size() > 0);
    }

    @Test
    public void testSave() throws ResourceNotFound {
        dao.save(fixture1);
        ApplicationOption ao = dao.findByOid(fixture1.getOid());
        assertEquals(fixture1.getOid(), ao.getOid());
        i18NEquals(fixture1.getName(), ao.getName());
        assertEquals(fixture1.getStartingQuota(), ao.getStartingQuota());
        i18NEquals(fixture1.getStartingQuotaDescription(), ao.getStartingQuotaDescription());
        i18NEquals(fixture1.getSora(), ao.getSora());
        i18NEquals(fixture1.getAdditionalInfo(), ao.getAdditionalInfo());
        i18NEquals(fixture1.getSelectionCriteria(), ao.getSelectionCriteria());
        i18NEquals(fixture1.getRequirementDescription(), ao.getRequirementDescription());
        assertNotNull(ao.getExams());
        Exam exam = ao.getExams().get(0);
        assertNotNull(exam);
        Exam fixtureExam = fixture1.getExams().get(0);
        assertEquals(fixtureExam.getOid(), exam.getOid());
        assertEquals(fixtureExam.getLang(), exam.getLang());
        assertEquals(fixtureExam.getType(), exam.getType());
        assertEquals(fixtureExam.getDescription(), exam.getDescription());
        assertNotNull(exam.getEvents());
        ExamEvent event = exam.getEvents().get(0);
        assertNotNull(event);
        ExamEvent fixtureEvent = fixtureExam.getEvents().get(0);
        assertEquals(fixtureEvent.getOid(), event.getOid());
        assertEquals(fixtureEvent.getInfo(), event.getInfo());
        Address eventAddress = event.getAddress();
        Address fixtureEventAddress = fixtureEvent.getAddress();
        assertEquals(fixtureEventAddress.getStreet(), eventAddress.getStreet());
        assertEquals(fixtureEventAddress.getPostalCode(), eventAddress.getPostalCode());
        assertEquals(fixtureEventAddress.getPostOffice(), eventAddress.getPostOffice());
        assertEquals(fixtureEvent.getStarts(), event.getStarts());
        assertEquals(fixtureEvent.getEnds(), event.getEnds());
        assertNotNull(ao.getAttachments());
        Attachment attachment = ao.getAttachments().get(0);
        assertNotNull(attachment);
        Attachment fixtureAttachment = fixture1.getAttachments().get(0);
        assertEquals(fixtureAttachment.getOid(), attachment.getOid());
        assertEquals(fixtureAttachment.getLang(), attachment.getLang());
        assertEquals(fixtureAttachment.getName(), attachment.getName());
        assertEquals(fixtureAttachment.getDue(), attachment.getDue());
        assertEquals(fixtureAttachment.getDescription(), attachment.getDescription());
        Address attachmentAddress = attachment.getAddress();
        assertNotNull(attachmentAddress);
        Address fixtureAttachmentAddress  = fixtureAttachment.getAddress();
        assertEquals(fixtureAttachmentAddress.getStreet(), attachmentAddress.getStreet());
        assertEquals(fixtureAttachmentAddress.getPostalCode(), attachmentAddress.getPostalCode());
        assertEquals(fixtureAttachmentAddress.getPostOffice(), attachmentAddress.getPostOffice());
        assertNotNull(ao.getRequirements());
        Requirement requirement = ao.getRequirements().get(0);
        Requirement fixtureRequirement = fixture1.getRequirements().get(0);
        i18NEquals(fixtureRequirement.getDescription(), requirement.getDescription());
        assertEquals(fixture1.getApplicationSystem(), ao.getApplicationSystem());
        ApplicationPeriod ap = ao.getApplicationPeriod();
        assertNotNull(ap);
        assertEquals("hakuaika_id1", ap.getId());
        assertEquals("hakuaika nimi fi", ap.getName().getFi());
        assertEquals("hakuaika nimi sv", ap.getName().getSv());
        assertEquals("hakuaika nimi en", ap.getName().getEn());
        Calendar apStarts = getZeroCalendar();
        apStarts.set(Calendar.YEAR, 2015);
        apStarts.set(Calendar.MONTH, Calendar.JANUARY);
        apStarts.set(Calendar.DATE, 1);
        apStarts.set(Calendar.HOUR_OF_DAY, 15);
        Calendar apEnds = getZeroCalendar();
        apEnds.set(Calendar.YEAR, 2015);
        apEnds.set(Calendar.MONTH, Calendar.FEBRUARY);
        apEnds.set(Calendar.DATE, 1);
        apEnds.set(Calendar.HOUR_OF_DAY, 15);
        assertEquals(apStarts.getTime(), ap.getStarts());
        assertEquals(apEnds.getTime(), ap.getEnds());
    }
}
