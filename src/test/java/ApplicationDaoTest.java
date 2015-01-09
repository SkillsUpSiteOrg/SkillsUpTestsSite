import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import java.util.List;

import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.dp.skillsup.tests.Application;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

/**
 * Created by Daniel on 23.12.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationDaoTest {

    @Autowired
    @Qualifier("applicationDao")
    private ApplicationDAO dao;

    @Before
    public void setUp() throws Exception {
        TestDescription test1 = new TestDescription();
        test1.setTestName(".NET-1");
        test1.setMaxTimeToPassInMinutes(90);
        TestDescription test2 = new TestDescription();
        test2.setTestName(".NET-2");
        test2.setMaxTimeToPassInMinutes(120);
        dao.addTestDescription(test1);
        dao.addTestDescription(test2);
        for(TestDescription test : dao.getAllTestDescriptions()){
            System.out.println(test);
        }
    }

    @After
    public void tearDown() {
        for(TestDescription test : dao.getAllTestDescriptions()){
            dao.deleteTestDescription(test);
        }
    }

    @Test
    public void testGetAllTestDescriptions() throws Exception {
        List<TestDescription> testDescriptions = dao.getAllTestDescriptions();
        Assert.assertEquals("did not get expected number of entities ", 2, testDescriptions.size());
    }

    @Test
    public void testAddTestDescription() throws Exception {
        TestDescription testDescription = new TestDescription();
        testDescription.setTestName("Java-0");
        testDescription.setMaxTimeToPassInMinutes(180);

        dao.addTestDescription(testDescription);
        Assert.assertEquals("Checking the quantity of tests", 3, dao.getAllTestDescriptions().size());
    }

    @Test
    public void testDeleteTestDescription() throws Exception {
        long testId = dao.getAllTestDescriptions().get(0).getTestDescriptionId();
        TestDescription testDescription = dao.getTestDescription(testId);
        dao.deleteTestDescription(testDescription);
        TestDescription testDescriptionActual = dao.getTestDescription(testId);
        Assert.assertNull("delete did not work", testDescriptionActual);
    }

    @Test
    public void testUpdateTestDescription() throws Exception {
        long testId = dao.getAllTestDescriptions().get(0).getTestDescriptionId();
        TestDescription testDescription = dao.getTestDescription(testId);
        testDescription.setTestName("New Java");
        dao.updateTestDescription(testId, testDescription);
        TestDescription testDescriptionActual = dao.getTestDescription(testId);
        Assert.assertEquals(testDescription.getTestName(), testDescriptionActual.getTestName());
        Assert.assertEquals(dao.getAllTestDescriptions().size(), 2);
    }

    @Test
    public void testGetTestDescription() throws Exception {
        long testId = dao.getAllTestDescriptions().get(0).getTestDescriptionId();
        TestDescription testDescription = dao.getTestDescription(testId);

        Assert.assertEquals(testDescription.getTestDescriptionId(), testId);
        Assert.assertEquals(testDescription.getTestName(), ".NET-1");
        Assert.assertEquals(testDescription.getMaxTimeToPassInMinutes(), 90);
    }
}
