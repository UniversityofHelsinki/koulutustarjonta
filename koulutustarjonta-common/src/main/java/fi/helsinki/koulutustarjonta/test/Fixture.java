package fi.helsinki.koulutustarjonta.test;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.*;

import java.util.Date;

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

    public static ApplicationOption applicationOption(String oid) {
        ApplicationOption ao = new ApplicationOption();
        ao.setOid(oid);
        ao.setName(new I18N("name fi", "name sv", "name en"));
        ao.setStartingQuota(10);
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
        Date starts = new Date();
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
        attachment.setDue(new Date());
        Address address2 = new Address();
        address2.setPostalCode("00200");
        address2.setPostOffice("post office2");
        address2.setStreet("street address 2");
        attachment.setAddress(address);
        ao.setAttachments(Lists.newArrayList(attachment));
        return ao;
    }


}
