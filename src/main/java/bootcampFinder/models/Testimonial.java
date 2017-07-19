package bootcampFinder.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by student on 7/17/17.
 */
@Entity
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private long bootcampId;

    private String userName;

    private String message;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(long bootcampId) {
        this.bootcampId = bootcampId;
    }
}
