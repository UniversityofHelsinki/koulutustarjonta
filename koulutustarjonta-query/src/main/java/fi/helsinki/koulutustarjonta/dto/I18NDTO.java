package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hannu Lyytikainen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class I18NDTO {

    private String fi;
    private String sv;
    private String en;

    public I18NDTO() {
    }

    public I18NDTO(String fi, String sv, String en) {
        this.fi = fi;
        this.sv = sv;
        this.en = en;
    }

    @JsonProperty("fi")
    public String getFi() {
        return fi;
    }

    @JsonProperty("sv")
    public String getSv() {
        return sv;
    }

    @JsonProperty("en")
    public String getEn() {
        return en;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
