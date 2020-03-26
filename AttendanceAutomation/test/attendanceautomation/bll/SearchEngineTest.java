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

    StudentManager studentManager = new StudentManager();

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

    public ObservableList<Student> createStudenttdata() {
        StudentManager studentManager = new StudentManager();
        ObservableList<Student> students = FXCollections.observableArrayList();

        Student student1 = new Student("Peter", "Peter@easv.dk");
        Student student2 = new Student("Lasse", "Lasse@easv.dk");
        Student student3 = new Student("Ole", "Ole@easv.dk");
        Student student4 = new Student("Ella", "Ella@easv.dk");
        
        student1.setId(1);
        student2.setId(2);
        student3.setId(3);
        student4.setId(4);
        
        Classroom allClasses = new Classroom("All");
        Classroom classroom1 = new Classroom("CS 2019A");
        Classroom classroom2 = new Classroom("CS 2019B");

        allClasses.setId(0);
        classroom1.setId(1);
        classroom2.setId(2);

        student1.getClassRoom().add(classroom1);
        student2.getClassRoom().add(classroom1);
        student3.getClassRoom().add(classroom2);
        student4.getClassRoom().add(classroom2);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        return students;

    }
    
    public ObservableList<Classroom> createClassroomtdata(ObservableList<Student> students) {
        ObservableList<Classroom> classes = FXCollections.observableArrayList();

        Classroom allClasses = new Classroom("All");
        Classroom classroom1 = new Classroom("CS 2019A");
        Classroom classroom2 = new Classroom("CS 2019B");

        allClasses.setId(0);
        classroom1.setId(1);
        classroom2.setId(2);

        classes.add(allClasses);
        classes.add(classroom1);
        classes.add(classroom2);

        return classes;
    }

    /**
     * Test of searchStudent method, of class StudentManager.
     */
    @Test
    public void searchStudentTest() {

        StudentManager studentManager = new StudentManager();
        ObservableList<Student> students = createStudenttdata();
        ObservableList<Classroom> classes = createClassroomtdata(students);

        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Student student4 = students.get(3);

        Classroom allClasses = classes.get(0);
        Classroom classroom1 = classes.get(1);
        Classroom classroom2 = classes.get(2);

        ObservableList<Student> results = FXCollections.observableArrayList();
        String searchName = "p";

        results = studentManager.searchStudent(searchName, allClasses, students);

        assertEquals(1, results.size());
        assertEquals(student1, results.get(0));
    }

    /**
     * Test of searchStudent method, of class StudentManager.
     */
    @Test
    public void searchAllTest() {

        StudentManager studentManager = new StudentManager();
        ObservableList<Student> students = createStudenttdata();
        ObservableList<Classroom> classes = createClassroomtdata(students);

        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Student student4 = students.get(3);

        Classroom allClasses = classes.get(0);
        Classroom classroom1 = classes.get(1);
        Classroom classroom2 = classes.get(2);

        ObservableList<Student> results = FXCollections.observableArrayList();
        String searchName = "e";

        results = studentManager.searchStudent(searchName, allClasses, students);

        assertEquals(4, results.size());
        assertEquals(student1, results.get(0));
    }

     /**
     * Test of searchStudent method, of class StudentManager.
     */
    @Test
    public void searchAllWithClassTest() {

        StudentManager studentManager = new StudentManager();
        ObservableList<Student> students = createStudenttdata();
        ObservableList<Classroom> classes = createClassroomtdata(students);

        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Student student4 = students.get(3);

        Classroom allClasses = classes.get(0);
        Classroom classroom1 = classes.get(1);
        Classroom classroom2 = classes.get(2);

        ObservableList<Student> results = FXCollections.observableArrayList();
        String searchName = "e";

        results = studentManager.searchStudent(searchName, classroom1, students);

        assertEquals(2, results.size());
        assertEquals(student1, results.get(0));
    }
    @Test
    public void searchZeroResultsWithClassTest() {

        StudentManager studentManager = new StudentManager();
        ObservableList<Student> students = createStudenttdata();
        ObservableList<Classroom> classes = createClassroomtdata(students);

        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Student student4 = students.get(3);

        Classroom allClasses = classes.get(0);
        Classroom classroom1 = classes.get(1);
        Classroom classroom2 = classes.get(2);
        
        System.out.println(classroom2.getId());

        ObservableList<Student> results = FXCollections.observableArrayList();
        String searchName = "p";

        results = studentManager.searchStudent(searchName, classroom2, students);
        
        assertEquals(0, results.size());
    }
}
