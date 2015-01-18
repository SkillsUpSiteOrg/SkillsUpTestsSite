package ua.dp.skillsup.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

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

        TestDescription test2 = new TestDescription();
        test2.setTestName("Java-1");
        test2.setMaxTimeToPassInMinutes(60);

        TestDescription test3 = new TestDescription();
        test3.setTestName("Java-2");
        test3.setMaxTimeToPassInMinutes(120);

        dao.addTestDescription(test1);
        dao.addTestDescription(test2);
        dao.addTestDescription(test3);
    }
}
