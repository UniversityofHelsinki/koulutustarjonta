package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class Address {

    private String street;
    private String postalCode;
    private String postOffice;
}
