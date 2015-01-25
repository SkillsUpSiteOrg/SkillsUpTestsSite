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

import java.util.List;

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
        for (TestDescription test : dao.getAllTestDescriptions()){
            System.out.println("--------------------------------------------------------");
            System.out.println(test);
            System.out.println(test.getQuestionAnswersRelations());
        }
    }

    public void initialize(ApplicationContext context, ApplicationDAO dao){
        //Creating new tests
        TestDescription test1 = new TestDescription();
        test1.setTestName("Java-0");
        test1.setMaxTimeToPassInMinutes(90);
        TestDescription test2 = new TestDescription();
        test2.setTestName("Java-1");
        test2.setMaxTimeToPassInMinutes(60);
        TestDescription test3 = new TestDescription();
        test3.setTestName("Java-2");
        test3.setMaxTimeToPassInMinutes(120);
        //Creating new questions
        QuestionAnswers question1 = new QuestionAnswers();
        question1.setQuestion("How is it working....?");
        question1.addAnswers("ans1", false);
        question1.addAnswers("ans2", false);
        question1.addAnswers("ans3", true);
        question1.addAnswers("ans4", false);
        QuestionAnswers question2 = new QuestionAnswers();
        question2.setQuestion("Why is it working....?");
        question2.addAnswers("ans1", true);
        question2.addAnswers("ans2", false);
        question2.addAnswers("ans3", false);
        question2.addAnswers("ans4", true);
        //Adding questions to test
        test1.addQuestionAnswersRelations(question1);
        test1.addQuestionAnswersRelations(question2);
        test2.addQuestionAnswersRelations(question2);
        //Adding test to DB
        dao.addTestDescription(test1);
        dao.addTestDescription(test2);
        dao.addTestDescription(test3);
    }
}
