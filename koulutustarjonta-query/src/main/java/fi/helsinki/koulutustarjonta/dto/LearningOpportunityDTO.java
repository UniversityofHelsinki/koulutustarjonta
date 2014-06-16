package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityDTO {

    private final String oid;
    private final I18NDTO goals;

    public LearningOpportunityDTO(String oid, I18NDTO goals) {
        this.oid = oid;
        this.goals = goals;
    }

    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    @JsonProperty("tavoitteet")
    public I18NDTO getGoals() {
        return goals;
    }
}
