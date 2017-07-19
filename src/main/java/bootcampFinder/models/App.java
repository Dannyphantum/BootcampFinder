package bootcampFinder.models;

import javax.persistence.*;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="applicantId")
    private long appId;

    private String degree;

    private String skills;

    private String yrsExperience;

    private String userName;


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

    public String getYrsExperience() {
        return yrsExperience;
    }

    public void setYrsExperience(String yrsExperience) {
        this.yrsExperience = yrsExperience;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void makeNew(String userName) {
        degree = "please add your degrees";
        skills = "please add your skills";
        yrsExperience = "please provide your experience";
        this.userName = userName;
    }

}
