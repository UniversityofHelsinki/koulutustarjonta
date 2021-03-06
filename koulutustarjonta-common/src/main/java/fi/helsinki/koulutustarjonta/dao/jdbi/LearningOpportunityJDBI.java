package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindLearningOpportunity;
import fi.helsinki.koulutustarjonta.dao.binder.BindTeachingLanguage;
import fi.helsinki.koulutustarjonta.dao.mapper.LearningOpportunityMapper;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.domain.TeachingLanguage;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@UseStringTemplate3StatementLocator("/db/sql/templates/LearningOpportunityJDBI.sql.stg")
@RegisterMapper(LearningOpportunityMapper.class)
public interface LearningOpportunityJDBI extends Transactional<LearningOpportunityJDBI> {

    @SqlUpdate
    void upsert(@BindLearningOpportunity LearningOpportunity learningOpportunity);

    @SqlQuery
    List<LearningOpportunity> findAllJoinRows();

    @SqlQuery
    List<LearningOpportunity> findJoinRowsById(@Bind("id") String id);

    @SqlBatch
    @BatchChunkSize(10)
    void insertTeachingLanguages(@BindTeachingLanguage List<TeachingLanguage> teachingLanguage);

    @SqlUpdate
    void addKeywordToLearningOpportunity(@Bind("id_koulutus") String learningOpportunityId,
                                         @Bind("keyword_fi") String kw_fi,
                                         @Bind("keyword_sv") String kw_sv,
                                         @Bind("keyword_en") String kw_en
    );

    @SqlBatch
    @BatchChunkSize(10)
    void addTeachingLanguagesToLearningOpportunity(@Bind("id_koulutus") String learningOpportunityId,
                                                   @Bind("id_opetuskieli") List<String> teachingLanguageIds);

    @SqlUpdate
    void removeTeachingLanguagesFromLearningOpportunity(@Bind("id") String learningOpportunityId);

    @SqlUpdate
    void removeKeywordsFromLearningOpportunity(@Bind("id") String learningOpportunityId);

    @SqlBatch
    @BatchChunkSize(10)
    void addApplicationOptions(@Bind("id_koulutus") String learningOpportunityOid, @Bind("id_hakukohde") List<String> applicationOptionOids);

    @SqlUpdate
    void removeDeletedApplicationOptions(@Bind("id_koulutus") String learningOpportunityOid,
                                         @BindIn("current") List<String> currentApplicationOptionOids);

    @SqlBatch
    @BatchChunkSize(10)
    void addParents(@Bind("id_lapsi") String childOid, @Bind("id_vanhempi") List<String> parentOids);

    @SqlBatch
    @BatchChunkSize(10)
    void addChildren(@Bind("id_vanhempi") String parentOid, @Bind("id_lapsi") List<String> childOids);

    @SqlBatch
    @BatchChunkSize(10)
    void addProviders(@Bind("id_koulutus") String learningOpportunityOid, @Bind("id_tarjoaja") List<String> providers);

    @SqlUpdate
    void removeProviders(@Bind("id_koulutus") String learningOpportunityOid);

    @SqlUpdate
    void removeOutdatedLearningOpportunities(@Bind("current_year") Integer currentYear,
                                             @Bind("current_month") Integer currentMonth,
                                             @Bind("current_day") Integer currentDay);
}
