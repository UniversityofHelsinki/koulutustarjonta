package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class Attachment {

    private String oid;
    private String lang;
    private String name;
    private String description;
    private Date due;
    private Address address;
    private String receiver; // liitteenVastaanottaja
}

