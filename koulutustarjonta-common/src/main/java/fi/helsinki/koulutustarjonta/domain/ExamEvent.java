package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class ExamEvent {

    private String oid;
    private Address address;
    private Date starts;
    private Date ends;
    private String info;
}
