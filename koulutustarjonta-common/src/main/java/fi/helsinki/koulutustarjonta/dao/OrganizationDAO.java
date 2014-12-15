package fi.helsinki.koulutustarjonta.dao;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dao.jdbi.OrganizationJDBI;
import fi.helsinki.koulutustarjonta.dao.mapper.OrganizationObjectMapper;
import fi.helsinki.koulutustarjonta.dao.util.OrganizationJoinRow;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationDAO {
    private static final Logger LOG = LoggerFactory.getLogger(OrganizationDAO.class);

    private final OrganizationJDBI jdbi;

    public OrganizationDAO(OrganizationJDBI jdbi) {
        this.jdbi = jdbi;
    }

    public void save(Organization organization) {
        LOG.debug(String.format("Saving organization %s", organization.getOid()));
        jdbi.begin();
        try {
            jdbi.upsert(organization);
            List<ContactInfo> combinedContactInfos = Lists.newArrayList(organization.getContactInfos());
            combinedContactInfos.addAll(organization.getApplicantServices());
            jdbi.upsertContactInfos(combinedContactInfos, organization.getOid());
            jdbi.removeDeletedContactInfos(organization.getOid(), combinedContactInfos.stream()
                    .map(info -> info.getOid()).collect(toList()));
            jdbi.commit();
        }
        catch (Exception e) {
            LOG.warn("Failed to save organization, rolling back");
            jdbi.rollback();
            throw e;
        }
    }

    public Organization findByOid(String oid) throws ResourceNotFound {
        List<OrganizationJoinRow> joinRows = jdbi.findByOid(oid);
        if (joinRows.size() == 0) {
            throw new ResourceNotFound(Organization.class, oid);
        }
        return OrganizationObjectMapper.buildOne(joinRows);
    }

    public List<Organization> findAll() {
        return OrganizationObjectMapper.build(jdbi.findAll());
    }
}
