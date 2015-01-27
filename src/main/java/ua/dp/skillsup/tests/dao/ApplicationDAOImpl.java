package ua.dp.skillsup.tests.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Class to manage test descriptions in database.
 */
@Repository("applicationDao")
@Transactional
public class ApplicationDAOImpl implements ApplicationDAO {

    @PersistenceContext/*(type = PersistenceContextType.EXTENDED)*/
    public EntityManager em;

    @Override
    public TestDescription addTestDescription(TestDescription testDescription){
        TestDescription existingTestDescription = em.find(TestDescription.class, testDescription.getTestDescriptionId());
        if (existingTestDescription == null){
            em.persist(testDescription);
            return testDescription;
        }
        else {
            return em.merge(testDescription);
        }
    }

    @Override
    public void deleteTestDescription(TestDescription testDescription){
        TestDescription test = em.find(TestDescription.class, testDescription.getTestDescriptionId());
        em.remove(test);
    }

    @Override
    public TestDescription getTestDescription(long id){
        return em.find(TestDescription.class, id);
    }

    @Override
    public void updateTestDescription(long id, TestDescription testDescription){
        TestDescription newTest = em.find(TestDescription.class, id);
        if(newTest != null){
            if (!em.find(TestDescription.class, testDescription.getTestDescriptionId()).equals(newTest)){
                newTest.setTestName(testDescription.getTestName());
                newTest.setMaxTimeToPassInMinutes(testDescription.getMaxTimeToPassInMinutes());
                newTest.setQuestionAnswersRelations(testDescription.getQuestionAnswersRelations());
                em.merge(newTest);
            }
            else {
                System.out.println("There is no such entity in data base. Check the class TestDescription.");
            }
        }
        else{
            System.out.println("There is no such entity in data base. Check the id.");
        }
    }

    @Override
    public List<TestDescription> getAllTestDescriptions(){
        TypedQuery<TestDescription> namedQuery = em.createQuery("select c from TestDescription c", TestDescription.class);
        return namedQuery.getResultList();
    }

    @Override
    public QuestionAnswers addQuestionAnswers(QuestionAnswers questionAnswers) {
        QuestionAnswers existingQuestionAnswers = em.find(QuestionAnswers.class, questionAnswers.getQuestionAnswersId());
        if (existingQuestionAnswers == null){
            em.persist(questionAnswers);
            return questionAnswers;
        }
        else {
            return em.merge(questionAnswers);
        }
    }

    @Override
    public void deleteQuestionAnswers(QuestionAnswers questionAnswers) {
        questionAnswers = this.em.merge(questionAnswers);
        this.em.remove(questionAnswers);
        /*QuestionAnswers questionAnswers_ = this.em.find(QuestionAnswers.class, questionAnswers.getQuestionAnswersId());
        this.em.remove(questionAnswers_);*/
    }

    @Override
    public QuestionAnswers getQuestionAnswers(long id) {
        return em.find(QuestionAnswers.class, id);
    }

    @Override
    public void updateQuestionAnswers(long id, QuestionAnswers questionAnswers) {
        QuestionAnswers newQuestionAnswers =em.find(QuestionAnswers.class, id);
        if( newQuestionAnswers!= null){
            if (!em.find(QuestionAnswers.class, questionAnswers.getQuestionAnswersId()).equals(newQuestionAnswers)){
                newQuestionAnswers.setQuestion(questionAnswers.getQuestion());
                newQuestionAnswers.setAnswers(questionAnswers.getAnswers());
                newQuestionAnswers.setTestDescriptionRelations(questionAnswers.getTestDescriptionRelations());
                em.merge(newQuestionAnswers);
            }
            else {
                System.out.println("There is no such entity in data base. Check the class QuestionAnswers.");
            }
        }
        else{
            System.out.println("There is no such entity in data base. Check the id.");
        }
    }

    @Override
    public List<QuestionAnswers> getAllQuestionAnswers() {
        TypedQuery<QuestionAnswers> namedQuery = em.createQuery("select c from QuestionAnswers c", QuestionAnswers.class);
        return namedQuery.getResultList();
    }

    @Override
    public List<QuestionAnswers> getAllQuestionAnswersOfTestDescription(TestDescription testDescription) {
        String queryString = "SELECT td.questionAnswersRelations FROM TestDescription td " +
                "WHERE td.testDescriptionId = :idTestDescription" ;
        TypedQuery<QuestionAnswers> namedQuery = em.createQuery(queryString, QuestionAnswers.class);
        namedQuery.setParameter("idTestDescription", testDescription.getTestDescriptionId());
        return namedQuery.getResultList();
    }

    @Override
    public List<TestDescription> getAllTestDescriptionOfQuestionAnswers(QuestionAnswers questionAnswers) {
        String queryString = "SELECT qa.testDescriptionRelations FROM QuestionAnswers qa " +
                "WHERE qa.questionAnswersId = :questionAnswersId" ;
        TypedQuery<TestDescription> namedQuery = em.createQuery(queryString, TestDescription.class);
        namedQuery.setParameter("questionAnswersId", questionAnswers.getQuestionAnswersId());
        return namedQuery.getResultList();
    }

    @Override
    public QuestionAnswers getQuestionAnswers(String question) {
        String queryString = "SELECT qa FROM QuestionAnswers qa " +
                "WHERE qa.question = :question" ;
        TypedQuery<QuestionAnswers> namedQuery = em.createQuery(queryString, QuestionAnswers.class);
        namedQuery.setParameter("question", question);
        return namedQuery.getSingleResult();
    }

    @Override
    public TestDescription getTestDescription(String testName) {
        String queryString = "SELECT td FROM TestDescription td " +
                "WHERE td.testName = :testName" ;
        TypedQuery<TestDescription> namedQuery = em.createQuery(queryString, TestDescription.class);
        namedQuery.setParameter("testName", testName);
        return namedQuery.getSingleResult();
    }
}
