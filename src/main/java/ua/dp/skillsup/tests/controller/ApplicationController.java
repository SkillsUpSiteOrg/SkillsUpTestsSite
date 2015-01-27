package ua.dp.skillsup.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;
import ua.dp.skillsup.tests.service.ApplicationService;

import java.util.List;
import java.util.Map;

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

    /**/
    @RequestMapping(value = "/getAllTestDescriptions", method = RequestMethod.GET)
    public @ResponseBody List<TestDescription> getAllTestDescriptions() {
        return service.getAllTestDescriptions();
    }

    @RequestMapping(value = "/getAllQuestionAnswers", method = RequestMethod.GET)
    public @ResponseBody List<QuestionAnswers> getAllQuestionAnswers(){
        return service.getAllQuestionAnswers();
    }

    @RequestMapping(value = "/addNewQuestionToTest", method = RequestMethod.POST)
    public @ResponseBody String addNewQuestionToTest(
            @RequestParam(value = "testName", required = true) String testName,
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "answers", required = true) Map<String, Boolean> answers) {
        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setQuestion(question);
        questionAnswers.setAnswers(answers);
        questionAnswers.addTestDescriptionRelation(service.getTestDescription(testName));
        questionAnswers = service.addQuestionAnswers(questionAnswers);
        return "{\"state\" : \"Successfully added new question "+questionAnswers.getQuestion()+"\"}";
    }

    @RequestMapping(value = "/editTestDescription", method = RequestMethod.POST)
    public @ResponseBody void editTestDescription(
            @RequestParam(value = "testName", required = true) String testName,
            @RequestParam(value = "maxTimeToPassInMinutes", required = true) int maxTimeToPassInMinutes,
            @RequestParam(value = "oldTestName", required = true) String oldTestName,
            @RequestParam(value = "oldMaxTimeToPassInMinutes", required = false) int oldMaxTimeToPassInMinutes) {
        TestDescription oldTestDescription = service.getTestDescription(oldTestName);
        oldTestDescription.setTestName(testName);
        oldTestDescription.setMaxTimeToPassInMinutes(maxTimeToPassInMinutes);
        service.updateTestDescription(oldTestDescription.getTestDescriptionId(), oldTestDescription);
    }

    @RequestMapping(value = "/deleteQuestionAnswersFromTest", method = RequestMethod.POST)
    public @ResponseBody void deleteQuestionAnswersFromTest(
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "testName", required = true) String testName) {
        TestDescription testDescription = service.getTestDescription(testName);
        testDescription.removeQuestionAnswersRelation(service.getQuestionAnswers(question));
        service.updateTestDescription(testDescription.getTestDescriptionId(), testDescription);
    }

    @RequestMapping(value = "/getQuestionAnswersOfTest", method = RequestMethod.POST)
    public @ResponseBody List<QuestionAnswers> getQuestionAnswersOfTest(
            @RequestParam(value = "testName", required = true) String testName) {
        return service.getAllQuestionAnswersOfTestDescription(service.getTestDescription(testName));
    }

    @RequestMapping(value = "/removeSelectedTest", method = RequestMethod.POST)
    public @ResponseBody String removeSelectedTest(
            @RequestParam(value = "testName", required = true) String testName) {
        service.deleteTestDescription(service.getTestDescription(testName));
        return "{\"state\" : \"Successfully delete test "+testName+"\"}";
    }


    /*For testing on the future*/
    @RequestMapping(value = "/addNewTestDescription", method = RequestMethod.POST)
    public @ResponseBody String addNewTestDescription(
            @RequestParam(value = "testName", required = true) String testName,
            @RequestParam(value = "maxTimeToPassInMinutes", required = true) int maxTimeToPassInMinutes,
            @RequestParam(value = "questionAnswersRelations", required = false) List<String> questionAnswersRelations) {
        TestDescription testDescription = new TestDescription();
        testDescription.setTestName(testName);
        testDescription.setMaxTimeToPassInMinutes(maxTimeToPassInMinutes);
        testDescription = service.addTestDescription(testDescription);
        for(String question : questionAnswersRelations){
            testDescription.addQuestionAnswersRelation(service.getQuestionAnswers(question));
        }
        service.updateTestDescription(testDescription.getTestDescriptionId(),testDescription);
        return "{\"state\" : \"Successfully added new test "+testDescription.getTestName()+"\"}";
    }

    @RequestMapping(value = "/addNewQuestionAnswers", method = RequestMethod.POST)
    public @ResponseBody String addNewQuestionAnswers(
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "answers", required = true) Map<String, Boolean> answers,
            @RequestParam(value = "testDescriptionRelations", required = false) List<String> testDescriptionRelations) {
        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setQuestion(question);
        questionAnswers.setAnswers(answers);
        questionAnswers = service.addQuestionAnswers(questionAnswers);
        for(String testName : testDescriptionRelations){
            questionAnswers.addTestDescriptionRelation(service.getTestDescription(testName));
        }
        service.updateQuestionAnswers(questionAnswers.getQuestionAnswersId(), questionAnswers);
        return "{\"state\" : \"Successfully added new question "+questionAnswers.getQuestion()+"\"}";
    }

    @RequestMapping(value = "/addRelationForTestAndQuestion", method = RequestMethod.POST)
    public @ResponseBody String addQuestionAnswersRelation(
            @RequestParam(value = "testName", required = true) String testName,
            @RequestParam(value = "question", required = true) String question) {
        TestDescription testDescription = service.getTestDescription(testName);
        testDescription.addQuestionAnswersRelation(service.getQuestionAnswers(question));
        service.updateTestDescription(testDescription.getTestDescriptionId(), testDescription);
        return "done";
    }

    @RequestMapping(value = "/setAnswersByQuestion", method = RequestMethod.POST)
    public @ResponseBody String setAnswersByQuestion(
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "answers", required = true) Map<String, Boolean> answers) {
        QuestionAnswers questionAnswers = service.getQuestionAnswers(question);
        questionAnswers.setAnswers(answers);
        service.updateQuestionAnswers(questionAnswers.getQuestionAnswersId(), questionAnswers);
        return "done";
    }

    @RequestMapping(value = "/getQuestionAnswersOfTestDescription/{testName}", method = RequestMethod.GET)
    public @ResponseBody List<QuestionAnswers> getQuestionAnswersOfTestDescription(@PathVariable String testName) {
        return service.getAllQuestionAnswersOfTestDescription(service.getTestDescription(testName));
    }

    @RequestMapping(value = "/getAllTestDescriptionByQuestionAnswers/{question}", method = RequestMethod.GET)
    public @ResponseBody List<TestDescription> getAllTestDescriptionByQuestionAnswers(@PathVariable String question) {
        return service.getAllTestDescriptionOfQuestionAnswers(service.getQuestionAnswers(question));
    }

    @RequestMapping(value = "/getTestDescription/{testName}", method = RequestMethod.GET)
    public @ResponseBody TestDescription getTestDescription(@PathVariable String testName) {
        return service.getTestDescription(testName);
    }

    @RequestMapping(value = "/getQuestionAnswers/{question}", method = RequestMethod.GET)
    public @ResponseBody QuestionAnswers getQuestionAnswers(@PathVariable String question) {
        return service.getQuestionAnswers(question);
    }
}
