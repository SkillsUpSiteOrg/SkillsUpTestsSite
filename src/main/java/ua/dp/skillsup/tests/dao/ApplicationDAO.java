package ua.dp.skillsup.tests.dao;

import ua.dp.skillsup.tests.dao.entity.TestDescription;

import java.util.List;

/**
 * Created by Daniel on 16.12.2014.
 */
public interface ApplicationDAO {
    void addTestDescription(TestDescription testDescription);
    void deleteTestDescription(long id);
    TestDescription getTestDescription(long id);
    void updateTestDescription(TestDescription testDescription);
    List<TestDescription> getAllTestDescriptions();
}
