package bootcampFinder.models;

import javax.persistence.*;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="applicantId")
    private long appId;

    private String degree;

    private String skills;

    private int yrsExperience;

    private long userId;


    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getYrsExperience() {
        return yrsExperience;
    }

    public void setYrsExperience(int yrsExperience) {
        this.yrsExperience = yrsExperience;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



}
