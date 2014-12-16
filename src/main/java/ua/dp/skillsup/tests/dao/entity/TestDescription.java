package ua.dp.skillsup.tests.dao.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * Created by Daniel on 16.12.2014.
 */
@Entity
@Table(name = "TEST_DESCRIPTION")
@NamedQuery(name = "TestDescription.getAll", query = "SELECT c from TestDescription c")
public class TestDescription {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private long testDescriptionId;

    @Column(name = "TEST_NAME")
    private String testName;

    @Column(name = "DATE")
    private Date dateOfCreation;

    @Column(name = "TIME")
    private Time maxTimeToPass;

    public long getTestDescriptionId() {
        return testDescriptionId;
    }

    public void setTestDescriptionId(long testDescriptionId) {
        this.testDescriptionId = testDescriptionId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Time getMaxTimeToPass() {
        return maxTimeToPass;
    }

    public void setMaxTimeToPass(Time maxTimeToPass) {
        this.maxTimeToPass = maxTimeToPass;
    }

    @Override
    public String toString() {
        return "TestDescription{" +
                "Id=" + testDescriptionId +
                ", Name='" + testName + '\'' +
                ", Date of creation=" + dateOfCreation +
                ", Max time to pass=" + maxTimeToPass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDescription that = (TestDescription) o;

        if (testDescriptionId != that.testDescriptionId) return false;
        if (dateOfCreation != null ? !dateOfCreation.equals(that.dateOfCreation) : that.dateOfCreation != null)
            return false;
        if (maxTimeToPass != null ? !maxTimeToPass.equals(that.maxTimeToPass) : that.maxTimeToPass != null)
            return false;
        if (testName != null ? !testName.equals(that.testName) : that.testName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (testDescriptionId ^ (testDescriptionId >>> 32));
        result = 31 * result + (testName != null ? testName.hashCode() : 0);
        result = 31 * result + (dateOfCreation != null ? dateOfCreation.hashCode() : 0);
        result = 31 * result + (maxTimeToPass != null ? maxTimeToPass.hashCode() : 0);
        return result;
    }
}
