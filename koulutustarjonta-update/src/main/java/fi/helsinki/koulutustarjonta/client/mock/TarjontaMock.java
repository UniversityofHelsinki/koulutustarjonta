package fi.helsinki.koulutustarjonta.client.mock;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.domain.*;

import java.util.ArrayList;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaMock {

    public LearningOpportunity getDegree() {
        return buildLearningOpportunity();
    }

    private LearningOpportunity buildLearningOpportunity() {
        LearningOpportunity learningOpportunity = new LearningOpportunity();
        learningOpportunity.setOid("1.2.246.562.17.17939899864");
        learningOpportunity.setDegreeProgram(new I18N("Filosofian maisteri", null, null));
        learningOpportunity.setEducationalField(new I18N("Kemia", null, null));
        learningOpportunity.setStartYear(2015);
        learningOpportunity.setStartSeason(new I18N("Kevät", null, null));
        learningOpportunity.setPlannedDurationUnit(new I18N("vuotta", null, null));
        learningOpportunity.setPlannedDurationValue(3);
        learningOpportunity.setCreditValue(120);
        learningOpportunity.setCreditUnit(new I18N("op", null, null));
        learningOpportunity.setTeachingLanguages(Lists.newArrayList(new TeachingLanguage("fi", new I18N("suomi", null, null))));
        learningOpportunity.setTranslations(Lists.newArrayList("fi"));
        learningOpportunity.setGoals(loremFi());
        learningOpportunity.setLanguageInfo(loremFi());
        learningOpportunity.setSelectingMajorSubject(loremFi());
        learningOpportunity.setWorkLifePlacement(loremFi());
        learningOpportunity.setCompetency(loremFi());
        learningOpportunity.setPostgraduateStudies(loremFi());
        learningOpportunity.setContents(loremFi());
        learningOpportunity.setStructure(loremFi());
        learningOpportunity.setThesis(loremFi());
        learningOpportunity.setInternationalization(loremFi());
        learningOpportunity.setCooperation(loremFi());
        learningOpportunity.setResearch(loremFi());
        learningOpportunity.setApplicationOptions(Lists.newArrayList(buildApplicationOption()));
        learningOpportunity.setProvider(buildOrganization());
        return learningOpportunity;
    }

    private ApplicationOption buildApplicationOption() {
        ApplicationOption ao = new ApplicationOption();
        ao.setOid("1.2.246.562.20.133716233010");
        ao.setName(new I18N("Kemia, luonnontieteiden kandidaatti ja filosofian maisteri", null, null));
        ao.setStartingQuota(10);
        ao.setApplicationSuitabilityRequirements(Lists.newArrayList(loremFi()));
        ao.setApplicationSuitabilityRequirementDescription(loremFi());
        ao.setAdditionalInfo(loremFi());
        ao.setSelectionCriteria(loremFi());
        ao.setSora(loremFi());
        ao.setExams(new ArrayList<Exam>());
        ao.setAttachments(new ArrayList<Attachment>());
        ao.setApplicationSystem(buildApplicationSystem());
        return ao;
    }


    private ApplicationSystem buildApplicationSystem() {
        ApplicationSystem as = new ApplicationSystem();
        as.setOid("1.2.246.562.29.15511233411");
        as.setName(new I18N("Haku maisterikoulutukseen (Helsingin yliopisto, oikeustieteellinen tiedekunta)", null, null));
        as.setApplicationMethod(loremFi());
        as.setApplicationYear("2015");
        return as;
    }

    private Organization buildOrganization() {
        Organization o = new Organization();
        o.setOid("1.2.246.562.10.94639300915");
        o.setName(new I18N("Helsingin yliopisto, Matemaattis-luonnontieteellinen tiedekunta", null, null));
        return o;
    }




    private I18N loremFi() {
        return new I18N("Lorem ipsum dolor sit amet, consectetur adipisicing", null, null);
    }

}
