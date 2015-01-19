package ua.dp.skillsup.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import java.util.List;

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
        //second line
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

    }

    @Override
    public QuestionAnswers getQuestionAnswers(long id) {
        return dao.getQuestionAnswers(id);
    }

    @Override
    public void updateQuestionAnswers(long id, QuestionAnswers questionAnswers) {

    }

    @Override
    public List<QuestionAnswers> getAllQuestionAnswers() {
        return dao.getAllQuestionAnswers();
    }

    @Override
    public List<QuestionAnswers> getAllQuestionAnswersForTestDescription(TestDescription testDescription) {
        return dao.getAllQuestionAnswersForTestDescription(testDescription);
    }

    @Override
    public List<TestDescription> getAllTestDescriptionForQuestionAnswers(QuestionAnswers questionAnswers) {
        return dao.getAllTestDescriptionForQuestionAnswers(questionAnswers);
    }
}
