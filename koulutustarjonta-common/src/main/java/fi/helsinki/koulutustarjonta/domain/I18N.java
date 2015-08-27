package fi.helsinki.koulutustarjonta.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class I18N {

    private String fi;
    private String sv;
    private String en;

    public I18N(String fi, String sv, String en) {
        this.fi = fi;
        this.sv = sv;
        this.en = en;
    }

    public String getFi() {
        return fi;
    }

    public String getSv() {
        return sv;
    }

    public String getEn() {
        return en;
    }

    public String getLang(String lang) {
        if (!availableTranslations().contains(lang))
            return new String();

        if (lang.equals("fi"))
            return fi;
        else if (lang.equals("sv"))
            return sv;
        else if (lang.equals("en"))
            return en;
        else
            return new String();
    }

    public List<String> availableTranslations() {
        List<String> translations = Lists.newArrayList();
        if (fi != null) {
            translations.add("fi");
        }
        if (sv != null) {
            translations.add("sv");
        }
        if (en != null) {
            translations.add("en");
        }
        return translations;
    }
}
