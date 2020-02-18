/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ander
 */
public class MockStudentManager {

    List<Student> students = new ArrayList<Student>();

    public MockStudentManager() {
        Student s1 = new Student("Michael Hansen", "Mich322@easv365.dk");
        Student s2 = new Student("Henrik Christensen", "Henr147@easv365.dk");
        Student s3 = new Student("Martin Jørgensen", "Mart863@easv365.dk");

        Date date1 = new Date(LocalDate.of(2020, 1, 6), true, "");
        Date date2 = new Date(LocalDate.of(2020, 1, 7), false, "School project");
        Date date3 = new Date(LocalDate.of(2020, 1, 8), true, "");
        Date date4 = new Date(LocalDate.of(2020, 1, 9), true, "");
        Date date5 = new Date(LocalDate.of(2020, 1, 10), true, "");

        s1.getDays().add(date1);
        s1.getDays().add(date2);
        s1.getDays().add(date3);
        s1.getDays().add(date4);
        s1.getDays().add(date5);
        
        s2.getDays().add(date1);
        s2.getDays().add(date3);
        
        s3.getDays().add(date2);

        students.add(s1);
        students.add(s2);
        students.add(s3);

    }

    /**
     * Returns the students arraylist containing all the student mockdata
     * objects.
     *
     * @return a list containing all the student mockdata.
     */
    public List<Student> readAllStudents() {
        return students;
    }

    /**
     * Returns the specified student in the students arraylist.
     *
     * @return the specified student.
     */
    public Student readSpecificStudent(int number) {
        return students.get(number);
    }
    
}
