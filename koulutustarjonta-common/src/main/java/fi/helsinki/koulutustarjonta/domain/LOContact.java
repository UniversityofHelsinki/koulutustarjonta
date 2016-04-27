package fi.helsinki.koulutustarjonta.domain;

import lombok.Value;

import java.util.List;

@Value
public class LOContact {
    public enum Type {
        CONTACT_PERSON //todo add more if needed
    }

    private final String name;
    private final String title;
    private final String email;
    private final String phoneNumber;
    private final List<String> languages;
    private final Type contactType;
}
