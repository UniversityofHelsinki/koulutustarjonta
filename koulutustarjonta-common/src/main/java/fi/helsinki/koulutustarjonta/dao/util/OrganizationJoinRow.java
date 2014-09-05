package fi.helsinki.koulutustarjonta.dao.util;

import fi.helsinki.koulutustarjonta.domain.ContactInfo;
import fi.helsinki.koulutustarjonta.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
public class OrganizationJoinRow {
    private final Organization organization;
    private final ContactInfo contactInfo;
}
