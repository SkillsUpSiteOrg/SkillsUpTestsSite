package ua.dp.skillsup.tests;

import com.google.common.collect.Lists;
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
        test1.setQuestionAnswersRelations(Lists.newArrayList(questionAnswers));
        test1.addQuestionAnswersRelations(questionAnswers);
        test1 = dao.addTestDescription(test1);

        System.out.println(test1.getQuestionAnswersRelations().get(0));
        dao.getTestDescription(test1.getTestDescriptionId());
        List<QuestionAnswers> questionAnswers1 = test1.getQuestionAnswersRelations();
        System.out.println(test1);
        List<QuestionAnswers> questionAnswers2 = test1.getQuestionAnswersRelations();
        System.out.println(questionAnswers1.get(0));
        System.out.println(questionAnswers2.get(0));
    }
}
