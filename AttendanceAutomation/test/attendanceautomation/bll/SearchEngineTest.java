package attendanceautomation.bll;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Student;
import attendanceautomation.utilities.DateIndexer;
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
public class SearchEngineTest {
    
    public SearchEngineTest() {
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
     * Test of searchStudent method, of class StudentManager.
     */
    @Test
    public void searchStudentTest() {
        
        StudentManager studentManager = new StudentManager();
        ObservableList<Student> students = FXCollections.observableArrayList();
        ObservableList<Student> results = FXCollections.observableArrayList();
        
        Student student1 = new Student("Peter", "Peter@easv.dk");
        Student student2 = new Student("Lasse", "Lasse@easv.dk");
        Student student3 = new Student("Ole", "Ole@easv.dk");
        Student student4 = new Student("Ella", "Ella@easv.dk");
        
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        
        Classroom allClasses = new Classroom("All");
        Classroom classroom1 = new Classroom("CS 2019A");
        Classroom classroom2 = new Classroom("CS 2019B");
        
        allClasses.setId(0);
        classroom1.setId(1);
        classroom1.setId(2);
        
        classroom1.getStudents().add(student1);
        classroom1.getStudents().add(student2);
        classroom2.getStudents().add(student3);
        classroom2.getStudents().add(student4);
        
        String searchName = "p";
        
        results = studentManager.searchStudent(searchName, allClasses, students);
        
        assertEquals(1, results.size());
        assertEquals(student1, results.get(0));
    }
}
