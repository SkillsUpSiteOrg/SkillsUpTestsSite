package ua.dp.skillsup.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.ApplicationDAOImpl;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/*@RunWith(SpringJUnit4ClassRunner.class)
*//*@SpringApplicationConfiguration(classes = Application.class)*//*
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@WebAppConfiguration*/
public class ApplicationTests {

    private ApplicationDAO dao = new ApplicationDAOImpl();
    private TestDescription test1 = new TestDescription();
    private TestDescription test2 = new TestDescription();
    private int currentDBSize = dao.getAllTestDescriptions().size();

	/*@Test
	public void contextLoads() {
	}*/

/*    @Before
    public void addDataToDataBase(){
        test1.setTestName("Java-0");
        test1.setDateOfCreation(new Date(2014, 11, 12));
        test1.setMaxTimeToPass(new Time(20_000));
        test2.setTestName("Java-1");
        test2.setDateOfCreation(new Date(2014, 12, 15));
        test2.setMaxTimeToPass(new Time(50_000));
        dao.addTestDescription(test1);
        dao.addTestDescription(test2);
    }*/

    @Test
    public void testAddTestDescription(){
        test1.setTestName("Java-0");
        test1.setDateOfCreation(new Date(2014, 11, 12));
        test1.setMaxTimeToPass(new Time(20_000));
        test2.setTestName("Java-1");
        test2.setDateOfCreation(new Date(2014, 12, 15));
        test2.setMaxTimeToPass(new Time(50_000));
        dao.addTestDescription(test1);
        dao.addTestDescription(test2);
        assertEquals("Asserting the db size", currentDBSize + 2, dao.getAllTestDescriptions().size());
    }
    /*@Test
    public void testGetTestDescription(){
        assertEquals(test1, dao.getTestDescription(test1.getTestDescriptionId()));
        assertEquals(test2, dao.getTestDescription(test2.getTestDescriptionId()));
    }*/

    /*@Test
    public void testUpdateTestDescription(){
        test1.setTestName("New Java-0");
        test2.setTestName("New Java-1");
        dao.updateTestDescription(test1);
        dao.updateTestDescription(test2);
        assertEquals(currentDBSize + 2, dao.getAllTestDescriptions().size());
        assertEquals("New Java-0", dao.getTestDescription(test1.getTestDescriptionId()).getTestName());
        assertEquals("New Java-1", dao.getTestDescription(test2.getTestDescriptionId()).getTestName());
    }*/

    /*@Test
    public void testDeleteTestDescription(){
        dao.deleteTestDescription(test1.getTestDescriptionId());
        dao.deleteTestDescription(test2.getTestDescriptionId());
        assertEquals(currentDBSize, dao.getAllTestDescriptions().size());
    }*/
}
