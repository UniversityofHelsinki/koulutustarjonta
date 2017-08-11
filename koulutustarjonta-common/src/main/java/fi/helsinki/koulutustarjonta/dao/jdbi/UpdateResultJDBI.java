package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindUpdateResult;
import fi.helsinki.koulutustarjonta.dao.mapper.UpdateResultMapper;
import fi.helsinki.koulutustarjonta.domain.UpdateResult;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

/**
 * @author Sebastian Monte
 */
@UseStringTemplate3StatementLocator("/db/sql/templates/UpdateResultJDBI.sql.stg")
@RegisterMapper(UpdateResultMapper.class)
public interface UpdateResultJDBI extends Transactional<UpdateResultJDBI> {

    @SqlUpdate
    void insert(@BindUpdateResult UpdateResult updateResult);

    @SqlQuery
    List<UpdateResult> findLatest(@Bind("limit") int limit);

}
