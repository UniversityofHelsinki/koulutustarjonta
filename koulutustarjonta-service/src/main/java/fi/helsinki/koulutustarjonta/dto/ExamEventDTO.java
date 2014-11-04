package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
public class ExamEventDTO {
    private String oid;
    private Date starts;
    private Date ends;
    private String info;
    private AddressDTO address;

    public ExamEventDTO() {
    }

    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @JsonProperty("alkaa")
    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    @JsonProperty("loppuu")
    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    @JsonProperty("kuvaus")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @JsonProperty("osoite")
    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
