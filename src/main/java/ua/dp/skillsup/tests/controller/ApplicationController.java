package ua.dp.skillsup.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
    public ModelAndView getPages() {
        System.out.println("getPages() method is running...");
        return new ModelAndView("index");
    }

    /*@RequestMapping("/"*//*, method = RequestMethod.GET*//*)
    public String index (*//*Model model*//*) {
        //System.out.println("index() method is running...");
        //model.addAttribute("index");
        return "index";
    }*/

    @RequestMapping(value = "/getAllTestDescriptions", method = RequestMethod.GET)
    public @ResponseBody List<TestDescription> getAllTestDescriptions() {
        List<TestDescription> testDescriptions = new ArrayList<>();
        testDescriptions.addAll(service.getAllTestDescriptions());
        return testDescriptions;
    }
}
