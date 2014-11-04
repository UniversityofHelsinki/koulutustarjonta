package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hannu Lyytikainen
 */
public class TeachingLanguageDTO {

    String lang;
    I18NDTO name;

    public TeachingLanguageDTO() {
    }

    @JsonProperty("lyhenne")
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("nimi")
    public I18NDTO getName() {
        return name;
    }

    public void setName(I18NDTO name) {
        this.name = name;
    }
}
