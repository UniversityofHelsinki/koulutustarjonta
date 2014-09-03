package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
public class ContactInfo {
    public enum TYPE {CONTACT, APPLICANT}

    private final String oid;
    private final TYPE type;
    private final String lang;
    private final String www;
    private final String phone;
    private final String email;
    private final String fax;
    private final Address visitingAddress;
    private final Address postalAddress;
}
