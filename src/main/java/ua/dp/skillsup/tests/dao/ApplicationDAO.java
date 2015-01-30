package ua.dp.skillsup.tests.dao;

import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;
import ua.dp.skillsup.tests.dao.entity.UserResults;

import java.util.List;

/**
 * Created by Daniel on 16.12.2014.
 */
public interface ApplicationDAO {
    TestDescription addTestDescription(TestDescription testDescription);
    void deleteTestDescription(TestDescription testDescription);
    TestDescription getTestDescription(long id);
    void updateTestDescription(long id, TestDescription testDescription);
    List<TestDescription> getAllTestDescriptions();

    QuestionAnswers addQuestionAnswers(QuestionAnswers questionAnswers);
    void deleteQuestionAnswers(QuestionAnswers questionAnswers);
    QuestionAnswers getQuestionAnswers(long id);
    TestDescription getTestDescription(String testName);
    QuestionAnswers getQuestionAnswers(String question);
    void updateQuestionAnswers(long id, QuestionAnswers questionAnswers);
    List<QuestionAnswers> getAllQuestionAnswers();

    public List<QuestionAnswers> getAllQuestionAnswersOfTestDescription(TestDescription testDescription);
    public List<TestDescription> getAllTestDescriptionOfQuestionAnswers(QuestionAnswers questionAnswers);

    UserResults addUserResults(UserResults userResults);
    UserResults getUserResults(long id);
    List<UserResults> getAllResultsOfUser(String userName, String userSecret);
}
