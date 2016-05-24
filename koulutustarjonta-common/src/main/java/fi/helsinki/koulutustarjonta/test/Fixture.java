package fi.helsinki.koulutustarjonta.test;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class Fixture {

    public static LearningOpportunity learningOpportunity(String oid) {
        LearningOpportunity lo = new LearningOpportunity();
        lo.setOid(oid);
        lo.setQualification(new I18N("qualification fi new", "qualification sv new", "qualification en new"));
        lo.setEducationalField(new I18N("field fi new", "field sv new", "field en new"));
        lo.setDegreeProgram(new I18N("degree fi new", "degree sv new", "degree en new"));
        lo.setStartYear(3015);
        lo.setStartSeason(new I18N("season fi new", "season sv new", "season en new"));
        lo.setPlannedDurationValue("5");
        lo.setPlannedDurationUnit(new I18N("duration fi new", "duration sv new", "duration en new"));
        lo.setCreditValue("200");
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
        lo.setApplicationOptions(Lists.newArrayList("hakukohde_id2"));
        lo.setProvider(Lists.newArrayList("organisaatio_id1"));
        lo.setParents(Lists.newArrayList("1.2.3"));
        lo.setEducationLevel(new I18N("education level fi", "education level sv", "education level en"));
        lo.setKeywords(Lists.newArrayList(new I18N("aihe fi", "aihe sv", "aihe en")));


        lo.setContactInfos(Arrays.asList(getLoContact()));
        return lo;
    }

    private static LOContact getLoContact() {
        LOContact c = LOContact.builder()
                .id(1l)
                .email("testaaja@gmail.com")
                .name("testi")
                .title("testaaja")
                .phoneNumber("123456789")
                .languages(Arrays.asList("FI","EN"))
                .contactType("yhteyshenkilo")
                .build();
                //new LOContact(1l, "testi", "testaaja@gmail.com","123456789",Arrays.asList("FI", "EN"), "yhteyshenkilo");
        return c;
    }

    public static ApplicationOption applicationOption(String oid) {
        ApplicationOption ao = new ApplicationOption();
        ao.setOid(oid);
        ao.setName(new I18N("name fi", "name sv", "name en"));
        ao.setStartingQuota(10);
        ao.setStartingQuotaDescription(new I18N("10 fi", "10 sv", "10 en"));
        ao.setRequirementDescription(
                new I18N("suitability desc fi", "suitability desc sv", "suitability desc en"));
        ao.setAdditionalInfo(new I18N("info fi", "info sv", "info en"));
        ao.setSelectionCriteria(new I18N("criteria fi", "criteria sv", "criteria en"));
        ao.setSora(new I18N("sora fi", "sora sv", "sora en"));
        Exam exam = new Exam();
        exam.setOid("examoid");
        exam.setDescription("exam description");
        exam.setLang("fi");
        exam.setType("exam type");
        ExamEvent event = new ExamEvent();
        event.setOid("event oid");
        event.setInfo("exam info");
        Calendar startsCal = Calendar.getInstance();
        startsCal.set(Calendar.MILLISECOND, 0);
        Date starts = startsCal.getTime();
        Date ends = new Date(starts.getTime() + 3600000L);
        event.setStarts(starts);
        event.setEnds(ends);
        Address address = new Address();
        address.setPostalCode("00100");
        address.setPostOffice("post office");
        address.setStreet("street address 1");
        event.setAddress(address);
        exam.setEvents(Lists.newArrayList(event));
        ao.setExams(Lists.newArrayList(exam));
        Attachment attachment = new Attachment();
        attachment.setOid("attachment oid");
        attachment.setName("attachment name");
        attachment.setLang("fi");
        attachment.setDescription("attachment description");
        Calendar dueCal = Calendar.getInstance();
        dueCal.set(Calendar.MILLISECOND, 0);
        attachment.setDue(dueCal.getTime());
        Address address2 = new Address();
        address2.setPostalCode("00200");
        address2.setPostOffice("post office2");
        address2.setStreet("street address 2");
        attachment.setAddress(address);
        ao.setAttachments(Lists.newArrayList(attachment));
        Requirement r = new Requirement();
        r.setDescription(new I18N("requirement fi", "requirement sv", "requirement en"));
        ao.setRequirements(Lists.newArrayList(r));
        ao.setApplicationSystem("haku_id1");
        ao.setApplicationPeriod(applicationPeriod("hakuaika_id1"));
        ao.setApplicationPeriodId("hakuaika_id1");
        ao.setFirstTimePositions(new Integer(2));
        ao.setAoFormUrl("https://opintopolku-url");
        return ao;
    }

    public static ApplicationSystem applicationSystemWithApplicationForm(String oid) {
        ApplicationPeriod ap = applicationPeriod("ap id");
        Season applicationSeason = applicationSeason();
        Season educationSeason = educationSeason();

        return new ApplicationSystem(
                oid,
                new I18N("as name fi", "as name sv", "as name en"),
                new I18N("method fi", "method sv", "method en"),
                2015,
                applicationSeason,
                2016,
                educationSeason,
                "www.applicationform.url",
                true,
                Lists.newArrayList(ap)
        );
    }

    public static ApplicationSystem applicationSystemWithoutApplicationForm(String oid) {
        ApplicationPeriod ap = applicationPeriod("ap id 2");
        Season applicationSeason = applicationSeason();
        Season educationSeason = educationSeason();

        return new ApplicationSystem(
                oid,
                new I18N("as name fi", "as name sv", "as name en"),
                new I18N("method fi", "method sv", "method en"),
                2015,
                applicationSeason,
                2016,
                educationSeason,
                null,
                false,
                Lists.newArrayList(ap)
        );
    }

    private static Season applicationSeason() {
        return new Season("K", new I18N("a season fi", "a season sv", "a season en"));
    }

    private static Season educationSeason() {
        return new Season("S", new I18N("e season fi", "e season sv", "e season en"));
    }

    public static ApplicationPeriod applicationPeriod(String id) {
        Calendar apStartsCal = Calendar.getInstance();
        apStartsCal.set(Calendar.MILLISECOND, 0);
        Date apStarts = apStartsCal.getTime();
        Date apEnds = new Date(apStarts.getTime() + 3600000L);

        return new ApplicationPeriod(id, new I18N("ap name fi", "ap name sv", "ap name en"), apStarts, apEnds);
    }

    public static Organization organization(String oid) {
        return new Organization(oid,
                new I18N("name fi", "name sv", "name en"),
                new I18N("outline fi", "outline sv", "outline en"),
                new I18N("expenses fi", "expenses sv", "expenses en"),
                new I18N("international fi", "international sv", "internationl en"),
                new I18N("transfer fi", "transfer sv", "transfer en"),
                new I18N("environment fi", "environment sv", "environment en"),
                new I18N("accessibility fi", "accessibility sv", "accessibility en"),
                new I18N("yearclock fi", "yearclock sv", "yearclock en"),
                new I18N("people fi", "people sv", "people en"),
                new I18N("selection fi", "selection sv", "selection en"),
                new I18N("pervious fi", "previous sv", "previous en"),
                new I18N("language fi", "language sv", "language en"),
                new I18N("internship fi", "internship sv", "internship en"),
                new Some(oid,
                        new I18N("facebook fi", "facebook sv", "facebook en"),
                        new I18N("google plus fi", "google plus sv", "google plus en"),
                        new I18N("linkedin fi", "linkedin sv", "linkedin en"),
                        new I18N("twitter fi", "twitter sv", "twitter en"),
                        new I18N("other fi", "other sv", "other en"),
                        new I18N("instagram fi", "instagram sv", "instagram en"),
                        new I18N("youtube fi", "youtube sv", "youtube en")
                ),
                Lists.newArrayList(

                        new ContactInfo("contact oid 1", ContactInfo.TYPE.CONTACT, "fi", "c www fi",
                                "c phone fi", "c email fi", "c fax fi", new Address("c street visit", "c postal code visit", "c post office visit"),
                                new Address("c street post", "c postal code post", "c post office post")),
                        new ContactInfo("contact oid 2", ContactInfo.TYPE.CONTACT, "sv", "c www sv",
                                "c phone sv", "c email sv", "c fax sv", new Address("c street visit", "c postal code visit", "c post office visit"),
                                new Address("c street post", "c postal code post", "c post office post")),
                        new ContactInfo("contact oid 3", ContactInfo.TYPE.CONTACT, "en", "c www en",
                                "c phone en", "c email en", "c fax en", new Address("c street visit", "c postal code visit", "c post office visit"),
                                new Address("c street post", "c postal code post", "c post office post"))
                        ),
                Lists.newArrayList(
                        new ContactInfo("applicant oid 1", ContactInfo.TYPE.APPLICANT, "fi", "a www fi",
                                "a phone fi", "a email fi", "a fax fi", new Address("a street visit", "a postal code visit", "a post office visit"),
                                new Address("a street post", "a postal code post", "a post office post")),
                        new ContactInfo("applicant oid 2", ContactInfo.TYPE.APPLICANT, "sv", "a www sv",
                                "a phone sv", "a email sv", "a fax sv", new Address("a street visit", "a postal code visit", "a post office visit"),
                                new Address("a street post", "a postal code post", "a post office post")),
                        new ContactInfo("applicant oid 3", ContactInfo.TYPE.APPLICANT, "en", "a www en",
                                "a phone en", "a email en", "a fax en", new Address("a street visit", "a postal code visit", "a post office visit"),
                                new Address("a street post", "a postal code post", "a post office post"))
                        )
        );
    }

}
