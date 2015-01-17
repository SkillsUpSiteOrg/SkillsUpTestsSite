package ua.dp.skillsup.tests.dao.entity;

import com.google.common.collect.Maps;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 26.12.2014.
 */
@Entity
@Table(name = "QUESTION_ANSWER")
public class QuestionAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long questionAnswerId;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWER")
    @ElementCollection
    private Map<String, Boolean> answers;

    @ManyToMany(mappedBy = "questionAnswers", fetch = FetchType.EAGER, cascade={CascadeType.MERGE})
    @Fetch(FetchMode.JOIN)
    private List<TestDescription> testDescriptions;

    public long getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(long questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
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

    public List<TestDescription> getTestDescriptions() {
        return testDescriptions;
    }

    public void setTestDescriptions(List<TestDescription> testDescriptions) {
        this.testDescriptions = testDescriptions;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "questionAnswerId=" + questionAnswerId +
                ", question='" + question + '\'' +
                ", answer='" + answers + '\'' +
                ", used in tests=" + testDescriptions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionAnswers that = (QuestionAnswers) o;

        if (questionAnswerId != that.questionAnswerId) return false;
        if (answers != null ? !answers.equals(that.answers) : that.answers != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        if (testDescriptions != null ? !testDescriptions.equals(that.testDescriptions) : that.testDescriptions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (questionAnswerId ^ (questionAnswerId >>> 32));
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (testDescriptions != null ? testDescriptions.hashCode() : 0);
        return result;
    }

    public void addAnswers(String answer, boolean correct) {
        if(answers == null){
            answers = Maps.newHashMap();
        }
        answers.put(answer, correct);
    }

    public void addTestDescriptions(TestDescription testDescription) {
        testDescriptions.add(testDescription);
    }
}
