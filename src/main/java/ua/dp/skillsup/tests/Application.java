package ua.dp.skillsup.tests;

import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.ApplicationDAOImpl;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        /*SpringApplication.run(Application.class, args);*/
        //Just to test
        ApplicationDAO dao = new ApplicationDAOImpl();
        System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());
        //Creating new TestDescription
        TestDescription test1 = new TestDescription();
        test1.setTestName("Java-0");
        test1.setDateOfCreation(new DateTime(2014, 12, 23, 0, 0));
        test1.setMaxTimeToPassInMinutes(90);
        //Adding new TestDescription to DB
        dao.addTestDescription(test1);
        for(TestDescription test : dao.getAllTestDescriptions()){
            System.out.println(test);
            //System.out.println("Check: " + dao.getTestDescription(test.getTestDescriptionId()));
        }
        //Trying to update my entity in DB. Now it adds new entity to DB, it's wrong.
        test1.setTestName("New Java-0");
        dao.updateTestDescription(test1);
        for(TestDescription test : dao.getAllTestDescriptions()){
            System.out.println(test);
        }
        //Deleting all entities in DB
        for(TestDescription test : dao.getAllTestDescriptions()){
            dao.deleteTestDescription(test);
        }
        System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());
    }
}
