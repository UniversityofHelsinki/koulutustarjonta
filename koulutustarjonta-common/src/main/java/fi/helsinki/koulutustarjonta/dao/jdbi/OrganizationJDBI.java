package fi.helsinki.koulutustarjonta.dao.jdbi;

import fi.helsinki.koulutustarjonta.dao.binder.BindContactInfo;
import fi.helsinki.koulutustarjonta.dao.binder.BindOrganization;
import fi.helsinki.koulutustarjonta.dao.mapper.OrganizationJoinRowMapper;
import fi.helsinki.koulutustarjonta.dao.util.OrganizationJoinRow;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
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
@UseStringTemplate3StatementLocator("/db/sql/templates/OrganizationJDBI.sql.stg")
public interface OrganizationJDBI extends Transactional<OrganizationJDBI> {

    @SqlQuery
    @Mapper(OrganizationJoinRowMapper.class)
    List<OrganizationJoinRow> findByOid(@Bind("id") String oid);

    @SqlQuery
    @Mapper(OrganizationJoinRowMapper.class)
    List<OrganizationJoinRow> findAll();

    @SqlUpdate
    void upsert(@BindOrganization Organization organization);

    @SqlBatch
    @BatchChunkSize(10)
    void upsertContactInfos(@BindContactInfo List<ContactInfo> contactInfos, @Bind("id_organisaatio") String organizationOid);

    @SqlUpdate
    void removeDeletedContactInfos(@Bind("id_organisaatio") String organizationOid,
                                         @BindIn("current") List<String> currentContactInfoOids);

}
