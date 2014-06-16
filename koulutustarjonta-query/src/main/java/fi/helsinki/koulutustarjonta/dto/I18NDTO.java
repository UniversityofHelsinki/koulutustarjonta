package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hannu Lyytikainen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class I18NDTO {

    private final String fi;
    private final String sv;
    private final String en;

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
}
