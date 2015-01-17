package ua.dp.skillsup.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /*@RequestMapping(value = "/getAllQuestionAnswers/{identifier}", method = RequestMethod.GET)
    public @ResponseBody List<QuestionAnswers> getAllQuestionAnswers(@PathVariable String identifier) {
        List<QuestionAnswers> questionAnswers = repository.findAllAddressesForContact(identifier);
        String name;
        if (addressesForContact.size() > 0) {
            name = addressesForContact.get(0).getName();
        } else {
            name = repository.loadContactDetails(identifier).getName();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addresses", addressesForContact);
        map.put("identifier", identifier);
        map.put("name", name);
        return map;
    }*/
}
