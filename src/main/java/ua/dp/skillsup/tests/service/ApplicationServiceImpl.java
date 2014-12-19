package ua.dp.skillsup.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.ApplicationDAOImpl;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import java.util.List;

/**
 * Created by Daniel on 19.12.2014.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDAOImpl dao;

    public ApplicationDAOImpl getDao() {
        return dao;
    }

    public void setDao(ApplicationDAOImpl dao) {
        this.dao = dao;
    }

    @Override
    public void addTestDescription(TestDescription testDescription) {
        dao.addTestDescription(testDescription);
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
    public void updateTestDescription(TestDescription testDescription) {
        dao.updateTestDescription(testDescription);
    }

    @Override
    public List<TestDescription> getAllTestDescriptions() {
        return dao.getAllTestDescriptions();
    }
}
