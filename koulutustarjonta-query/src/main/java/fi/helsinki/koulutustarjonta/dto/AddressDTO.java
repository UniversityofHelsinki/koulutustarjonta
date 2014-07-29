package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hannu Lyytikainen
 */
public class AddressDTO {
    private String street;
    private String postalCode;
    private String postOffice;

    public AddressDTO() {
    }

    @JsonProperty("katuosoite")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("postinumero")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("postitoimipaikka")
    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }
}
