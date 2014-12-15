package fi.helsinki.koulutustarjonta.dao.mapper;

import fi.helsinki.koulutustarjonta.dao.util.OrganizationJoinRow;
import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationObjectMapper {

    public static Organization buildOne(List<OrganizationJoinRow> joinRows) {
        return build(joinRows).get(0);
    }

    public static List<Organization> build(List<OrganizationJoinRow> joinRows) {
        return joinRows.parallelStream()
                .collect(groupingBy(row -> row.getOrganization().getOid()))
                .values()
                .parallelStream()
                .map(rows -> resolveOrganization(rows))
                .collect(toList());
    }

    private static Organization resolveOrganization(List<OrganizationJoinRow> joinRows) {
        Organization organization = joinRows.get(0).getOrganization();
        List<ContactInfo> contactInfos = joinRows.parallelStream()
                .filter(row -> row.getContactInfo() != null
                        && row.getContactInfo().getType().equals(ContactInfo.TYPE.CONTACT))
                .collect(groupingBy(row -> row.getContactInfo().getOid()))
                .values()
                .parallelStream()
                .map(rows -> rows.get(0).getContactInfo())
                .collect(toList());
        List<ContactInfo> applicationServices = joinRows.parallelStream()
                .filter(row -> row.getContactInfo() != null
                        && row.getContactInfo().getType().equals(ContactInfo.TYPE.APPLICANT))
                .collect(groupingBy(row -> row.getContactInfo().getOid()))
                .values()
                .parallelStream()
                .map(rows -> rows.get(0).getContactInfo())
                .collect(toList());

        return organization.addContactInfos(contactInfos).addApplicantServices(applicationServices);
    }
}
