package fi.helsinki.koulutustarjonta.domain;

import lombok.Value;
import lombok.experimental.Builder;

import java.util.List;

@Value
@Builder
public class LOContact {

    public enum Type {
        YHTEYSHENKILO //contact person
    }
    private final Long id;
    private final String name;
    private final String title;
    private final String email;
    private final String phoneNumber;
    private final List<String> languages;
    private final String contactType;
}
