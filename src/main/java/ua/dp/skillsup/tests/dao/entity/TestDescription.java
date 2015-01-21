package ua.dp.skillsup.tests.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Daniel on 16.12.2014.
 */
/*@Proxy(lazy=false)*/
@Entity
@Table(name = "TEST_DESCRIPTION")
public class TestDescription {

    public TestDescription(){
        this.dateOfCreation = new Date();
        this.questionAnswersRelations = new ArrayList<QuestionAnswers>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long testDescriptionId;

    @Column(name = "TEST_NAME")
    private String testName;

    @Column(name = "DATE")
    private Date dateOfCreation;

    @Column(name = "TIME_IN_MINUTES")
    private int maxTimeToPassInMinutes;

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    @ManyToMany(fetch = FetchType.EAGER,
            cascade={CascadeType.ALL})
    @Fetch(FetchMode.JOIN)
    @JsonBackReference
    private List<QuestionAnswers> questionAnswersRelations;

    public List<QuestionAnswers> getQuestionAnswersRelations() {
        return questionAnswersRelations;
    }

    public void setQuestionAnswersRelations(List<QuestionAnswers> questionAnswersRelations) {
        this.questionAnswersRelations = questionAnswersRelations;
        for (QuestionAnswers questionAnswersRelation : questionAnswersRelations) {
            questionAnswersRelation.addTestDescriptionRelations(this);
        }
    }

    public void addQuestionAnswersRelations(QuestionAnswers questionAnswers) {
        questionAnswersRelations.add(questionAnswers);
        questionAnswers.addTestDescriptionRelations(this);
    }
    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public long getTestDescriptionId() {
        return testDescriptionId;
    }

    public void setTestDescriptionId(long testDescriptionId) {
        this.testDescriptionId = testDescriptionId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(DateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation.toDate();
    }

    public int getMaxTimeToPassInMinutes() {
        return maxTimeToPassInMinutes;
    }

    public void setMaxTimeToPassInMinutes(int maxTimeToPassInMinutes) {
        this.maxTimeToPassInMinutes = maxTimeToPassInMinutes;
    }

    @Override
    public String toString() {
        return "TestDescription{" +
                "Id=" + testDescriptionId +
                ", Name='" + testName + '\'' +
                ", Date of creation=" + dateOfCreation +
                ", Max time to pass=" + maxTimeToPassInMinutes +
                '}';
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDescription that = (TestDescription) o;

        return new EqualsBuilder()
                .append(maxTimeToPassInMinutes, that.maxTimeToPassInMinutes)
                .append(testDescriptionId, that.testDescriptionId)
                .append(dateOfCreation, that.dateOfCreation)
                .append(testName, that.testName)
                .append(questionAnswersRelations, that.questionAnswersRelations)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(testDescriptionId).
                append(testName).
                append(dateOfCreation).
                append(maxTimeToPassInMinutes).
                append(questionAnswersRelations).
                toHashCode();
    }*/
}
