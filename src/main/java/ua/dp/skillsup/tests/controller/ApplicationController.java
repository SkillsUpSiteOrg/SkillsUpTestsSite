package ua.dp.skillsup.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;
import ua.dp.skillsup.tests.service.ApplicationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 19.12.2014.
 */
@Controller
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    public ApplicationService getService() {
        return service;
    }

    public void setService(ApplicationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/getAllTestDescriptions", method = RequestMethod.GET)
    public @ResponseBody List<TestDescription> getAllTestDescriptions() {
        List<TestDescription> testDescriptions = new ArrayList<TestDescription>();
        testDescriptions.addAll(service.getAllTestDescriptions());
        return testDescriptions;
    }

    @RequestMapping(value = "/addNewTestDescription", method = RequestMethod.POST)
    public @ResponseBody String addNewTestDescription(
            @RequestParam(value = "testName", required = true) String testName,
            @RequestParam(value = "maxTimeToPassInMinutes", required = true) int maxTimeToPassInMinutes) {
        TestDescription testDescription = new TestDescription();
        testDescription.setTestName(testName);
        testDescription.setMaxTimeToPassInMinutes(maxTimeToPassInMinutes);
        service.addTestDescription(testDescription);
        return "{\"some\" : \"Successfully added new test "+testDescription.getTestName()+"\"}";
    }

    @RequestMapping(value = "/addNewQuestionAnswersByTestDescriptionId", method = RequestMethod.POST)
    public @ResponseBody String addNewQuestionAnswersByTestDescriptionId(
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "testDescriptionId", required = true) long testDescriptionId) {

        TestDescription testDescription = service.getTestDescription(testDescriptionId);

        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setQuestion(question);

        service.addQuestionAnswers(questionAnswers);
        return "{\"id\" : "+questionAnswers.getQuestionAnswersId()+"\"}";
    }

    @RequestMapping(value = "/addNewAnswerByQuestionAnswersId", method = RequestMethod.POST)
    public @ResponseBody String addNewAnswerByQuestionAnswersId(
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "testDescriptionId", required = true) int testDescriptionId) {
        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setQuestion(question);

        service.addQuestionAnswers(questionAnswers);
        return "{\"id\" : "+questionAnswers.getQuestionAnswersId()+"\"}";
    }

    @RequestMapping(value = "/getAllQuestionAnswersByTestDescriptionId/{id}", method = RequestMethod.GET)
    public @ResponseBody List<QuestionAnswers> getAllQuestionAnswersByTestDescriptionId(@PathVariable Long id) {
        List<QuestionAnswers> questionAnswers = service.getTestDescription(id).getQuestionAnswersRelations();

        return questionAnswers;
    }

    @RequestMapping(value = "/getAllTestDescriptionByQuestionAnswersId/{id}", method = RequestMethod.GET)
    public @ResponseBody List<TestDescription> getAllTestDescriptionByQuestionAnswersId(@PathVariable Long id) {
        List<TestDescription> testDescriptions = service.getQuestionAnswers(id).getTestDescriptionRelations();

        return testDescriptions;
    }

    @RequestMapping(value = "/getAllQuestionAnswers", method = RequestMethod.GET)
    public @ResponseBody List<QuestionAnswers> getAllQuestionAnswers() {
        List<QuestionAnswers> questionAnswerses = new ArrayList<QuestionAnswers>();
        questionAnswerses.addAll(service.getAllQuestionAnswers());
        return questionAnswerses;
    }
}
