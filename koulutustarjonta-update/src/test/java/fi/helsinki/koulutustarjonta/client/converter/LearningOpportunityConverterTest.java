package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityConverterTest extends AbstractClientConverterTest {

    LearningOpportunityConverter converter;
    JsonNode fixture;

    @Before
    public void init() throws IOException {
        KoodistoClient koodistoClient = mockKoodistoClient();
        converter = new LearningOpportunityConverter(koodistoClient);
        fixture = fixture("fixtures/koulutus.json");
    }

    @Test
    public void testConvert() {
        LearningOpportunity lo = converter.convert(fixture);
        assertNotNull(lo);
        assertEquals("1.2.246.562.17.17939899864", lo.getOid());
        assertNotNull(lo.getQualification());
        assertEquals("Filosofian maisteri", lo.getQualification().getFi());
        assertEquals("Filosofie magister", lo.getQualification().getSv());
        assertEquals("Master of Science", lo.getQualification().getEn());
        assertNotNull(lo.getEducationalField());
        assertEquals("Kemia", lo.getEducationalField().getFi());
        assertEquals("Kemi", lo.getEducationalField().getSv());
        assertEquals("Chemistry", lo.getEducationalField().getEn());
        assertNotNull(lo.getDegreeProgram());
        assertEquals("Kemia, filosofian maisteri", lo.getDegreeProgram().getFi());
        assertEquals("Kemi, filosofie magister", lo.getDegreeProgram().getSv());
        assertEquals("Chemistry, Master of Science", lo.getDegreeProgram().getEn());
        assertEquals(Integer.valueOf(2015), lo.getStartYear());
        assertNotNull(lo.getStartSeason());
        assertEquals("Kevät", lo.getStartSeason().getFi());
        assertEquals("Vår", lo.getStartSeason().getSv());
        assertEquals("Spring", lo.getStartSeason().getEn());
        assertEquals("2", lo.getPlannedDurationValue());
        assertNotNull(lo.getPlannedDurationUnit());
        assertEquals("vuotta", lo.getPlannedDurationUnit().getFi());
        assertEquals("år", lo.getPlannedDurationUnit().getSv());
        assertEquals("years", lo.getPlannedDurationUnit().getEn());
        assertEquals(Integer.valueOf(120), lo.getCreditValue());
        assertNotNull(lo.getCreditUnit());
        assertEquals("opintopistettä", lo.getCreditUnit().getFi());
        assertEquals("poäng", lo.getCreditUnit().getSv());
        assertEquals("ECTS cr", lo.getCreditUnit().getEn());
        validateTeachingLanguages(lo.getTeachingLanguages());
        assertNotNull(lo.getGoals());
        assertEquals("<p>Yliopistossa suoritetut kemian opinnot ", lo.getGoals().getFi());
        assertEquals("<p>Universitetsstudier i kemi ger en utmärkt", lo.getGoals().getSv());
        assertEquals("<p><span style=\"color: rgb(0,0,0);\">Studie", lo.getGoals().getEn());
        assertNotNull(lo.getStructure());
        assertEquals("<p>Kemian opiskelijana voit valmistua filosofia", lo.getStructure().getFi());
        assertEquals("<p>Studerande i kemi kan avlägga filosofie magi", lo.getStructure().getSv());
        assertEquals("<p>As a student of chemistry, you can qualify ", lo.getStructure().getEn());
        assertNotNull(lo.getPostgraduateStudies());
        assertEquals("<p>Hyvin suoritettu kemian maisterin tutkinto " ,lo.getPostgraduateStudies().getFi());
        assertEquals("<p>En magisterexamen i kemi med goda vitsord " ,lo.getPostgraduateStudies().getSv());
        assertEquals("<p>For students planning an academic career, " ,lo.getPostgraduateStudies().getEn());
        assertNotNull(lo.getCompetency());
        assertEquals("<p>Kemian opiskelijana voit valmistua filos", lo.getCompetency().getFi());
        assertEquals("<p>Studerande i kemi kan avlägga filosofie ", lo.getCompetency().getSv());
        assertEquals("<p>As a student of chemistry, you can quali", lo.getCompetency().getEn());
        assertNotNull(lo.getLanguageInfo());
        assertEquals("<p>Opetuskielinä ovat pääsääntöisesti suom", lo.getLanguageInfo().getFi());
        assertEquals("<p>Undervisningsspråken är i ", lo.getLanguageInfo().getSv());
        assertEquals("<p><span style=\"color: rgb(0,0,0);\">The ", lo.getLanguageInfo().getEn());
        assertNotNull(lo.getCooperation());
        assertEquals("<p>Kemian laitos tekee paljon yhteist", lo.getCooperation().getFi());
        assertEquals("<p>Institutionen för kemi samarbetar ", lo.getCooperation().getSv());
        assertEquals("<p>The Department of Chemistry has a ", lo.getCooperation().getEn());
        assertNotNull(lo.getSelectingMajorSubject());
        assertEquals("<p>Maisterivaiheessa valitset oman erikoistumislinjasi (ana", lo.getSelectingMajorSubject().getFi());
        assertEquals("<p>I magisterskedet väljer du din specialiseringsinriktning", lo.getSelectingMajorSubject().getSv());
        assertEquals("<p><span style=\"color: rgb(0,0,0);\"><span>After being adm", lo.getSelectingMajorSubject().getEn());
        assertNotNull(lo.getInternationalization());
        assertEquals("<p>Kemian laitos on suosittu kohde monell", lo.getInternationalization().getFi());
        assertEquals("<p>Många utländska utbytesstuderande komm", lo.getInternationalization().getSv());
        assertEquals("<p>The Department of Chemistry is a popul", lo.getInternationalization().getEn());
        assertNotNull(lo.getWorkLifePlacement());
        assertEquals("<p>Kemianteollisuus on yksi Suomen suurimmista ty", lo.getWorkLifePlacement().getFi());
        assertEquals("<p>Kemiindustrin hör till de största arbetsgivarn", lo.getWorkLifePlacement().getSv());
        assertEquals("<p>The chemical industry is one of the biggest em", lo.getWorkLifePlacement().getEn());
        assertNotNull(lo.getContents());
        assertEquals("<p>Maisterin tutkinnossa syvennät \"http://www.he\"", lo.getContents().getFi());
        assertEquals("<p>I magisterexamen fördjupar du \"http://www.he\"", lo.getContents().getSv());
        assertEquals("<p>In the Master's Degree Program \"http://www.he\"", lo.getContents().getEn());
        assertNotNull(lo.getResearch());
        assertEquals("<p>Matemaattis-luonnontieteellisessä tiede", lo.getResearch().getFi());
        assertEquals("<p>Matematisk-naturvetenskapliga fakulteten ", lo.getResearch().getSv());
        assertEquals("<p>There are many ongoing interdisciplinar", lo.getResearch().getEn());
        assertNotNull(lo.getThesis());
        assertEquals("<p>Opintojesi lopputyönä teet pro gradu ", lo.getThesis().getFi());
        assertEquals("<p>Studierna avslutas med ett slutarbete", lo.getThesis().getSv());
        assertEquals("<p><span style=\"color: rgb(0,0,0);\">Th", lo.getThesis().getEn());
        assertEquals("1.2.246.562.10.94639300915", lo.getProvider());

        assertNotNull(lo.getEducationLevel());
        assertEquals("Ylempi korkeakoulututkinto", lo.getEducationLevel().getFi());
        assertEquals("Högre högskoleexamen", lo.getEducationLevel().getSv());
        assertEquals("Master's degree", lo.getEducationLevel().getEn());
    }

    private void validateTeachingLanguages(List<TeachingLanguage> teachnigLanguages) {
        assertNotNull(teachnigLanguages);
        assertEquals(3, teachnigLanguages.size());
        teachnigLanguages.forEach(tl -> {
                    if (tl.getLang().equals("fi")) {
                        assertEquals("suomi", tl.getName().getFi());
                        assertEquals("finska", tl.getName().getSv());
                        assertEquals("Finnish", tl.getName().getEn());
                    }
                    if (tl.getLang().equals("sv")) {
                        assertEquals("ruotsi", tl.getName().getFi());
                        assertEquals("svenska", tl.getName().getSv());
                        assertEquals("Swedish", tl.getName().getEn());
                    }
                    if (tl.getLang().equals("en")) {
                        assertEquals("englanti", tl.getName().getFi());
                        assertEquals("engelska", tl.getName().getSv());
                        assertEquals("English", tl.getName().getEn());
                    }
                }
        );

    }
}
