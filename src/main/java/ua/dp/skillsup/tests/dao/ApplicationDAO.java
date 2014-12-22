package ua.dp.skillsup.tests.dao;

import ua.dp.skillsup.tests.dao.entity.TestDescription;

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
}
