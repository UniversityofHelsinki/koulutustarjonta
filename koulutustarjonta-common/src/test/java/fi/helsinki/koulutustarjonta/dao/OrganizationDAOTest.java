package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.OrganizationJDBI;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.domain.Some;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.Handle;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationDAOTest extends BaseDAOTest {

    OrganizationDAO dao;

    final Organization fixture = Fixture.organization("org_test_oid");

    // test data organization oid
    final String orgOidPopulated = "organisaatio_id1";

    @Before
    public void init() {
        dao = new OrganizationDAO(dbi.onDemand(OrganizationJDBI.class));
    }

    @After
    public void destroy() {
        Handle h = dbi.open();
        h.execute("DELETE FROM yhteystieto WHERE id_organisaatio = ?", fixture.getOid());
        h.execute("DELETE FROM some WHERE id_organisaatio = ?", fixture.getOid());
        h.execute("DELETE FROM organisaatio WHERE id = ?", fixture.getOid());
        dbi.close(h);
    }

    @Test
    public void testFindByOid() throws ResourceNotFound {
        Organization o = dao.findByOid(orgOidPopulated);
        assertNotNull(o);
        verifyOrganizationAgainstPopulatedData(o);
    }

    @Test
    public void testFindAll() {
        List<Organization> organizations = dao.findAll();
        Organization organization = organizations.parallelStream()
                .filter(o -> o.getOid().equals(orgOidPopulated))
                .collect(toList())
                .get(0);
        assertNotNull(organizations);
        assertNotNull(organization);
        verifyOrganizationAgainstPopulatedData(organization);
    }

    @Test( expected = ResourceNotFound.class )
    public void testNotFound() throws ResourceNotFound {
        dao.findByOid("invalidoid");
    }

    @Test
    public void testSave() throws ResourceNotFound {
        dao.save(fixture);
        Organization o = dao.findByOid(fixture.getOid());
        assertNotNull(o);
        assertEquals(fixture.getOid(), o.getOid());
        i18NEquals(fixture.getName(), o.getName());
        i18NEquals(fixture.getOutline(), o.getOutline());
        i18NEquals(fixture.getExpenses(), o.getExpenses());
        i18NEquals(fixture.getInternationalStudyPrograms(), o.getInternationalStudyPrograms());
        i18NEquals(fixture.getStudentTransfer(), o.getStudentTransfer());
        i18NEquals(fixture.getStudyEnvironment(), o.getStudyEnvironment());
        i18NEquals(fixture.getAccessibility(), o.getAccessibility());
        i18NEquals(fixture.getYearClock(), o.getYearClock());
        i18NEquals(fixture.getPeopleInCharge(), o.getPeopleInCharge());
        i18NEquals(fixture.getSelectionProcedure(), o.getSelectionProcedure());
        i18NEquals(fixture.getPreviouslyGainedExperience(), o.getPreviouslyGainedExperience());
        i18NEquals(fixture.getLanguageStudies(), o.getLanguageStudies());
        i18NEquals(fixture.getInternship(), o.getInternship());
        i18NEquals(fixture.getSome().getFacebook(), o.getSome().getFacebook());
        i18NEquals(fixture.getSome().getTwitter(), o.getSome().getTwitter());
        i18NEquals(fixture.getSome().getGooglePlus(), o.getSome().getGooglePlus());
        i18NEquals(fixture.getSome().getLinkedIn(), o.getSome().getLinkedIn());
        i18NEquals(fixture.getSome().getOther(), o.getSome().getOther());
        i18NEquals(fixture.getSome().getInstagram(), o.getSome().getInstagram());
        i18NEquals(fixture.getSome().getYoutube(), o.getSome().getYoutube());
        contactInfosEqual(filterLang(fixture.getContactInfos(), "fi"), filterLang(o.getContactInfos(), "fi"));
        contactInfosEqual(filterLang(fixture.getContactInfos(), "sv"), filterLang(o.getContactInfos(), "sv"));
        contactInfosEqual(filterLang(fixture.getContactInfos(), "en"), filterLang(o.getContactInfos(), "en"));
        contactInfosEqual(filterLang(fixture.getApplicantServices(), "fi"), filterLang(o.getApplicantServices(), "fi"));
        contactInfosEqual(filterLang(fixture.getApplicantServices(), "sv"), filterLang(o.getApplicantServices(), "sv"));
        contactInfosEqual(filterLang(fixture.getApplicantServices(), "en"), filterLang(o.getApplicantServices(), "en"));
    }

    private ContactInfo filterLang(List<ContactInfo> contactInfos, String lang) {
        return contactInfos.stream()
                .filter(info -> info.getLang().equals(lang))
                .collect(toList()).get(0);
    }

    private void contactInfosEqual(ContactInfo expected, ContactInfo actual) {
        assertEquals(expected.getOid(), actual.getOid());
        assertEquals(expected.getLang(), actual.getLang());
        assertEquals(expected.getWww(), actual.getWww());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFax(), actual.getFax());
        assertEquals(expected.getVisitingAddress().getStreet(), actual.getVisitingAddress().getStreet());
        assertEquals(expected.getVisitingAddress().getPostalCode(), actual.getVisitingAddress().getPostalCode());
        assertEquals(expected.getVisitingAddress().getPostOffice(), actual.getVisitingAddress().getPostOffice());
        assertEquals(expected.getPostalAddress().getStreet(), actual.getPostalAddress().getStreet());
        assertEquals(expected.getPostalAddress().getPostalCode(), actual.getPostalAddress().getPostalCode());
        assertEquals(expected.getPostalAddress().getPostOffice(), actual.getPostalAddress().getPostOffice());
    }

    private void verifyOrganizationAgainstPopulatedData(Organization o) {
        assertEquals(orgOidPopulated, o.getOid());
        assertEquals("o nimi fi", o.getName().getFi());
        assertEquals("o nimi sv", o.getName().getSv());
        assertEquals("o nimi en", o.getName().getEn());
        assertEquals("kustannukset fi", o.getExpenses().getFi());
        assertEquals("kustannukset sv", o.getExpenses().getSv());
        assertEquals("kustannukset en", o.getExpenses().getEn());
        assertEquals("kv fi", o.getInternationalStudyPrograms().getFi());
        assertEquals("kv sv", o.getInternationalStudyPrograms().getSv());
        assertEquals("kv en", o.getInternationalStudyPrograms().getEn());
        assertEquals("liikkuvuus fi", o.getStudentTransfer().getFi());
        assertEquals("liikkuvuus sv", o.getStudentTransfer().getSv());
        assertEquals("liikkuvuus en", o.getStudentTransfer().getEn());
        assertEquals("ymparisto fi", o.getStudyEnvironment().getFi());
        assertEquals("ymparisto sv", o.getStudyEnvironment().getSv());
        assertEquals("ymparisto en", o.getStudyEnvironment().getEn());
        assertEquals("yleiskuvaus fi", o.getOutline().getFi());
        assertEquals("yleiskuvaus sv", o.getOutline().getSv());
        assertEquals("yleiskuvaus en", o.getOutline().getEn());
        assertEquals("saavutettavuus fi", o.getAccessibility().getFi());
        assertEquals("saavutettavuus sv", o.getAccessibility().getSv());
        assertEquals("saavutettavuus en", o.getAccessibility().getEn());
        assertEquals("vuosikello fi", o.getYearClock().getFi());
        assertEquals("vuosikello sv", o.getYearClock().getSv());
        assertEquals("vuosikello en", o.getYearClock().getEn());
        assertEquals("vastuuhenkilot fi", o.getPeopleInCharge().getFi());
        assertEquals("vastuuhenkilot sv", o.getPeopleInCharge().getSv());
        assertEquals("vastuuhenkilot en", o.getPeopleInCharge().getEn());
        assertEquals("valintamenettely fi", o.getSelectionProcedure().getFi());
        assertEquals("valintamenettely sv", o.getSelectionProcedure().getSv());
        assertEquals("valintamenettely en", o.getSelectionProcedure().getEn());
        assertEquals("aik kokemus fi", o.getPreviouslyGainedExperience().getFi());
        assertEquals("aik kokemus sv", o.getPreviouslyGainedExperience().getSv());
        assertEquals("aik kokemus en", o.getPreviouslyGainedExperience().getEn());
        assertEquals("kieliopinnot fi", o.getLanguageStudies().getFi());
        assertEquals("kieliopinnot sv", o.getLanguageStudies().getSv());
        assertEquals("kieliopinnot en", o.getLanguageStudies().getEn());
        assertEquals("tyoharjoittelu fi", o.getInternship().getFi());
        assertEquals("tyoharjoittelu sv", o.getInternship().getSv());
        assertEquals("tyoharjoittelu en", o.getInternship().getEn());
        verifyContactInfosAgainstPopulatedData(o.getContactInfos());
        verifyApplicantServicesAgainstPopulatedData(o.getApplicantServices());
        verifySocialMediaAgainstPopulatedData(o.getSome());
    }

    private void verifyContactInfosAgainstPopulatedData(List<ContactInfo> contactInfos) {
        ContactInfo ciFi = contactInfos.stream()
                .filter(ci -> ci.getLang().equals("fi"))
                .collect(toList()).get(0);
        assertNotNull(ciFi);
        assertEquals("yhteystieto_id1", ciFi.getOid());
        assertEquals("contact www fi", ciFi.getWww());
        assertEquals("contact phone fi", ciFi.getPhone());
        assertEquals("contact email fi", ciFi.getEmail());
        assertEquals("contact fax fi", ciFi.getFax());
        assertEquals("contact visit street fi", ciFi.getVisitingAddress().getStreet());
        assertEquals("contact visit numb fi", ciFi.getVisitingAddress().getPostalCode());
        assertEquals("contact visit office fi", ciFi.getVisitingAddress().getPostOffice());
        assertEquals("contact post street fi", ciFi.getPostalAddress().getStreet());
        assertEquals("contact post numb fi", ciFi.getPostalAddress().getPostalCode());
        assertEquals("contact post office fi", ciFi.getPostalAddress().getPostOffice());

        ContactInfo ciSv = contactInfos.stream()
                .filter(ci -> ci.getLang().equals("sv"))
                .collect(toList()).get(0);
        assertNotNull(ciSv);
        assertEquals("yhteystieto_id2", ciSv.getOid());
        assertEquals("contact www sv", ciSv.getWww());
        assertEquals("contact phone sv", ciSv.getPhone());
        assertEquals("contact email sv", ciSv.getEmail());
        assertEquals("contact fax sv", ciSv.getFax());
        assertEquals("contact visit street sv", ciSv.getVisitingAddress().getStreet());
        assertEquals("contact visit numb sv", ciSv.getVisitingAddress().getPostalCode());
        assertEquals("contact visit office sv", ciSv.getVisitingAddress().getPostOffice());
        assertEquals("contact post street sv", ciSv.getPostalAddress().getStreet());
        assertEquals("contact post numb sv", ciSv.getPostalAddress().getPostalCode());
        assertEquals("contact post office sv", ciSv.getPostalAddress().getPostOffice());

        ContactInfo ciEn = contactInfos.stream()
                .filter(ci -> ci.getLang().equals("en"))
                .collect(toList()).get(0);
        assertNotNull(ciEn);
        assertEquals("yhteystieto_id3", ciEn.getOid());
        assertEquals("contact www en", ciEn.getWww());
        assertEquals("contact phone en", ciEn.getPhone());
        assertEquals("contact email en", ciEn.getEmail());
        assertEquals("contact fax en", ciEn.getFax());
        assertEquals("contact visit street en", ciEn.getVisitingAddress().getStreet());
        assertEquals("contact visit numb en", ciEn.getVisitingAddress().getPostalCode());
        assertEquals("contact visit office en", ciEn.getVisitingAddress().getPostOffice());
        assertEquals("contact post street en", ciEn.getPostalAddress().getStreet());
        assertEquals("contact post numb en", ciEn.getPostalAddress().getPostalCode());
        assertEquals("contact post office en", ciEn.getPostalAddress().getPostOffice());

    }

    private void verifyApplicantServicesAgainstPopulatedData(List<ContactInfo> applicantServices) {
        ContactInfo asFi = applicantServices.stream()
                .filter(as -> as.getLang().equals("fi"))
                .collect(toList()).get(0);
        assertNotNull(asFi);
        assertEquals("yhteystieto_id4", asFi.getOid());
        assertEquals("applicant www fi", asFi.getWww());
        assertEquals("applicant phone fi", asFi.getPhone());
        assertEquals("applicant email fi", asFi.getEmail());
        assertEquals("applicant fax fi", asFi.getFax());
        assertEquals("applicant visit street fi", asFi.getVisitingAddress().getStreet());
        assertEquals("applicant visit numb fi", asFi.getVisitingAddress().getPostalCode());
        assertEquals("applicant visit office fi", asFi.getVisitingAddress().getPostOffice());
        assertEquals("applicant post street fi", asFi.getPostalAddress().getStreet());
        assertEquals("applicant post numb fi", asFi.getPostalAddress().getPostalCode());
        assertEquals("applicant post office fi", asFi.getPostalAddress().getPostOffice());

        ContactInfo asSv = applicantServices.stream()
                .filter(as -> as.getLang().equals("sv"))
                .collect(toList()).get(0);
        assertNotNull(asSv);
        assertEquals("yhteystieto_id5", asSv.getOid());
        assertEquals("applicant www sv", asSv.getWww());
        assertEquals("applicant phone sv", asSv.getPhone());
        assertEquals("applicant email sv", asSv.getEmail());
        assertEquals("applicant fax sv", asSv.getFax());
        assertEquals("applicant visit street sv", asSv.getVisitingAddress().getStreet());
        assertEquals("applicant visit numb sv", asSv.getVisitingAddress().getPostalCode());
        assertEquals("applicant visit office sv", asSv.getVisitingAddress().getPostOffice());
        assertEquals("applicant post street sv", asSv.getPostalAddress().getStreet());
        assertEquals("applicant post numb sv", asSv.getPostalAddress().getPostalCode());
        assertEquals("applicant post office sv", asSv.getPostalAddress().getPostOffice());

        ContactInfo asEn = applicantServices.stream()
                .filter(as -> as.getLang().equals("en"))
                .collect(toList()).get(0);
        assertNotNull(asEn);
        assertEquals("yhteystieto_id6", asEn.getOid());
        assertEquals("applicant www en", asEn.getWww());
        assertEquals("applicant phone en", asEn.getPhone());
        assertEquals("applicant email en", asEn.getEmail());
        assertEquals("applicant fax en", asEn.getFax());
        assertEquals("applicant visit street en", asEn.getVisitingAddress().getStreet());
        assertEquals("applicant visit numb en", asEn.getVisitingAddress().getPostalCode());
        assertEquals("applicant visit office en", asEn.getVisitingAddress().getPostOffice());
        assertEquals("applicant post street en", asEn.getPostalAddress().getStreet());
        assertEquals("applicant post numb en", asEn.getPostalAddress().getPostalCode());
        assertEquals("applicant post office en", asEn.getPostalAddress().getPostOffice());
    }

    private void verifySocialMediaAgainstPopulatedData(Some some) {
        assertNotNull(some);
        assertEquals("face " + "fi", some.getFacebook().getLang("fi"));
        assertEquals("twitter " + "fi", some.getTwitter().getLang("fi"));
        assertEquals("plus " + "fi", some.getGooglePlus().getLang("fi"));
        assertEquals("linkedin " + "fi", some.getLinkedIn().getLang("fi"));
        assertEquals("some_other " + "fi", some.getOther().getLang("fi"));
        assertEquals("instagram " + "fi", some.getInstagram().getLang("fi"));
        assertEquals("youtube " + "fi", some.getYoutube().getLang("fi"));

        assertEquals("face " + "sv", some.getFacebook().getLang("sv"));
        assertEquals("twitter " + "sv", some.getTwitter().getLang("sv"));
        assertEquals("plus " + "sv", some.getGooglePlus().getLang("sv"));
        assertEquals("linkedin " + "sv", some.getLinkedIn().getLang("sv"));
        assertEquals("some_other " + "sv", some.getOther().getLang("sv"));
        assertEquals("instagram " + "sv", some.getInstagram().getLang("sv"));
        assertEquals("youtube " + "sv", some.getYoutube().getLang("sv"));

        assertEquals("face " + "en", some.getFacebook().getLang("en"));
        assertEquals("twitter " + "en", some.getTwitter().getLang("en"));
        assertEquals("plus " + "en", some.getGooglePlus().getLang("en"));
        assertEquals("linkedin " + "en", some.getLinkedIn().getLang("en"));
        assertEquals("some_other " + "en", some.getOther().getLang("en"));
        assertEquals("instagram " + "en", some.getInstagram().getLang("en"));
        assertEquals("youtube " + "en", some.getYoutube().getLang("en"));

    }

}
