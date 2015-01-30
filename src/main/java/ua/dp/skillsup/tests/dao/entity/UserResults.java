package ua.dp.skillsup.tests.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SERG on 29.01.15.
 */
@Proxy(lazy=false)
@Entity
@Table(name = "USER_RESULTS")
public class UserResults {

    public UserResults(){
        this.dateOfPassed = new Date();
        this.correctQuestionAnswerses = new ArrayList<QuestionAnswers>();
        this.userQuestionAnswerses = new ArrayList<QuestionAnswers>();
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

/*
    @Column
    private int percentOfTimeToPass;
*/

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "ETALON_ANSWERS_OF_TEST")
    private List<QuestionAnswers> correctQuestionAnswerses;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "USER_ANSWERS_OF_TEST")
    private List<QuestionAnswers> userQuestionAnswerses;

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

    public List<QuestionAnswers> getCorrectQuestionAnswerses() {
        return correctQuestionAnswerses;
    }

    public void setCorrectQuestionAnswerses(List<QuestionAnswers> correctQuestionAnswerses) {
        this.correctQuestionAnswerses = correctQuestionAnswerses;
    }

    public List<QuestionAnswers> getUserQuestionAnswerses() {
        return userQuestionAnswerses;
    }

    public void setUserQuestionAnswerses(List<QuestionAnswers> userQuestionAnswerses) {
        this.userQuestionAnswerses = userQuestionAnswerses;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Map<String, Integer> getUserQuestionResultss() {
        return userQuestionResults;
    }

    public void setUserQuestionResultss(Map<String, Integer> userQuestionResultss) {
        this.userQuestionResults = userQuestionResultss;
    }

    @Override
    public String toString() {
        return "UserResults{" +
                "userResultsId=" + userResultsId +
                ", userName='" + userName + '\'' +
                ", userSecret='" + userSecret + '\'' +
                ", testName='" + testName + '\'' +
                ", dateOfPassed=" + dateOfPassed +
                ", correctQuestionAnswerses=" + correctQuestionAnswerses +
                ", userQuestionAnswerses=" + userQuestionAnswerses +
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
        if (correctQuestionAnswerses != null ? !correctQuestionAnswerses.equals(that.correctQuestionAnswerses) : that.correctQuestionAnswerses != null)
            return false;
        if (dateOfPassed != null ? !dateOfPassed.equals(that.dateOfPassed) : that.dateOfPassed != null) return false;
        if (testName != null ? !testName.equals(that.testName) : that.testName != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userQuestionAnswerses != null ? !userQuestionAnswerses.equals(that.userQuestionAnswerses) : that.userQuestionAnswerses != null)
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
        result1 = 31 * result1 + (correctQuestionAnswerses != null ? correctQuestionAnswerses.hashCode() : 0);
        result1 = 31 * result1 + (userQuestionAnswerses != null ? userQuestionAnswerses.hashCode() : 0);
        result1 = 31 * result1 + result;
        return result1;
    }
}
