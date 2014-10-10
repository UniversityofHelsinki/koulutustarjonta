package fi.helsinki.koulutustarjonta.dao;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.dao.jdbi.LearningOpportunityJDBI;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDAOTest extends BaseDAOTest {

    DBI dbi;
    LearningOpportunityDAO dao;

    final String oid1 = "1.2.3";
    final String oid2 = "1.2.3.4";
    final List<String> oids = Lists.newArrayList(oid1, oid2);

    @Before
    public void init() throws IOException, SQLException {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");
        dbi = new DBI(url, user, passwd);
        dao = new LearningOpportunityDAO(dbi.onDemand(LearningOpportunityJDBI.class));
    }

    @After
    public void close() throws SQLException, IOException {
        Handle h = dbi.open();
        h.execute(String.format("DELETE FROM hakukohde_koulutus WHERE id_koulutus = '%s'", oid2));
        h.execute(String.format("DELETE FROM KOULUTUS_OPETUSKIELI WHERE id_koulutus = '%s'", oid2));
        h.execute(String.format("DELETE FROM KOULUTUS WHERE id = '%s'", oid2));
        dbi.close(h);
    }

    @Test
    public void testFindAll() {
        List<LearningOpportunity> los = dao.findAll();
        assertNotNull(los);
    }

    @Test
    public void testFindById() {
        LearningOpportunity lo = dao.findById(oid1);
        assertNotNull(lo);
        assertEquals(oid1, lo.getOid());
        assertEquals("tutkintonimike fi", lo.getQualification().getFi());
        assertEquals("tutkintonimike sv", lo.getQualification().getSv());
        assertEquals("tutkintonimike en", lo.getQualification().getEn());
        assertEquals("opintoala fi", lo.getEducationalField().getFi());
        assertEquals("opintoala sv", lo.getEducationalField().getSv());
        assertEquals("opintoala en", lo.getEducationalField().getEn());
        assertEquals("tutkintoohjelma fi", lo.getDegreeProgram().getFi());
        assertEquals("tutkintoohjelma sv", lo.getDegreeProgram().getSv());
        assertEquals("tutkintoohjelma en", lo.getDegreeProgram().getEn());
        assertEquals(Integer.valueOf(2015), lo.getStartYear());
        assertEquals("alkamiskausi fi", lo.getStartSeason().getFi());
        assertEquals("alkamiskausi sv", lo.getStartSeason().getSv());
        assertEquals("alkamiskausi en", lo.getStartSeason().getEn());
        assertEquals(Integer.valueOf(3), lo.getPlannedDurationValue());
        assertEquals("suunni tyyppi fi", lo.getPlannedDurationUnit().getFi());
        assertEquals("suunni tyyppi sv", lo.getPlannedDurationUnit().getSv());
        assertEquals("suunni tyyppi en", lo.getPlannedDurationUnit().getEn());
        assertEquals(Integer.valueOf(120), lo.getCreditValue());
        assertEquals("laajuus tyyppi fi", lo.getCreditUnit().getFi());
        assertEquals("laajuus tyyppi sv", lo.getCreditUnit().getSv());
        assertEquals("laajuus tyyppi en", lo.getCreditUnit().getEn());
        assertEquals("rakenne fi", lo.getStructure().getFi());
        assertEquals("rakenne sv", lo.getStructure().getSv());
        assertEquals("rakenne en", lo.getStructure().getEn());
        assertEquals("tavoitteet fi", lo.getGoals().getFi());
        assertEquals("tavoitteet sv", lo.getGoals().getSv());
        assertEquals("tavoitteet en", lo.getGoals().getEn());
        assertEquals("mahdollisuudet fi", lo.getPostgraduateStudies().getFi());
        assertEquals("mahdollisuudet sv", lo.getPostgraduateStudies().getSv());
        assertEquals("mahdollisuudet en", lo.getPostgraduateStudies().getEn());
        assertEquals("patevyys fi", lo.getCompetency().getFi());
        assertEquals("patevyys sv", lo.getCompetency().getSv());
        assertEquals("patevyys en", lo.getCompetency().getEn());
        assertEquals("lisatietoja kielista fi", lo.getLanguageInfo().getFi());
        assertEquals("lisatietoja kielista sv", lo.getLanguageInfo().getSv());
        assertEquals("lisatietoja kielista en", lo.getLanguageInfo().getEn());
        assertEquals("yhteistyo fi", lo.getCooperation().getFi());
        assertEquals("yhteistyo sv", lo.getCooperation().getSv());
        assertEquals("yhteistyo en", lo.getCooperation().getEn());
        assertEquals("paaaineen valinta fi", lo.getSelectingMajorSubject().getFi());
        assertEquals("paaaineen valinta sv", lo.getSelectingMajorSubject().getSv());
        assertEquals("paaaineen valinta en", lo.getSelectingMajorSubject().getEn());
        assertEquals("kansainvalinen toiminta fi", lo.getInternationalization().getFi());
        assertEquals("kansainvalinen toiminta sv", lo.getInternationalization().getSv());
        assertEquals("kansainvalinen toiminta en", lo.getInternationalization().getEn());
        assertEquals("sijoittuminen tyoelamaan fi", lo.getWorkLifePlacement().getFi());
        assertEquals("sijoittuminen tyoelamaan sv", lo.getWorkLifePlacement().getSv());
        assertEquals("sijoittuminen tyoelamaan en", lo.getWorkLifePlacement().getEn());
        assertEquals("sisalto fi", lo.getContents().getFi());
        assertEquals("sisalto sv", lo.getContents().getSv());
        assertEquals("sisalto en", lo.getContents().getEn());
        assertEquals("tutkimuksen painotus fi", lo.getResearch().getFi());
        assertEquals("tutkimuksen painotus sv", lo.getResearch().getSv());
        assertEquals("tutkimuksen painotus en", lo.getResearch().getEn());
        assertEquals("opinnaytetyo fi", lo.getThesis().getFi());
        assertEquals("opinnaytetyo sv", lo.getThesis().getSv());
        assertEquals("opinnaytetyo en", lo.getThesis().getEn());
        List<TeachingLanguage> teachingLangs = lo.getTeachingLanguages();
        assertNotNull(teachingLangs);
        assertEquals(2, teachingLangs.size());
        List<String> applicationOptions = lo.getApplicationOptions();
        assertNotNull(applicationOptions);
        assertEquals(1, applicationOptions.size());
        assertEquals("hakukohde_id1", applicationOptions.get(0));
        assertEquals("organisaatio_id1", lo.getProvider());
    }

    @Test
    public void testInsert() {
        LearningOpportunity lo = Fixture.learningOpportunity(oid2);
        dao.save(lo);
        LearningOpportunity fetched = dao.findById(oid2);
        assertNotNull(fetched);
        learningOpportunitiesEqual(lo, fetched);
    }

    @Test
    public void testUpdate() {
        LearningOpportunity lo = Fixture.learningOpportunity(oid1);
        dao.save(lo);
        LearningOpportunity fetched = dao.findById(oid1);
        assertNotNull(fetched);
        learningOpportunitiesEqual(lo, fetched);
    }

    private void learningOpportunitiesEqual(LearningOpportunity expected, LearningOpportunity actual) {

        assertEquals(expected.getOid(), actual.getOid());
        i18NEquals(expected.getQualification(), actual.getQualification());
        i18NEquals(expected.getEducationalField(), actual.getEducationalField());
        i18NEquals(expected.getDegreeProgram(), actual.getDegreeProgram());
        assertEquals(expected.getStartYear(), actual.getStartYear());
        i18NEquals(expected.getStartSeason(), actual.getStartSeason());
        assertEquals(expected.getPlannedDurationValue(), actual.getPlannedDurationValue());
        i18NEquals(expected.getPlannedDurationUnit(), actual.getPlannedDurationUnit());
        assertEquals(expected.getCreditValue(), actual.getCreditValue());
        i18NEquals(expected.getCreditUnit(), actual.getCreditUnit());
        i18NEquals(expected.getStructure(), actual.getStructure());
        i18NEquals(expected.getGoals(), actual.getGoals());
        i18NEquals(expected.getPostgraduateStudies(), actual.getPostgraduateStudies());
        i18NEquals(expected.getCompetency(), actual.getCompetency());
        i18NEquals(expected.getLanguageInfo(), actual.getLanguageInfo());
        i18NEquals(expected.getCooperation(), actual.getCooperation());
        i18NEquals(expected.getSelectingMajorSubject(), actual.getSelectingMajorSubject());
        i18NEquals(expected.getInternationalization(), actual.getInternationalization());
        i18NEquals(expected.getWorkLifePlacement(), actual.getWorkLifePlacement());
        i18NEquals(expected.getContents(), actual.getContents());
        i18NEquals(expected.getResearch(), actual.getResearch());
        i18NEquals(expected.getThesis(), actual.getThesis());
        assertEquals(expected.getTeachingLanguages().size(), actual.getTeachingLanguages().size());

        for (TeachingLanguage expectedLang : expected.getTeachingLanguages()) {
            TeachingLanguage actualLang = actual.getTeachingLanguages().stream()
                    .filter(x -> x.getLang().equals(expectedLang.getLang())).findFirst().get();
            i18NEquals(expectedLang.getName(), actualLang.getName());
        }

        assertEquals(expected.getApplicationOptions().size(), actual.getApplicationOptions().size());
        assertEquals(expected.getApplicationOptions().get(0), actual.getApplicationOptions().get(0));
        assertEquals(expected.getProvider(), actual.getProvider());
    }


}
