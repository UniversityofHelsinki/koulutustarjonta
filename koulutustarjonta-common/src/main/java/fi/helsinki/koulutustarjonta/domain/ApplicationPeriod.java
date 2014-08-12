package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationPeriod {
    private String id;
    private String name;
    private Date starts;
    private Date ends;
}
