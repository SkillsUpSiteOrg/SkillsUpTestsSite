package ua.dp.skillsup.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.QuestionAnswers;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath:/applicationContext.xml")
public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        ApplicationContext context = SpringApplication.run(Application.class, args);
        ApplicationDAO dao = context.getBean("applicationDao", ApplicationDAO.class);
        //Initializing data to test Application
        application.initialize(context, dao);
        //application.getStateDB(context, dao);
    }

    public void getStateDB(ApplicationContext context, ApplicationDAO dao){
        System.out.println("-----------------------------getAllTestDescriptions----------------------------------");
        for (TestDescription testDescription : dao.getAllTestDescriptions()) {
            System.out.println(testDescription.getTestDescriptionId()+" - testDescription ("+testDescription.getTestName()+") => Relations:");
            for (QuestionAnswers questionAnswers : testDescription.getQuestionAnswersRelations()) {
                System.out.println("|->"+questionAnswers);
            }
        }
        System.out.println("------------------------------getAllQuestionAnswers---------------------------------");
        for (QuestionAnswers questionAnswers : dao.getAllQuestionAnswers()) {
            System.out.println(questionAnswers.getQuestionAnswersId()+" - questionAnswers ("+questionAnswers.getQuestion()+") => Relations:");
            for (TestDescription testDescription : questionAnswers.getTestDescriptionRelations()) {
                System.out.println("|->"+testDescription);
            }
        }
        System.out.println("-------------------------------------------------------------------------------------");

    }

    public void initialize(ApplicationContext context, ApplicationDAO dao){
        //Creating new tests
        TestDescription test1 = new TestDescription();
        test1.setTestName("Java-0");
        test1.setMaxTimeToPassInMinutes(1);
        TestDescription test2 = new TestDescription();
        test2.setTestName("Java-1");
        test2.setMaxTimeToPassInMinutes(15);
        TestDescription test3 = new TestDescription();
        test3.setTestName("Java-2");
        test3.setMaxTimeToPassInMinutes(20);
        //Creating new questions
        QuestionAnswers question1 = new QuestionAnswers();
        question1.setQuestion("What are main three words?");
        question1.addAnswers("Inheritance", true);
        question1.addAnswers("Encapsulation", true);
        question1.addAnswers("Polymorphism", true);
        question1.addAnswers("Abstraction", false);
        QuestionAnswers question2 = new QuestionAnswers();
        question2.setQuestion("What collection doesn't extend Collection interface?");
        question2.addAnswers("Set", false);
        question2.addAnswers("List", false);
        question2.addAnswers("Map", true);
        question2.addAnswers("Queue", false);
        QuestionAnswers question3 = new QuestionAnswers();
        question3.setQuestion("Does the new file creates if run this code: File file = new File('path');?");
        question3.addAnswers("Yes", false);
        question3.addAnswers("No", true);
        //Adding questions to test
        test1.addQuestionAnswersRelation(question1);
        test1.addQuestionAnswersRelation(question2);
        test1.addQuestionAnswersRelation(question3);
        test2.addQuestionAnswersRelation(question2);
        test3.addQuestionAnswersRelation(question3);
        //Adding test to DB
        dao.addTestDescription(test1);
        dao.addTestDescription(test2);
        dao.addTestDescription(test3);
    }
}
