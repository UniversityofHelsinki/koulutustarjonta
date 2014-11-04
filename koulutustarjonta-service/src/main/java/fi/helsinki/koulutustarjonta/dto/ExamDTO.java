package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
public class ExamDTO {
    private String oid;
    private String lang;
    private String type;
    private String description;
    private List<ExamEventDTO> events;

    public ExamDTO() {
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("kuvaus")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("koetapahtumat")
    public List<ExamEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<ExamEventDTO> events) {
        this.events = events;
    }
}
