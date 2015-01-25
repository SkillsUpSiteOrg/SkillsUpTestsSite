package ua.dp.skillsup.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;
import ua.dp.skillsup.tests.service.ApplicationService;

import java.util.ArrayList;
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

    @RequestMapping(value = "/addRelationByTestDescriptionIdAndQuestionAnswersId", method = RequestMethod.POST)
    public @ResponseBody String addQuestionAnswersRelation(
            @RequestParam(value = "testDescriptionId", required = true) long testDescriptionId,
            @RequestParam(value = "questionAnswersId", required = true) long questionAnswersId) {
            //todo

        return "in process";
    }

    @RequestMapping(value = "/setAnswersByQuestionAnswersId", method = RequestMethod.POST)
    public @ResponseBody String addRelationByTestDescriptionIdAndQuestionAnswersId(
            @RequestParam(value = "questionAnswersId", required = true) long questionAnswersId,
            @RequestParam(value = "testDescriptionId", required = true) long testDescriptionId) {
            //todo

        return "in process";
    }

    @RequestMapping(value = "/addNewQuestionAnswers", method = RequestMethod.POST)
    public @ResponseBody String addNewQuestionAnswers(
            @RequestParam(value = "question", required = true) String question,
            @RequestParam(value = "answers", required = true) Map<String, Boolean> answers) {
        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setQuestion(question);
        questionAnswers.setAnswers(answers);

        questionAnswers = service.addQuestionAnswers(questionAnswers);
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

    @RequestMapping(value = "/getQuestionAnswersById/{id}", method = RequestMethod.GET)
    public @ResponseBody QuestionAnswers getQuestionAnswersById(@PathVariable Long id) {
        QuestionAnswers questionAnswers = service.getQuestionAnswers(id);

        return questionAnswers;
    }

    @RequestMapping(value = "/getTestDescriptionById/{id}", method = RequestMethod.GET)
    public @ResponseBody TestDescription getTestDescriptionById(@PathVariable Long id) {
        TestDescription testDescription = service.getTestDescription(id);

        return testDescription;
    }

    //Method returning just a stub for testing UI.
    @RequestMapping(value = "/getQuestionAnswersOfTest", method = RequestMethod.POST)
    public @ResponseBody List<String> getQuestionAnswersOfTest(
            @RequestParam(value = "testDescriptionId", required = true) long testDescriptionId){
        TestDescription testDescription = service.getTestDescription(testDescriptionId);
        System.out.println(testDescription.getTestName());
        List<String> list = new ArrayList<String>();
        list.add("Question1");
        list.add("Question2");
        list.add("Question3");
        return list;
    }

    //Method returning just a stub for testing UI.
    @RequestMapping(value = "/getAllQuestionAnswers", method = RequestMethod.GET)
    public @ResponseBody List<String> getAllQuestionAnswers(){
        //List<String> allQuestionAnswers = service.getAllQuestionAnswers();
        List<String> list = new ArrayList<String>();
        list.add("Question10");
        list.add("Question20");
        list.add("Question30");
        return list;
    }

}
