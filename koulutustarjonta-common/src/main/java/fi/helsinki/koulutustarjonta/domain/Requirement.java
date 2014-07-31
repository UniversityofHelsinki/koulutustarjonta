package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class Requirement {

    public Requirement() {
    }

    public Requirement(I18N description) {
        this.description = description;
    }

    // DB generated id
    private long id;
    private I18N description;
}
