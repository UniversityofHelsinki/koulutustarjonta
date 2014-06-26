package fi.helsinki.koulutustarjonta.dao;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import oracle.jdbc.pool.OracleDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDAOTest {

    DBI dbi;
    LearningOpportunityDAO dao;
    OracleDataSource ds;

    final String oid1 = "1.2.3";
    final String oid2 = "1.2.3.4";
    final List<String> oids = Lists.newArrayList(oid1, oid2);

    @Before
    public void init() throws IOException, SQLException {
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String passwd = System.getProperty("db.passwd");

        ds = new OracleDataSource();
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(passwd);

        executeSqlFile(ds, "db/populate_test_db.sql");

        dbi = new DBI(url, user, passwd);
        dao = new LearningOpportunityDAO(dbi.onDemand(LearningOpportunityJDBI.class));
    }

    @After
    public void close() throws SQLException, IOException {
        executeSqlFile(ds, "db/delete_test_data.sql");
        Handle h = dbi.open();
        h.execute(String.format("DELETE FROM KOULUTUS_OPETUSKIELI WHERE id_koulutus = '%s'", oid2));
        h.execute(String.format("DELETE FROM KOULUTUS WHERE id = '%s'", oid2));
        ds.close();
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
    }

    @Test
    public void testInsert() {
        LearningOpportunity lo = fixture(oid2);
        dao.save(lo);

        LearningOpportunity fetched = dao.findById(oid2);
        assertNotNull(fetched);
        checkEquals(lo, fetched);
    }

    @Test
    public void testUpdate() {
        LearningOpportunity lo = fixture(oid1);
        dao.save(lo);

        LearningOpportunity fetched = dao.findById(oid1);
        assertNotNull(fetched);
        checkEquals(lo, fetched);
    }

    private LearningOpportunity fixture(String oid) {
        LearningOpportunity lo = new LearningOpportunity();
        lo.setOid(oid);
        lo.setQualification(new I18N("qualification fi new", "qualification sv new", "qualification en new"));
        lo.setEducationalField(new I18N("field fi new", "field sv new", "field en new"));
        lo.setDegreeProgram(new I18N("degree fi new", "degree sv new", "degree en new"));
        lo.setStartYear(3015);
        lo.setStartSeason(new I18N("season fi new", "season sv new", "season en new"));
        lo.setPlannedDurationValue(5);
        lo.setPlannedDurationUnit(new I18N("duration fi new", "duration sv new", "duration en new"));
        lo.setCreditValue(200);
        lo.setCreditUnit(new I18N("credits fi new", "credits sv new", "credits en new"));
        lo.setStructure(new I18N("structure fi new", "struckture sv new", "structure en new"));
        lo.setGoals(new I18N("goald fi new", "goals sv new", "goald en new"));
        lo.setPostgraduateStudies(new I18N("postgraduate fi new", "postgraduate sv new", "postgraduate en new"));
        lo.setCompetency(new I18N("comp fi new", "comp sv new", "comp en new"));
        lo.setLanguageInfo(new I18N("lang info fi new", "lang info sv new", "lang info en new"));
        lo.setCooperation(new I18N("cooop fi new", "cooop sv new", "cooop en new"));
        lo.setSelectingMajorSubject(new I18N("major fi new", "major sv new", "major en new"));
        lo.setInternationalization(new I18N("internationalization fi new", "internationalization sv new", "internationalization en new"));
        lo.setWorkLifePlacement(new I18N("working fi new", "working sv new", "working en new"));
        lo.setContents(new I18N("content fi new", "content sv new", "content en new"));
        lo.setResearch(new I18N("research fi new", "research sv new", "research en new"));
        lo.setThesis(new I18N("thesis fi new", "thesis sv new", "thesis en new"));
        TeachingLanguage teachingLanguage1 = new TeachingLanguage("fi", new I18N("suomi", "finska", "Finnish"));
        TeachingLanguage teachingLanguage2 = new TeachingLanguage("en", new I18N("englanti", "engelska", "English"));
        lo.setTeachingLanguages(Lists.newArrayList(teachingLanguage1, teachingLanguage2));
        return lo;
    }

    private void checkEquals(LearningOpportunity expected, LearningOpportunity actual) {
        assertEquals(expected.getOid(), actual.getOid());
        checkEquals(expected.getQualification(), actual.getQualification());
        checkEquals(expected.getEducationalField(), actual.getEducationalField());
        checkEquals(expected.getDegreeProgram(), actual.getDegreeProgram());
        assertEquals(expected.getStartYear(), actual.getStartYear());
        checkEquals(expected.getStartSeason(), actual.getStartSeason());
        assertEquals(expected.getPlannedDurationValue(), actual.getPlannedDurationValue());
        checkEquals(expected.getPlannedDurationUnit(), actual.getPlannedDurationUnit());
        assertEquals(expected.getCreditValue(), actual.getCreditValue());
        checkEquals(expected.getCreditUnit(), actual.getCreditUnit());
        checkEquals(expected.getStructure(), actual.getStructure());
        checkEquals(expected.getGoals(), actual.getGoals());
        checkEquals(expected.getPostgraduateStudies(), actual.getPostgraduateStudies());
        checkEquals(expected.getCompetency(), actual.getCompetency());
        checkEquals(expected.getLanguageInfo(), actual.getLanguageInfo());
        checkEquals(expected.getCooperation(), actual.getCooperation());
        checkEquals(expected.getSelectingMajorSubject(), actual.getSelectingMajorSubject());
        checkEquals(expected.getInternationalization(), actual.getInternationalization());
        checkEquals(expected.getWorkLifePlacement(), actual.getWorkLifePlacement());
        checkEquals(expected.getContents(), actual.getContents());
        checkEquals(expected.getResearch(), actual.getResearch());
        checkEquals(expected.getThesis(), actual.getThesis());
        assertEquals(expected.getTeachingLanguages().size(), actual.getTeachingLanguages().size());

        for (TeachingLanguage expectedLang : expected.getTeachingLanguages()) {
            TeachingLanguage actualLang = actual.getTeachingLanguages().stream()
                    .filter(x -> x.getLang().equals(expectedLang.getLang())).findFirst().get();
            checkEquals(expectedLang.getName(), actualLang.getName());
        }
    }

    private void checkEquals(I18N expected, I18N actual) {
        assertEquals(expected.getFi(), actual.getFi());
        assertEquals(expected.getSv(), actual.getSv());
        assertEquals(expected.getEn(), actual.getEn());

    }

    private void executeSqlFile (DataSource dataSource, String file) throws IOException {

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
}
