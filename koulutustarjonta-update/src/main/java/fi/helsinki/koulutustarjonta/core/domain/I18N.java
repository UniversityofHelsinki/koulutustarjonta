package fi.helsinki.koulutustarjonta.core.domain;

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
}
