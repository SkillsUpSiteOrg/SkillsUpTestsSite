package ua.dp.skillsup.tests.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long testDescriptionId;

    @Column(name = "TEST_NAME")
    private String testName;

    @Column(name = "DATE")
    private Date dateOfCreation;

    @Column(name = "TIME_IN_MINUTES")
    private int maxTimeToPassInMinutes;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade={CascadeType.ALL})
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<QuestionAnswers> questionAnswersRelations;

    public List<QuestionAnswers> getQuestionAnswersRelations() {
        return questionAnswersRelations;
    }

    public void setQuestionAnswersRelations(List<QuestionAnswers> questionAnswersRelations) {
        if (this.questionAnswersRelations != questionAnswersRelations){
            if (!this.getQuestionAnswersRelations().isEmpty()){
                this.getQuestionAnswersRelations().clear();
            }
            for (QuestionAnswers questionAnswers:questionAnswersRelations){
                questionAnswers.addTestDescriptionRelation(this);
            }
        }
    }

    public void addQuestionAnswersRelation(QuestionAnswers questionAnswers) {
        if (!this.getQuestionAnswersRelations().contains(questionAnswers)){
            this.questionAnswersRelations.add(questionAnswers);
            if (!questionAnswers.getTestDescriptionRelations().contains(this)){
                questionAnswers.internalAddTestDescriptionRelation(this);
            }
        }
    }

    public void removeQuestionAnswersRelation(QuestionAnswers questionAnswers) {
        if (this.getQuestionAnswersRelations().contains(questionAnswers)){
            if (questionAnswers.getTestDescriptionRelations().contains(this)){
                questionAnswers.internalRemoveTestDescriptionRelation(this);
            }
            this.questionAnswersRelations.remove(questionAnswers);
        }
    }

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
                /*", Used with questionAnswers=" + questionAnswersRelations +*/
                '}';
    }

    @Override
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
    }
}
