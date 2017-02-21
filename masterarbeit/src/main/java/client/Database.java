package client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Database {

    public List<String> firstNameMale;
    public List<String> firstNameFemale;
    public List<String> lastName;
    public List<String> city;
    public List<String> street;

    @JsonCreator
    public Database(
            @JsonProperty("first name male") List<String> firstNameMale,
            @JsonProperty("first name female") List<String> firstNameFemale,
            @JsonProperty("last name") List<String> lastName,
            @JsonProperty("city") List<String> city,
            @JsonProperty("street") List<String> street) {
        this.firstNameMale = firstNameMale;
        this.firstNameFemale = firstNameFemale;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
    }

}