package ua.dp.skillsup.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.ApplicationDAOImpl;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        /*SpringApplication.run(Application.class, args);*/
        ApplicationDAO dao = new ApplicationDAOImpl();
        System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());
        for(TestDescription test : dao.getAllTestDescriptions()){
            System.out.println(test);
        }
        for(TestDescription test : dao.getAllTestDescriptions()){
            dao.deleteTestDescription(test.getTestDescriptionId());
        }
        System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());
    }
}
