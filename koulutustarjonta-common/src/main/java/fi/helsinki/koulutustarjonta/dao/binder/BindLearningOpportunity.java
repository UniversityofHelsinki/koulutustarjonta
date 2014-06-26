package fi.helsinki.koulutustarjonta.dao.binder;

import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.util.Optional;

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
                private final I18N EMPTY_I18N = new I18N(null, null, null);

                public void bind(SQLStatement q, BindLearningOpportunity bind, LearningOpportunity lo)
                {
                    q.bind("id", lo.getOid());
                    bindI18N(q, "tutkintonimike", lo.getQualification());
                    bindI18N(q, "opintoala", lo.getEducationalField());
                    bindI18N(q, "tutkintoohjelma", lo.getDegreeProgram());
                    q.bind("alkamisvuosi", lo.getStartYear());
                    bindI18N(q, "alkamiskausi", lo.getStartSeason());
                    q.bind("suunni_kesto", lo.getPlannedDurationValue());
                    bindI18N(q, "suunni_tyyppi", lo.getPlannedDurationUnit());
                    q.bind("laajuus", lo.getCreditValue());
                    bindI18N(q, "laajuus_tyyppi", lo.getCreditUnit());
                    bindI18N(q, "rakenne", lo.getStructure());
                    bindI18N(q, "tavoitteet", lo.getGoals());
                    bindI18N(q, "mahdollisuudet", lo.getPostgraduateStudies());
                    bindI18N(q, "patevyys", lo.getCompetency());
                    bindI18N(q, "lisat_opkiel", lo.getLanguageInfo());
                    bindI18N(q, "yhteistyo", lo.getCooperation());
                    bindI18N(q, "paaaineval", lo.getSelectingMajorSubject());
                    bindI18N(q, "kansval", lo.getInternationalization());
                    bindI18N(q, "sijtyo", lo.getWorkLifePlacement());
                    bindI18N(q, "sisalto", lo.getContents());
                    bindI18N(q, "tutkpaino", lo.getResearch());
                    bindI18N(q, "opinnaytetyo", lo.getThesis());
                }

                private void bindI18N(SQLStatement q, String field, I18N i18n) {
                    Optional<I18N> optionalI18n = Optional.ofNullable(i18n);
                    bindI18N(q, field, optionalI18n);
                }

                private void bindI18N(SQLStatement q, String field, Optional<I18N> i18n) {
                        q.bind(String.format("%s_fi", field), i18n.orElse(EMPTY_I18N).getFi());
                        q.bind(String.format("%s_sv", field), i18n.orElse(EMPTY_I18N).getSv());
                        q.bind(String.format("%s_en", field), i18n.orElse(EMPTY_I18N).getEn());
                }
            };
        }
    }
}

