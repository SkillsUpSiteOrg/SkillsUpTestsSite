package ua.dp.skillsup.tests.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.*;

/**
 * Created by SERG on 29.01.15.
 */
@Proxy(lazy=false)
@Entity
@Table(name = "USER_RESULTS")
public class UserResults {

    public UserResults(){
        this.dateOfPassed = new Date();
        this.userQuestionResults = new HashMap<String, Integer>();
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long userResultsId;

    @Column(name = "USER_NAME")
    private String userName;

    @JsonIgnore
    @Column(name = "USER_SECRET")
    private String userSecret;

    @Column(name = "TEST_NAME")
    private String testName;

    @Column(name = "DATE_OF_PASSED")
    private Date dateOfPassed;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "USER_ANSWERS_OF_TEST")
    private Map<String,Integer> userQuestionResults;

    @Column(name = "RESULT")
    private int result;

    public long getUserResultsId() {
        return userResultsId;
    }

    public void setUserResultsId(long userResultsId) {
        this.userResultsId = userResultsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getDateOfPassed() {
        return dateOfPassed;
    }

    public void setDateOfPassed(Date dateOfPassed) {
        this.dateOfPassed = dateOfPassed;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Map<String, Integer> getUserQuestionResults() {
        return userQuestionResults;
    }

    public void setUserQuestionResults(Map<String, Integer> userQuestionResults) {
        this.userQuestionResults = userQuestionResults;
    }

    @Override
    public String toString() {
        return "UserResults{" +
                "userResultsId=" + userResultsId +
                ", userName='" + userName + '\'' +
                ", userSecret='" + userSecret + '\'' +
                ", testName='" + testName + '\'' +
                ", dateOfPassed=" + dateOfPassed +
                ", userQuestionResults=" + userQuestionResults +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserResults)) return false;

        UserResults that = (UserResults) o;

        if (result != that.result) return false;
        if (userResultsId != that.userResultsId) return false;
        if (dateOfPassed != null ? !dateOfPassed.equals(that.dateOfPassed) : that.dateOfPassed != null) return false;
        if (testName != null ? !testName.equals(that.testName) : that.testName != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userQuestionResults != null ? !userQuestionResults.equals(that.userQuestionResults) : that.userQuestionResults != null)
            return false;
        if (userSecret != null ? !userSecret.equals(that.userSecret) : that.userSecret != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = (int) (userResultsId ^ (userResultsId >>> 32));
        result1 = 31 * result1 + (userName != null ? userName.hashCode() : 0);
        result1 = 31 * result1 + (userSecret != null ? userSecret.hashCode() : 0);
        result1 = 31 * result1 + (testName != null ? testName.hashCode() : 0);
        result1 = 31 * result1 + (dateOfPassed != null ? dateOfPassed.hashCode() : 0);
        result1 = 31 * result1 + (userQuestionResults != null ? userQuestionResults.hashCode() : 0);
        result1 = 31 * result1 + result;
        return result1;
    }
}
