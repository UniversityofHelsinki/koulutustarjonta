package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindLOContact;
import fi.helsinki.koulutustarjonta.dao.mapper.LOContactMapper;
import fi.helsinki.koulutustarjonta.domain.LOContact;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@UseStringTemplate3StatementLocator("/db/sql/templates/LOContactJDBI.sql.stg")
@RegisterMapper(LOContactMapper.class)
public interface LOContactJDBI extends Transactional<LOContactJDBI>{

    @SqlUpdate
    void insert(@BindLOContact LOContact loContact, @Bind("loid") String loid);

    @SqlUpdate
    void removeByLearningOpportunityID(@Bind("loid") String loid);

    @SqlQuery
    List<LOContact> findByLOId(@Bind("oid_lo") String loid);
}
