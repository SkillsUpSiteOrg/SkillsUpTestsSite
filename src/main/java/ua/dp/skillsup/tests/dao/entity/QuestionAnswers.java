package ua.dp.skillsup.tests.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Daniel on 26.12.2014.
 */
/*@Proxy(lazy=false)*/
@Entity
@Table(name = "QUESTION_ANSWERS")
public class QuestionAnswers {

    public QuestionAnswers(){
        this.answers = new HashMap<String, Boolean>();
        this.testDescriptionRelations = new ArrayList<TestDescription>();
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long questionAnswersId;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWERS")
    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Boolean> answers;

    @JsonInclude
    public Set<String> getAnswersText() {
        return answers.keySet();
    }

    @ManyToMany(mappedBy = "questionAnswersRelations",
            fetch = FetchType.EAGER,
            cascade={CascadeType.ALL})
    @Fetch(FetchMode.JOIN)
    @JsonBackReference
    private List<TestDescription> testDescriptionRelations;

    public List<TestDescription> getTestDescriptionRelations() {
        return testDescriptionRelations;
    }

    public void setTestDescriptionRelations(List<TestDescription> testDescriptionRelations) {
        if (this.testDescriptionRelations != testDescriptionRelations){
            if(testDescriptionRelations != null){
                if (!this.getTestDescriptionRelations().isEmpty()){
                    this.getTestDescriptionRelations().clear();
                }
                for (TestDescription testDescriptionRelation : testDescriptionRelations) {
                    testDescriptionRelation.addQuestionAnswersRelation(this);
                }
            }
            else {
                this.testDescriptionRelations.clear();
            }

        }
        else {
            System.out.println("relations for this questionAnswers equals gets "+this.getQuestionAnswersId());
        }
    }

    public void addTestDescriptionRelation(TestDescription testDescription) {
        testDescription.addQuestionAnswersRelation(this);
    }

    public void removeTestDescriptionRelation(TestDescription testDescription) {
        testDescription.removeQuestionAnswersRelation(this);
    }

    public void internalAddTestDescriptionRelation(TestDescription testDescription) {
        this.testDescriptionRelations.add(testDescription);
    }

    public void internalRemoveTestDescriptionRelation(TestDescription testDescription) {
        this.testDescriptionRelations.remove(testDescription);
    }

    public long getQuestionAnswersId() {
        return questionAnswersId;
    }

    public void setQuestionAnswersId(long questionAnswerId) {
        this.questionAnswersId = questionAnswerId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Boolean> answer) {
        this.answers = answer;
    }

    public void addAnswers(String answer, boolean correct) {
        answers.put(answer, correct);
    }

    @Override
    public String toString() {
        return "QuestionAnswers{" +
                "Id=" + questionAnswersId +
                ", question='" + question + '\'' +
                ", answers='" + answers + '\'' +
                ", used in tests=" + testDescriptionRelations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionAnswers that = (QuestionAnswers) o;

        return new EqualsBuilder()
                .append(questionAnswersId, that.questionAnswersId)
                .append(answers, that.answers)
                .append(question, that.question)
                .append(testDescriptionRelations, that.testDescriptionRelations)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(questionAnswersId).
                append(question).
                append(answers).
                append(testDescriptionRelations).
                toHashCode();
    }
}
