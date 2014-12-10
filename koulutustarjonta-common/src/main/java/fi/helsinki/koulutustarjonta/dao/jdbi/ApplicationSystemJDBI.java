package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindApplicationPeriod;
import fi.helsinki.koulutustarjonta.dao.binder.BindApplicationSystem;
import fi.helsinki.koulutustarjonta.dao.mapper.ApplicationSystemJoinRowMapper;
import fi.helsinki.koulutustarjonta.dao.util.ApplicationSystemJoinRow;
import fi.helsinki.koulutustarjonta.domain.ApplicationPeriod;
import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@UseStringTemplate3StatementLocator("/db/sql/templates/ApplicationSystemJDBI.sql.stg")
public interface ApplicationSystemJDBI extends Transactional<ApplicationSystemJDBI> {

    @SqlQuery
    @Mapper(ApplicationSystemJoinRowMapper.class)
    List<ApplicationSystemJoinRow> findByOid(@Bind("id") String oid);

    @SqlQuery
    @Mapper(ApplicationSystemJoinRowMapper.class)
    List<ApplicationSystemJoinRow> findAll();

    @SqlUpdate
    void upsertApplicationSystem(@BindApplicationSystem ApplicationSystem applicationSystem);

    @SqlBatch
    @BatchChunkSize(10)
    void upsertApplicationPeriods(@BindApplicationPeriod List<ApplicationPeriod> applicationPeriods,
                                  @Bind("id_haku") String applicationSystemOid);

    @SqlUpdate
    void removeDeletedApplicationPeriods(@Bind("id_haku") String applicationSystemOid,
                                         @BindIn("current") List<String> currentPeriodIds);

    @SqlUpdate("UPDATE hakukohde SET id_hakuaika = NULL " +
            "WHERE id_haku=:id_haku AND id_hakuaika NOT IN (<current>)")
    void clearDeletedApplicationPeriodsFromApplicationOptions(@Bind("id_haku") String applicationSystemOid,
                                         @BindIn("current") List<String> currentPeriodIds);
}
