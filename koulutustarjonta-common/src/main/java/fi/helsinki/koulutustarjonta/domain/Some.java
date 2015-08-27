package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hannu Lyytikainen
 */
@AllArgsConstructor
@Getter
public class Some {
    private final String oid;
    private final I18N facebook;
    private final I18N googlePlus;
    private final I18N linkedIn;
    private final I18N twitter;
    private final I18N other;
    private final I18N instagram;
    private final I18N youtube;
}
