package ua.dp.skillsup.tests.dao;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.dp.skillsup.tests.config.DBUnitConfig;
import ua.dp.skillsup.tests.dao.ApplicationDAO;
import ua.dp.skillsup.tests.dao.ApplicationDAOImpl;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DaoTests extends DBUnitConfig{

    private ApplicationDAO dao = new ApplicationDAOImpl();
    private EntityManager em = Persistence.createEntityManagerFactory("item-manager-pu").createEntityManager();

    public DaoTests(String name) {
        super(name);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("testDescription-data.xml"));

        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    @Test
    public void testGetAllTestDescriptions() throws Exception {
        List<TestDescription> testDescriptions = dao.getAllTestDescriptions();

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("testDescription-data.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEquals(expectedData, actualData);
        Assert.assertEquals(expectedData.getTable("TEST_DESCRIPTION").getRowCount(), testDescriptions.size());
    }

    @Test
    public void testAddTestDescription() throws Exception {
        TestDescription testDescription = new TestDescription();
        testDescription.setTestName("Java-0");
        testDescription.setDateOfCreation(new DateTime(2014, 12, 15, 0, 0));
        testDescription.setMaxTimeToPassInMinutes(90);

        dao.addTestDescription(testDescription);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("testDescription-add-data.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), expectedData);

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "TEST_DESCRIPTION", ignore);
    }

    @Test
    public void testDeleteTestDescription() throws Exception {
        TestDescription testDescription = new TestDescription();
        testDescription.setTestName(".NET-0");
        testDescription.setDateOfCreation(new DateTime(2015, 01, 02, 0, 0));
        testDescription.setMaxTimeToPassInMinutes(70);

        testDescription = em.merge(testDescription);

        long id = testDescription.getTestDescriptionId();

        dao.deleteTestDescription(testDescription);
        TestDescription testDescriptionFromDb = em.find(TestDescription.class, id);

        Assert.assertNull(testDescriptionFromDb);
    }

    @Test
    public void testUpdateTestDescription() throws Exception {
        TestDescription testDescription = em.find(TestDescription.class, 1L);
        testDescription.setTestName("New Java");
        dao.updateTestDescription(testDescription);
        TestDescription testDescriptionFromDb = em.find(TestDescription.class, 1L);

        Assert.assertEquals(testDescription.getTestName(), testDescriptionFromDb.getTestName());
    }

    @Test
    public void testGetTestDescription() throws Exception {
        TestDescription testDescription = dao.getTestDescription(1L);
        TestDescription testDescription1 = em.find(TestDescription.class, 1L);

        Assert.assertEquals(testDescription.getTestDescriptionId(), testDescription1.getTestDescriptionId());
        Assert.assertEquals(testDescription.getTestName(), testDescription1.getTestName());
        Assert.assertEquals(testDescription.getDateOfCreation(), testDescription1.getDateOfCreation());
        Assert.assertEquals(testDescription.getMaxTimeToPassInMinutes(), testDescription1.getMaxTimeToPassInMinutes());
    }

}
