package ua.dp.skillsup.tests.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Daniel on 16.12.2014.
 */
@Repository
@Transactional
public class ApplicationDAOImpl implements ApplicationDAO {

    public EntityManager em = Persistence.createEntityManagerFactory("item-manager-pu").createEntityManager();

    @Override
    @Transactional
    public void addTestDescription(TestDescription testDescription){
        em.getTransaction().begin();
        em.merge(testDescription);
        em.getTransaction().commit();
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
        TypedQuery<TestDescription> namedQuery = em.createNamedQuery("TestDescription.getAll", TestDescription.class);
        return namedQuery.getResultList();
    }

}
