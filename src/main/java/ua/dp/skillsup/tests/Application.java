package ua.dp.skillsup.tests;

import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        //Just to test
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        ApplicationDAO dao = context.getBean("applicationDao", ApplicationDAO.class);

        TestDescription test1 = new TestDescription();
        test1.setTestName("Java-0");
        test1.setDateOfCreation(new DateTime(2014, 12, 23, 0, 0));
        test1.setMaxTimeToPassInMinutes(90);

        dao.addTestDescription(test1);

        /*System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());
        for(TestDescription test : dao.getAllTestDescriptions()){
            System.out.println(test);
            test1 = test;
            //System.out.println("Check: " + dao.getTestDescription(test.getTestDescriptionId()));
        }
//        //Trying to update my entity in DB. Now it adds new entity to DB, it's wrong.
        System.out.println("Updating the name of entity: ");
        test1.setTestName("New Java-0");
        dao.updateTestDescription(test1.getTestDescriptionId(), test1);
        System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());
        for(TestDescription test : dao.getAllTestDescriptions()){
            System.out.println(test);
        }
        //Deleting all entities in DB
        for(TestDescription test : dao.getAllTestDescriptions()){
            dao.deleteTestDescription(test);
            System.out.println("Successfully deleted one");
        }
        System.out.println("Current number of tests in DB: " + dao.getAllTestDescriptions().size());*/
    }
}

@Configuration
@ImportResource("classpath:/applicationContext.xml")
class XmlImportingConfiguration {
}
