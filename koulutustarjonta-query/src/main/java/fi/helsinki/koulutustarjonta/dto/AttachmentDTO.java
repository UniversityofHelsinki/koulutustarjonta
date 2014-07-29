package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Hannu Lyytikainen
 */
public class AttachmentDTO {
    private String oid;
    private String lang;
    private String name;
    private String description;
    private Date due;
    private AddressDTO address;

    public AttachmentDTO() {
    }

    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @JsonProperty("kieli")
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("tyyppi")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("kuvaus")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("erapaiva")
    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    @JsonProperty("toimitusosoite")
    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
