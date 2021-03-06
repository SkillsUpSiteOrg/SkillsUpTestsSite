package ua.dp.skillsup.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;
import ua.dp.skillsup.tests.dao.entity.UserResults;

import java.util.*;

/**
 * Created by Daniel on 19.12.2014.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    @Qualifier("applicationDao")
    private ApplicationDAO dao;

    public ApplicationDAO getDao() {
        return dao;
    }

    public void setDao(ApplicationDAO dao) {
        this.dao = dao;
    }

    @Override
    public TestDescription addTestDescription(TestDescription testDescription) {
        return dao.addTestDescription(testDescription);
    }

    @Override
    public void deleteTestDescription(TestDescription testDescription) {
        dao.deleteTestDescription(testDescription);
    }

    @Override
    public TestDescription getTestDescription(long id) {
        return dao.getTestDescription(id);
    }

    @Override
    public void updateTestDescription(long id, TestDescription testDescription) {
        dao.updateTestDescription(id, testDescription);
    }

    @Override
    public List<TestDescription> getAllTestDescriptions() {
        return dao.getAllTestDescriptions();
    }

    @Override
    public QuestionAnswers addQuestionAnswers(QuestionAnswers questionAnswers) {
        return dao.addQuestionAnswers(questionAnswers);
    }

    @Override
    public void deleteQuestionAnswers(QuestionAnswers questionAnswers) {
        dao.deleteQuestionAnswers(questionAnswers);
    }

    @Override
    public QuestionAnswers getQuestionAnswers(long id) {
        return dao.getQuestionAnswers(id);
    }

    @Override
    public TestDescription getTestDescriptionByName(String testName) {
        return dao.getTestDescriptionByName(testName);
    }

    @Override
    public QuestionAnswers getQuestionAnswersByQuestion(String question) {
        return dao.getQuestionAnswersByQuestion(question);
    }

    @Override
    public void updateQuestionAnswers(long id, QuestionAnswers questionAnswers) {
        dao.updateQuestionAnswers(id, questionAnswers);
    }

    @Override
    public List<QuestionAnswers> getAllQuestionAnswers() {
        return dao.getAllQuestionAnswers();
    }

    @Override
    public List<QuestionAnswers> getAllQuestionAnswersOfTestDescription(TestDescription testDescription) {
        return dao.getAllQuestionAnswersOfTestDescription(testDescription);
    }

    @Override
    public List<TestDescription> getAllTestDescriptionOfQuestionAnswers(QuestionAnswers questionAnswers) {
        return dao.getAllTestDescriptionOfQuestionAnswers(questionAnswers);
    }

    @Override
    public UserResults addUserResults(UserResults userResults) {
        return dao.addUserResults(userResults);
    }

    @Override
    public UserResults getUserResults(long id) {
        return dao.getUserResults(id);
    }

    @Override
    public List<UserResults> getAllResultsOfUser(String userName, String userSecret) {
        return dao.getAllResultsOfUser(userName, userSecret);
    }

    @Override
    public int addNewUserResults(String userName, String userSecret, String testName, Map<String, Map<String, Boolean>> mapOfUserQuestionsAnswers) {
        int averageTestPassPercent = 0;
        List<QuestionAnswers> allQuestionAnswersForUserAnswers = new ArrayList<QuestionAnswers>();
        UserResults userResults = new UserResults();
        userResults.setUserName(userName);
        userResults.setUserSecret(userSecret);

        TestDescription existingTestDescription = dao.getTestDescriptionByName(testName);

        if (existingTestDescription != null){
            userResults.setTestName(testName);
            if (mapOfUserQuestionsAnswers != null){
                Map<String,Integer> userQuestionResults = new HashMap<String, Integer>();
                List<QuestionAnswers> allCorrectQuestionAnswers = existingTestDescription.getQuestionAnswersRelations();
                if (allCorrectQuestionAnswers != null) {
                    for (String question : mapOfUserQuestionsAnswers.keySet()){
                        QuestionAnswers currentQuestionAnswers = dao.getQuestionAnswersByQuestion(question);
                        if (currentQuestionAnswers != null) {
                            Map<String, Boolean> userAnswers = mapOfUserQuestionsAnswers.get(question);
                            userQuestionResults.put(question, compareCorrectAndUserAnswers(currentQuestionAnswers.getAnswers(), userAnswers));
                        }
                        else {
                            System.out.println("There is no question in DB with such text: "+question);
                        }
                    }
                    int sum = 0;
                    for (int x : userQuestionResults.values()){sum += x;}
                    averageTestPassPercent = sum/userQuestionResults.values().size();
                    userResults.setResult(averageTestPassPercent);
                    userResults.setUserQuestionResults(userQuestionResults);
                    System.out.println("!!!: "+dao.addUserResults(userResults));
                }
                else {
                    System.out.println("Test " + testName + " doesn't have any questions");
                    averageTestPassPercent = -1;
                }
            }
            else {
                System.out.println("Answers are missing");
                averageTestPassPercent = -1;
            }
        }
        else {
            System.out.println("Test: " + testName + " is absent!!!");
            averageTestPassPercent = -1;
        }
        return averageTestPassPercent;
    }

    private int compareCorrectAndUserAnswers(Map<String, Boolean> correctAnswers, Map<String, Boolean> userAnswers) {
        double countCorrectAnswers = 0;
        double countUncheckedAnswers = 0;
        double countCheckedCorrectAnswers = 0;
        double countCheckedWrongAnswers = 0;
        double countNotCheckedWrongAnswers = 0;
        double countNotCheckedCorrectAnswers = 0;

        for (String currentCorrectAnswer : correctAnswers.keySet()) {
            if (userAnswers.containsKey(currentCorrectAnswer)) {
                Boolean valueCorrectAnswer = correctAnswers.get(currentCorrectAnswer);
                Boolean valueUserAnswer = userAnswers.get(currentCorrectAnswer);
                if (valueCorrectAnswer){
                    countCorrectAnswers++;
                    if (valueUserAnswer){
                        countCheckedCorrectAnswers++;
                    }
                    else {
                        countNotCheckedCorrectAnswers++;
                    }
                }
                else {
                    countUncheckedAnswers++;
                    if (valueUserAnswer){
                        countCheckedWrongAnswers++;
                    }
                    else {
                        countNotCheckedWrongAnswers++;
                    }
                }
            } else {
                System.out.println("User Answers has not current Test Answer: "+currentCorrectAnswer);
            }
        }

        if ((countCheckedCorrectAnswers>0)&&(countCheckedWrongAnswers==0)){
            return (int)((countCheckedCorrectAnswers/countCorrectAnswers)*100);
        }
        else {
            return 0;
        }
    }


}
