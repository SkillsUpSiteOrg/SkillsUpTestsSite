package ua.dp.skillsup.tests.dao;

import org.apache.log4j.Logger;
import org.dbunit.Assertion;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.springframework.jdbc.datasource.DataSourceUtils;
import ua.dp.skillsup.tests.dao.entity.TestDescription;

/**
 * Created by Daniel on 23.12.2014.
 */

public class ApplicationDaoTests extends AbstractTransactionalDataSourceSpringContextTests {

    private ApplicationDAO dao;

    private static final Logger logger = Logger.getLogger(ApplicationDaoTests.class);

    public ApplicationDaoTests() {
        super();
        logger.info("setting up test");
        ApplicationContext ctx = super.getApplicationContext();
        dao = (ApplicationDAO) ctx.getBean("applicationDao");
        assertNotNull(dao);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "applicationContext.xml" };
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        DataSource dataSource = jdbcTemplate.getDataSource();
        Connection con = DataSourceUtils.getConnection(dataSource);
        IDatabaseConnection dbUnitCon = new DatabaseConnection(con);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("testDescription-data.xml"));
        try {
            DatabaseOperation.REFRESH.execute(dbUnitCon, dataSet);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    @Test
    public void testGetAllTestDescriptions() throws Exception {
        List<TestDescription> testDescriptions = dao.getAllTestDescriptions();
        assertEquals("did not get expected number of entities ", 2, testDescriptions.size());
    }

    @Test
    public void testAddTestDescription() throws Exception {
        TestDescription testDescription = new TestDescription();
        testDescription.setTestName("Java-0");
        testDescription.setDateOfCreation(new DateTime(2014, 12, 15, 0, 0));
        testDescription.setMaxTimeToPassInMinutes(90);

        dao.addTestDescription(testDescription);
        TestDescription testDescriptionActual = dao.getTestDescription(3L);
        String[] ignore = {"id"};
        assertNotNull("expected save to work", testDescriptionActual);
    }

    @Test
    public void testDeleteTestDescription() throws Exception {
        TestDescription testDescription = dao.getTestDescription(1L);
        dao.deleteTestDescription(testDescription);
        TestDescription testDescriptionActual = dao.getTestDescription(1L);
        assertNull("delete did not work", testDescriptionActual);
    }

    @Test
    public void testUpdateTestDescription() throws Exception {
        TestDescription testDescription = dao.getTestDescription(1L);
        testDescription.setTestName("New Java");
        dao.updateTestDescription(1L, testDescription);
        TestDescription testDescriptionActual = dao.getTestDescription(1L);
        Assert.assertEquals(testDescription.getTestName(), testDescriptionActual.getTestName());
        Assert.assertEquals(dao.getAllTestDescriptions().size(), 2);
    }

    @Test
    public void testGetTestDescription() throws Exception {
        TestDescription testDescription = dao.getTestDescription(1L);

        Assert.assertEquals(testDescription.getTestDescriptionId(), 1L);
        Assert.assertEquals(testDescription.getTestName(), "Java-0");
        Assert.assertEquals(testDescription.getDateOfCreation().toString(), "2013-05-12 00:00:00.0");
        Assert.assertEquals(testDescription.getMaxTimeToPassInMinutes(), 120);
    }
}
