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
public class AbsenceCalculatorTest {
    
    public AbsenceCalculatorTest() {
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
     * Test of calculateAttendance method, of class AbsenceCalculator.
     */
    @Test
    public void testCalculateAttendance() {
        System.out.println("calculateAttendance");
        ObservableList<Date> dates = FXCollections.observableArrayList();
        Student student = new Student("test", "test@test.test");
        
        Date date1 = new Date(LocalDate.parse("2020-03-26"), true, "I am sick");
        Date date2 = new Date(LocalDate.parse("2020-03-27"), false, "I am sick");

        dates.add(date1);
        dates.add(date2);
        student.setDays(dates);
        
        double expResult = 50.0;
        double result = AbsenceCalculator.calculateAttendance(student);
        assertEquals(expResult, result, 1E-2);
        
        //testing part 2
        
        Date date3 = new Date(LocalDate.parse("2020-03-27"), true, "");
        dates.add(date3);
        student.setDays(dates);
        
        expResult = 33.33;
        result = AbsenceCalculator.calculateAttendance(student);
        assertEquals(expResult, result, 1E-2);
        
        //testing part 3
        ObservableList<Date> datesEmpty = FXCollections.observableArrayList();
        student.setDays(datesEmpty);
        expResult = 00.00;
        result = AbsenceCalculator.calculateAttendance(student);
        assertEquals(expResult, result, 1E-2);
    }

    /**
     * Test of getDaysAbsence method, of class AbsenceCalculator.
     */
    @Test
    public void testGetDaysAbsence() {
        System.out.println("getDaysAbsence");
        Student student = new Student("test", "test@test.test");
        
        int expResult = 0;
        int result = AbsenceCalculator.getDaysAbsence(student);
        assertEquals(expResult, result);
        
        //Test 2
        ObservableList<Date> dates = FXCollections.observableArrayList();
        Date date1 = new Date(LocalDate.parse("2020-03-27"), true, "");
        Date date2 = new Date(LocalDate.parse("2020-03-27"), false, "I am sick");
        dates.add(date1);
        dates.add(date2);
        student.setDays(dates);
        
        expResult = 1;
        result = AbsenceCalculator.getDaysAbsence(student);
        assertEquals(expResult, result, 1E-2);
        
        //test 3
        for (int i = 0; i < 10; i++)
        {
            Date date = new Date(LocalDate.parse("2020-03-27"), false, "I am sick");
            dates.add(date);
        }
        
        expResult = 11;
        result = AbsenceCalculator.getDaysAbsence(student);
        assertEquals(expResult, result, 1E-2);
        
    }

    /**
     * Test of getDaysPresent method, of class AbsenceCalculator.
     */
    @Test
    public void testGetDaysPresent() {
        System.out.println("getDaysPresent");
        Student student = new Student("test", "test@test.test");
        
        int expResult = 0;
        int result = AbsenceCalculator.getDaysPresent(student);
        assertEquals(expResult, result);
        
        //Test 2
        ObservableList<Date> dates = FXCollections.observableArrayList();
        Date date1 = new Date(LocalDate.parse("2020-03-27"), true, "");
        Date date2 = new Date(LocalDate.parse("2020-03-27"), false, "I am sick");
        dates.add(date1);
        dates.add(date2);
        student.setDays(dates);
        
        expResult = 1;
        result = AbsenceCalculator.getDaysPresent(student);
        assertEquals(expResult, result, 1E-2);
        
        //test 3
        for (int i = 0; i < 10; i++)
        {
            Date date = new Date(LocalDate.parse("2020-03-27"), true, "");
            dates.add(date);
        }
        
        expResult = 11;
        result = AbsenceCalculator.getDaysPresent(student);
        assertEquals(expResult, result, 1E-2);
        
    }

}
