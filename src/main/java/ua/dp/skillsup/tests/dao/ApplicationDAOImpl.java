package ua.dp.skillsup.tests.dao;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Class to manage test descriptions in database.
 */
@Repository("applicationDao")
@Transactional
public class ApplicationDAOImpl implements ApplicationDAO {

    @PersistenceContext
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

}
