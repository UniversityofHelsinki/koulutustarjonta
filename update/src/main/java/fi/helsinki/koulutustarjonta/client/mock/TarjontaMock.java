package fi.helsinki.koulutustarjonta.client.mock;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.core.domain.*;

/**
 * @author Hannu Lyytikainen
 */
public class TarjontaMock {

    public Degree getDegree() {
        return buildDegree();
    }

    private Degree buildDegree() {
        Degree degree = new Degree();
        degree.setOid("1.2.246.562.17.17939899864");
        degree.setDegreeProgram(new I18N("Filosofian maisteri", null, null));
        degree.setEducationalField(new I18N("Kemia", null, null));
        degree.setStartYear("2015");
        degree.setStartSeason(new I18N("Kevät", null, null));
        degree.setPlannedDurationUnit(new I18N("vuotta", null, null));
        degree.setPlannedDurationValue("3");
        degree.setCreditValue("120");
        degree.setCreditUnit(new I18N("op", null, null));
        degree.setTeachingLanguages(Lists.newArrayList(new I18N("suomi", null, null)));
        degree.setTranslations(Lists.newArrayList("fi"));
        degree.setGoals(loremFi());
        degree.setLanguageInfo(loremFi());
        degree.setSelectingMajorSubject(loremFi());
        degree.setWorkLifePlacement(loremFi());
        degree.setCompetency(loremFi());
        degree.setPostgraduateStudies(loremFi());
        degree.setContents(loremFi());
        degree.setStructure(loremFi());
        degree.setThesis(loremFi());
        degree.setInternationalization(loremFi());
        degree.setCooperation(loremFi());
        degree.setResearch(loremFi());
        degree.setApplicationOptions(Lists.newArrayList(buildApplicationOption()));
        degree.setProvider(buildOrganization());
        return degree;
    }

    private ApplicationOption buildApplicationOption() {
        ApplicationOption ao = new ApplicationOption();
        ao.setOid("1.2.246.562.20.133716233010");
        ao.setName(new I18N("Kemia, luonnontieteiden kandidaatti ja filosofian maisteri", null, null));
        ao.setStartingQuota(10);
        ao.setApplicationSuitabilityRequirements(Lists.newArrayList(loremFi()));
        ao.setApplicationSuitabilityrequirementDescription(loremFi());
        ao.setAdditionalInfo(loremFi());
        ao.setSelectionCriteria(loremFi());
        ao.setSora(loremFi());
        ao.setExams(Lists.newArrayList());
        ao.setAttachments(Lists.newArrayList());
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
