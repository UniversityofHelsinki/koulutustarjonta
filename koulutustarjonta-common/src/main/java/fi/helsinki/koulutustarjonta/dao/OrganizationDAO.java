package fi.helsinki.koulutustarjonta.dao;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.OrganizationJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.OrganizationObjectBuilder;
import fi.helsinki.koulutustarjonta.dao.util.OrganizationJoinRow;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationDAO {

    private final OrganizationJDBI jdbi;

    public OrganizationDAO(OrganizationJDBI jdbi) {
        this.jdbi = jdbi;
    }

    public void save(Organization organization) {
        jdbi.begin();
        jdbi.upsert(organization);
        List<ContactInfo> combinedContactInfos = Lists.newArrayList(organization.getContactInfos());
        combinedContactInfos.addAll(organization.getApplicantServices());
        jdbi.upsertContactInfos(combinedContactInfos, organization.getOid());
        jdbi.removeDeletedContactInfos(organization.getOid(), combinedContactInfos.stream()
                .map(info -> info.getOid()).collect(toList()));
        jdbi.commit();
    }

    public Organization findByOid(String oid) throws ResourceNotFound {
        List<OrganizationJoinRow> joinRows = jdbi.findByOid(oid);
        if (joinRows.size() == 0) {
            throw new ResourceNotFound(Organization.class, oid);
        }
        return OrganizationObjectBuilder.buildOne(joinRows);
    }

    public List<Organization> findAll() {
        return OrganizationObjectBuilder.build(jdbi.findAll());
    }
}
