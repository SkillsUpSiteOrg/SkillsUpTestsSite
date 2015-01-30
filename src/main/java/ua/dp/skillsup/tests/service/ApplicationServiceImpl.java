package ua.dp.skillsup.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;
import ua.dp.skillsup.tests.dao.entity.UserResults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public TestDescription getTestDescription(String testName) {
        return dao.getTestDescription(testName);
    }

    @Override
    public QuestionAnswers getQuestionAnswers(String question) {
        return dao.getQuestionAnswers(question);
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
        TestDescription existingTestDescription = dao.getTestDescription(testName);
        if (existingTestDescription != null){
            userResults.setTestName(testName);
            if (mapOfUserQuestionsAnswers != null){
                int[] arrayQuestionsPassPercent = new int[mapOfUserQuestionsAnswers.keySet().size()];
                List<QuestionAnswers> allCorrectQuestionAnswers = dao.getAllQuestionAnswersOfTestDescription(existingTestDescription);
                if (allCorrectQuestionAnswers != null) {
                    int i=0;
                    for (String question : mapOfUserQuestionsAnswers.keySet()){
                        QuestionAnswers currentQuestionAnswers = dao.getQuestionAnswers(question);
                        if (currentQuestionAnswers != null) {
                            if (allCorrectQuestionAnswers.contains(currentQuestionAnswers)){
                                Map<String, Boolean> userAnswers = mapOfUserQuestionsAnswers.get(question);

                                QuestionAnswers questionAnswersForUserAnswers = new QuestionAnswers();
                                questionAnswersForUserAnswers.setQuestion(question);
                                questionAnswersForUserAnswers.setAnswers(userAnswers);
                                allQuestionAnswersForUserAnswers.add(questionAnswersForUserAnswers);
                                arrayQuestionsPassPercent[i] = compareCorrectAndUserAnswers(currentQuestionAnswers.getAnswers(), userAnswers);
                                i++;
                            }
                            else {
                                System.out.println("Question - "+question+" - not belong questions of Test: "+testName);
                            }
                        }
                        else {
                            System.out.println("In DB no one question with text: "+question);
                        }
                    }
                    int summa = 0;
                    for (int x : arrayQuestionsPassPercent){summa += x;}
                    averageTestPassPercent = summa/arrayQuestionsPassPercent.length*100;

                    userResults.setCorrectQuestionAnswerses(allCorrectQuestionAnswers);
                    userResults.setUserQuestionAnswerses(allQuestionAnswersForUserAnswers);
                    dao.addUserResults(userResults);
                }
                else {
                    System.out.println("Test " + testName + " have not any questions");
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
        int countCorrectAnswers = 0;
        int countUncheckedAnswers = 0;
        int countCheckedCorrectAnswers = 0;
        int countCheckedWrongAnswers = 0;
        int countNotCheckedWrongAnswers = 0;
        int countNotCheckedCorrectAnswers = 0;

        for (String currentCorrectAnswer : correctAnswers.keySet()) {
            if (userAnswers.containsKey(currentCorrectAnswer)) {
                Boolean valueCorrectAnswer = correctAnswers.get(currentCorrectAnswer);
                Boolean valueUserAnswer = userAnswers.get(currentCorrectAnswer);
                if (valueCorrectAnswer.equals(true)){
                    countCorrectAnswers++;
                    if (valueUserAnswer.equals(true)){
                        countCheckedCorrectAnswers++;
                    }
                    else {
                        countNotCheckedCorrectAnswers++;
                    }
                }
                else {
                    countUncheckedAnswers++;
                    if (valueUserAnswer.equals(true)){
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

        if ((countNotCheckedWrongAnswers/countUncheckedAnswers > 0.5) && (countCheckedCorrectAnswers/countCorrectAnswers >= 0.5)){
            return  (countNotCheckedWrongAnswers+countCheckedCorrectAnswers)/(countUncheckedAnswers+countCorrectAnswers)*100;
        }
        else {
            return 0;
        }
    }


}
