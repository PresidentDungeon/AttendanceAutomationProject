/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.utilities;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author NLens
 */
public class DateIndexerTest {

    public DateIndexerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of indexAbsenceDays method, of class DateIndexer.
     */
    @Test
    public void testIndexZeroAbsenceDays() {
        System.out.println("indexZeroAbsenceDays");
        Student student = new Student("bob", "bob@test.co.uk");

        int[] expResult =
        {
            0, 0, 0, 0, 0
        };
        int[] result = DateIndexer.indexAbsenceDays(student);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of indexAbsenceDays method, of class DateIndexer.
     */
    @Test
    public void testIndexAbsenceDays() {
        System.out.println("indexAbsenceDays");
        ObservableList<Date> dates = FXCollections.observableArrayList();
        Student student = new Student("bob", "bob@test.co.uk");

        Date date1 = new Date(LocalDate.parse("2020-03-25"), true, "");
        Date date2 = new Date(LocalDate.parse("2020-03-26"), false, "I am sick");
        Date date3 = new Date(LocalDate.parse("2020-03-27"), false, "Fredagsbar");

        dates.add(date1);
        dates.add(date2);
        dates.add(date3);
        student.setDays(dates);

        int[] expResult =
        {
            0, 0, 0, 1, 1
        };
        int[] result = DateIndexer.indexAbsenceDays(student);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of indexAbsenceDays method, of class DateIndexer.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        System.out.println("indexAbsenceDays");

        ObservableList<Date> dates = FXCollections.observableArrayList();
        Student student = new Student("bob", "bob@test.co.uk");

        Date date1 = new Date(LocalDate.parse("2020-03-25"), true, "");
        Date date2 = new Date(LocalDate.parse("2020-03-26"), false, "I am sick");
        Date date3 = new Date(LocalDate.parse("2020-03-27"), false, "Fredagsbar");
        Date date4 = new Date(LocalDate.parse("2020-03-28"), true, "");

        dates.add(date1);
        dates.add(date2);
        dates.add(date3);
        dates.add(date4);
        student.setDays(dates);
        
        int[] result = DateIndexer.indexAbsenceDays(student);
        

    }

}
