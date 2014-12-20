package ua.dp.skillsup.tests.dao;

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

    @PersistenceContext(unitName = "item-manager-pu")
    public EntityManager em;

    @Override
    @Transactional
    public TestDescription addTestDescription(TestDescription testDescription){
        return em.merge(testDescription);
    }

    @Override
    @Transactional
    public void deleteTestDescription(TestDescription testDescription){
        em.getTransaction().begin();
        em.remove(testDescription);
        em.getTransaction().commit();
    }

    @Override
    @Transactional(readOnly = true)
    public TestDescription getTestDescription(long id){
        return em.find(TestDescription.class, id);
    }

    @Override
    @Transactional
    public void updateTestDescription(TestDescription testDescription){
        em.getTransaction().begin();
        em.merge(testDescription);
        em.getTransaction().commit();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestDescription> getAllTestDescriptions(){
        TypedQuery<TestDescription> namedQuery = em.createQuery("select c from TestDescription c", TestDescription.class);
        return namedQuery.getResultList();
    }

}
