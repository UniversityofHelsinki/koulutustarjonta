package fi.helsinki.koulutustarjonta.dao;

import fi.helsinki.koulutustarjonta.dao.binder.BindLearningOpportunity;
import fi.helsinki.koulutustarjonta.dao.mapper.LearningOpportunityMapper;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public interface LearningOpportunityDAO {

    @SqlUpdate("insert into KOULUTUS_POC (id, tavoitteet_fi) values (:id, :tavoitteet_fi)")
    void insert(@BindLearningOpportunity LearningOpportunity learningOpportunity);

    @SqlUpdate("update KOULUTUS_POC " +
            "set tavoitteet_fi = :tavoitteet_fi " +
            "where id = :id")
    int update(@BindLearningOpportunity LearningOpportunity learningOpportunity);

    @SqlQuery("select * from KOULUTUS_POC")
    @Mapper(LearningOpportunityMapper.class)
    List<LearningOpportunity> findAll();

    @SqlQuery("select * from KOULUTUS_POC " +
            "where id = :id")
    @Mapper(LearningOpportunityMapper.class)
    LearningOpportunity findById(@Bind("id") String id);

}
