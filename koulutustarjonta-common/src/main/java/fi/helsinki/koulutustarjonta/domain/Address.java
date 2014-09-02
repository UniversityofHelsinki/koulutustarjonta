package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    private String street;
    private String postalCode;
    private String postOffice;
}
