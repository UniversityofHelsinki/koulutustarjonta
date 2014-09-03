package fi.helsinki.koulutustarjonta.dao;

import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.dao.jdbi.OrganizationJDBI;
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
        int updated = jdbi.update(organization);
        if (updated == 0) {
            jdbi.insert(organization);
        }
        List<ContactInfo> combinedContactInfos = Lists.newArrayList(organization.getContactInfos());
        combinedContactInfos.addAll(organization.getApplicantServices());
        jdbi.upsertContactInfos(combinedContactInfos);
        jdbi.removeDeletedContactInfos(organization.getOid(), combinedContactInfos.stream()
                .map(info -> info.getOid()).collect(toList()));
        jdbi.commit();
    }

}
