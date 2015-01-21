package ua.dp.skillsup.tests.dao;

import org.joda.time.DateTime;
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

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public EntityManager em;

    @Override
    public TestDescription addTestDescription(TestDescription testDescription){
        return em.merge(testDescription);
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
        TestDescription newTest;
        if(em.find(TestDescription.class, id) != null){
            newTest = em.find(TestDescription.class, testDescription.getTestDescriptionId());
            newTest.setTestName(testDescription.getTestName());
            newTest.setMaxTimeToPassInMinutes(testDescription.getMaxTimeToPassInMinutes());
            em.merge(newTest);
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
        return em.merge(questionAnswers);
    }

    @Override
    public void deleteQuestionAnswers(QuestionAnswers questionAnswers) {
        QuestionAnswers questionAnswers_ = em.find(QuestionAnswers.class, questionAnswers.getQuestionAnswersId());
        em.remove(questionAnswers_);
    }

    @Override
    public QuestionAnswers getQuestionAnswers(long id) {
        return em.find(QuestionAnswers.class, id);
    }

    @Override
    public void updateQuestionAnswers(long id, QuestionAnswers questionAnswers) {
        QuestionAnswers newQuestionAnswers;
        if(em.find(QuestionAnswers.class, id) != null){
            newQuestionAnswers = em.find(QuestionAnswers.class, questionAnswers.getQuestionAnswersId());
            newQuestionAnswers.setQuestion(questionAnswers.getQuestion());
            newQuestionAnswers.setAnswers(questionAnswers.getAnswers());
            newQuestionAnswers.setTestDescriptionRelations(questionAnswers.getTestDescriptionRelations());
            em.merge(newQuestionAnswers);
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
    public List<QuestionAnswers> getAllQuestionAnswersForTestDescription(TestDescription testDescription) {
        String queryString = "SELECT DISTINCT qa FROM QuestionAnswers qa " +
                "INNER JOIN qa.testDescriptionRelations tdr"+
                "WHERE tdr.testDescriptionId = :testDescriptionId";
        TypedQuery<QuestionAnswers> namedQuery = em.createQuery(queryString, QuestionAnswers.class);
        namedQuery.setParameter("testDescriptionId", testDescription.getTestDescriptionId());
        return namedQuery.getResultList();
    }

    @Override
    public List<TestDescription> getAllTestDescriptionForQuestionAnswers(QuestionAnswers questionAnswers) {
        String queryString = "SELECT DISTINCT td FROM TestDescription td " +
                "INNER JOIN td.questionAnswersRelations qar"+
                "WHERE qa.questionAnswersId = :questionAnswersId";
        TypedQuery<TestDescription> namedQuery = em.createQuery(queryString, TestDescription.class);
        namedQuery.setParameter("questionAnswersId", questionAnswers.getQuestionAnswersId());
        return namedQuery.getResultList();
    }
}
