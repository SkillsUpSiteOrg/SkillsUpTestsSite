package ua.dp.skillsup.tests.service;

import ua.dp.skillsup.tests.dao.ApplicationDAO;

import java.util.Map;

/**
 * Created by Daniel on 19.12.2014.
 */
public interface ApplicationService extends ApplicationDAO {
    int addNewUserResults(String userName, String userSecret, String testName, Map<String,Map<String, Boolean>> mapOfUserQuestionsAnswers);
}
