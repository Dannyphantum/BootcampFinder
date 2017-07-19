package bootcampFinder.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class Bootcamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bootcampId;

    private String bootcampName;

    private String streetAddress;

    private String state;

    private String city;

    private String zipCode;

    private String topics;

    private String description;

    private String bootcampDirector;

    private String enabled;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public long getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(long bootcampId) {
        this.bootcampId = bootcampId;
    }

    public String getBootcampName() {
        return bootcampName;
    }

    public void setBootcampName(String bootcampName) {
        this.bootcampName = bootcampName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBootcampDirector() {
        return bootcampDirector;
    }

    public void setBootcampDirector(String bootcampDirector) {
        this.bootcampDirector = bootcampDirector;
    }

    public void makeNew(String bootcampDirector) {
        bootcampName = "please chose a name";
        streetAddress = "please give a Street";
        state = "please give a state";
        city = "please give a city";
        zipCode  ="please give a zip code";
        topics = "please add topics";
        description = "please add a description";
        enabled = "disabled";
        this.bootcampDirector = bootcampDirector;
    }

    public Bootcamp enable() {
        enabled = "enabled";
        return this;
    }

    public Bootcamp disable() {
        enabled = "disabled";
        return this;
    }
}
