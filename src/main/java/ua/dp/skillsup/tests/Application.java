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
        ApplicationContext context = SpringApplication.run(Application.class, args);
        //Just to test
        ApplicationDAO dao = context.getBean("applicationDao", ApplicationDAO.class);

        TestDescription test1 = new TestDescription();
        test1.setTestName("Java-0");
        test1.setMaxTimeToPassInMinutes(90);

        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setQuestion("How it working....?");
        questionAnswers.addAnswers("ans1", false);
        questionAnswers.addAnswers("ans2", false);
        questionAnswers.addAnswers("ans3", true);
        questionAnswers.addAnswers("ans4", false);
            System.out.println(questionAnswers.toString());
        test1.addQuestionAnswersRelations(questionAnswers);
/*
        test1.setQuestionAnswersRelations(Lists.newArrayList(questionAnswers));
*/


        TestDescription test_1 = dao.addTestDescription(test1);
            System.out.println(test_1.getQuestionAnswersRelations());

        QuestionAnswers questionAnswers_1 = new QuestionAnswers();
        questionAnswers_1.setQuestion("Why it working....?");
        questionAnswers_1.addAnswers("ans1", true);
        questionAnswers_1.addAnswers("ans2", false);
        questionAnswers_1.addAnswers("ans3", false);
        questionAnswers_1.addAnswers("ans4", true);
            System.out.println(questionAnswers_1);
        questionAnswers_1.addTestDescriptionRelations(test_1);
        QuestionAnswers questionAnswers_11 = dao.addQuestionAnswers(questionAnswers_1);
            System.out.println(questionAnswers_11);


        TestDescription test_11 = dao.addTestDescription(test_1);
        List<QuestionAnswers> listQuestionAnswers1 = test_11.getQuestionAnswersRelations();
        /*List<QuestionAnswers> listQuestionAnswers2 = dao.getAllQuestionAnswersForTestDescription(test_11);*/
            System.out.println(listQuestionAnswers1.toString());
            /*System.out.println(listQuestionAnswers2.toString());*/


            System.out.println(dao.getAllTestDescriptions().get(0).getQuestionAnswersRelations());
        /*System.out.println(dao.getTestDescription(2));*/

        System.out.println(dao.getAllQuestionAnswers());
        System.out.println(dao.getQuestionAnswers(1));
/*
        System.out.println(dao.getQuestionAnswers(2));
*/

    }
}
