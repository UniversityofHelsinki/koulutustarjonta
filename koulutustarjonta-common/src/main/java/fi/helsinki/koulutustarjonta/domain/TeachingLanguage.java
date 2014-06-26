package fi.helsinki.koulutustarjonta.domain;

/**
 * @author Hannu Lyytikainen
 */
public class TeachingLanguage {
    String lang;
    I18N name;

    public TeachingLanguage(String lang, I18N name) {
        this.lang = lang;
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public I18N getName() {
        return name;
    }

    public void setName(I18N name) {
        this.name = name;
    }
}
