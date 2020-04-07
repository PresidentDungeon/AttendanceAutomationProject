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
import attendanceautomation.bll.PersonManager;
import attendanceautomation.bll.StudentManager;
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

    /**
     * If correct username and password is entered, the stored person will be returned.
     * @param username the username of the account
     * @param password the password of the account
     * @return 
     */
    public Person validateUser(String username, String password) {
        return personManager.validateUser(username, password);
    }

    /**
     * Searches for students matching the search term.
     * @param studentName The name of the student being searched for
     * @param classRoom The classroom being searched in
     * @param studentList The list of students the search should be based on
     * @return A list of all the students found
     */
    public ObservableList<Student> searchStudent(String studentName, Classroom classRoom, ObservableList<Student> studentList) {
        return studentManager.searchStudent(studentName, classRoom, studentList);
    }

    /**
     * Update or adds the date to the student based on whether or not a date is already saved for the current day.
     * @param studentID The ID of the student the date should be added or updated to
     * @param date The date to be added or updated
     */
    public void toBeAttending(int studentID, Date date) {
        studentManager.toBeAttending(studentID, date);
    }
    
    /**
     * Returns an observableList of all dates used by the student.
     * @param studentID The ID of the student being searched for
     * @return observableList of all the students dates
     */
    public ObservableList<Date> getStudentDays(int studentID)
    {
        return studentManager.getStudentDays(studentID);
    }
    
    /**
     * Updates the specified date in the database.
     * @param date the date that will update the previous date with the same ID
     */
    public void updateAttendance(Date date)
    {
        studentManager.updateAttendance(date);
    }
}
