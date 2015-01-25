package ua.dp.skillsup.tests.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long questionAnswersId;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWERS")
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Boolean> answers;

    @ManyToMany(mappedBy = "questionAnswersRelations",
            fetch = FetchType.EAGER,
            cascade={CascadeType.ALL})
    @Fetch(FetchMode.JOIN)
    @JsonBackReference
    private List<TestDescription> testDescriptionRelations;

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

    public List<TestDescription> getTestDescriptionRelations() {
        return testDescriptionRelations;
    }

    public void setTestDescriptionRelations(List<TestDescription> testDescriptionRelations) {
        this.testDescriptionRelations = testDescriptionRelations;
    }

    @Override
    public String toString() {
        return "QuestionAnswers{" +
                "questionAnswersId=" + questionAnswersId +
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

        if (questionAnswersId != that.questionAnswersId) return false;
        if (answers != null ? !answers.equals(that.answers) : that.answers != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        if (testDescriptionRelations != null ? !testDescriptionRelations.equals(that.testDescriptionRelations) : that.testDescriptionRelations != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (questionAnswersId ^ (questionAnswersId >>> 32));
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (testDescriptionRelations != null ? testDescriptionRelations.hashCode() : 0);
        return result;
    }

    public void addAnswers(String answer, boolean correct) {
        answers.put(answer, correct);
    }

    public void addTestDescriptionRelations(TestDescription testDescription) {
        testDescriptionRelations.add(testDescription);
    }
}
