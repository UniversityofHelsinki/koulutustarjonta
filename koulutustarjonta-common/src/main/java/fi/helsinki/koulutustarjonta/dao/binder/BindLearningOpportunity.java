package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * @author Hannu Lyytikainen
 */
@BindingAnnotation(BindLearningOpportunity.LearningOpportunityBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindLearningOpportunity {
    static final Logger LOG = Logger.getLogger(BindLearningOpportunity.class);
    public static class LearningOpportunityBinderFactory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            return new Binder<BindLearningOpportunity, LearningOpportunity>()
            {
                public void bind(SQLStatement q, BindLearningOpportunity bind, LearningOpportunity lo)
                {
                    q.bind("id", lo.getOid());
                    BindUtil.bindI18N(q, "tutkintonimike", lo.getQualification());
                    BindUtil.bindI18N(q, "opintoala", lo.getEducationalField());
                    BindUtil.bindI18N(q, "tutkintoohjelma", lo.getDegreeProgram());
                    q.bind("alkamisvuosi", lo.getStartYear());
                    BindUtil.bindI18N(q, "alkamiskausi", lo.getStartSeason());
                    q.bind("suunni_kesto", lo.getPlannedDurationValue());
                    BindUtil.bindI18N(q, "suunni_tyyppi", lo.getPlannedDurationUnit());
                    q.bind("laajuus", lo.getCreditValue());
                    BindUtil.bindI18N(q, "laajuus_tyyppi", lo.getCreditUnit());
                    BindUtil.bindI18N(q, "rakenne", lo.getStructure());
                    BindUtil.bindI18N(q, "tavoitteet", lo.getGoals());
                    BindUtil.bindI18N(q, "mahdollisuudet", lo.getPostgraduateStudies());
                    BindUtil.bindI18N(q, "patevyys", lo.getCompetency());
                    BindUtil.bindI18N(q, "lisat_opkiel", lo.getLanguageInfo());
                    BindUtil.bindI18N(q, "yhteistyo", lo.getCooperation());
                    BindUtil.bindI18N(q, "paaaineval", lo.getSelectingMajorSubject());
                    BindUtil.bindI18N(q, "kansval", lo.getInternationalization());
                    BindUtil.bindI18N(q, "sijtyo", lo.getWorkLifePlacement());
                    BindUtil.bindI18N(q, "sisalto", lo.getContents());
                    BindUtil.bindI18N(q, "tutkpaino", lo.getResearch());
                    BindUtil.bindI18N(q, "opinnaytetyo", lo.getThesis());
                    q.bind("id_organisaatio", lo.getProvider());
                }
            };
        }
    }
}

