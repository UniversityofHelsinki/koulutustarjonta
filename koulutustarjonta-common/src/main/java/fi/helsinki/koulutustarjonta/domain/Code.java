package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * OPH koodi
 *
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class Code {

    private String uri;
    private String value;
    private I18N name;
    private I18N shortName;
    private I18N description;

    public Code() {
    }
}
