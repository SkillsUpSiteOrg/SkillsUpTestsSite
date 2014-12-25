package ua.dp.skillsup.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
<<<<<<< HEAD
    public ModelAndView getPages() {
        return new ModelAndView("home");
=======
    public String getPages() {
        return "main";
>>>>>>> 9cc052eb84eb0bcabba0a65251b3526fb58ae51a
    }

    @RequestMapping(value = "/getAllTestDescriptions", method = RequestMethod.GET)
    public @ResponseBody List<TestDescription> getAllTestDescriptions() {
        List<TestDescription> testDescriptions = new ArrayList<>();
        testDescriptions.addAll(service.getAllTestDescriptions());
        return testDescriptions;
    }
}
