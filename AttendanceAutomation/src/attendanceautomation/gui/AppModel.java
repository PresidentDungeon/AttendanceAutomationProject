/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Person;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.bll.PersonManager;
import attendanceautomation.bll.StudentManager;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class AppModel {

    private final PersonManager personManager;
    private final StudentManager studentManager;

    public AppModel() {
        personManager = new PersonManager();
        studentManager = new StudentManager();
    }

    public Person validateUser(String username, String password) {
        return personManager.validateUser(username, password);
    }

    public ObservableList<Student> searchStudent(String studentName, Classroom classRoom, ObservableList<Student> studentList) {
        return studentManager.searchStudent(studentName, classRoom, studentList);
    }

    public void toBeAttending(int studentID, Date date) {
        studentManager.toBeAttending(studentID, date);
    }
    
    public ObservableList<Date> getStudentDays(int studentID)
    {
        return studentManager.getStudentDays(studentID);
    }
    
    public void updateAttendance(Date date)
    {
        studentManager.updateAttendance(date);
    }
}
