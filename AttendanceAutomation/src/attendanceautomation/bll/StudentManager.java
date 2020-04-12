/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.dal.dbmanagers.facades.DalFacade;
import attendanceautomation.dal.dbmanagers.facades.IDalFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class StudentManager {

    private IDalFacade dalFacade;

    public StudentManager() {
        dalFacade = new DalFacade();
    }

    /**
     * Searches for students matching the search term.
     * @param studentName The name of the student being searched for
     * @param classRoom The classroom being searched in
     * @param studentList The list of students the search should be based on
     * @return A list of all the students found
     */
    public ObservableList<Student> searchStudent(String studentName, Classroom classRoom, ObservableList<Student> studentList) {
        
        ObservableList<Student> searchStudentList = FXCollections.observableArrayList();

        if (classRoom.getId() == 0) {
            for (Student student : studentList) {
                if (student.getName().toLowerCase().contains(studentName.toLowerCase())) {
                    searchStudentList.add(student);
                }
            }
        }
        
        else {
            for (Student student : studentList) {

                if (student.getName().toLowerCase().contains(studentName.toLowerCase())) {
                    for (Classroom c : student.getClassRoom()) {
                        if (c.getId() == classRoom.getId()) {
                            searchStudentList.add(student);
                            break;
                        }
                    }
                }
            }
        }
        return searchStudentList;
    }
    
    /**
     * Update or adds the date to the student based on
     * whether or not a date is already saved for the current day.
     * @param studentID The ID of the student the date should be added or updated to
     * @param date The date to be added or updated
     */
    public void toBeAttending (int studentID, Date date)
    {
        dalFacade.toBeAttending(studentID, date);
    }
    
    /**
     * Returns an observableList of all dates used by the student.
     * @param studentID The ID of the student being searched for
     * @return observableList of all the students dates
     */
     public ObservableList<Date> getStudentDays(int studentID)
    {
        return dalFacade.getStudentDays(studentID);
    }

    /**
     * Updates the specified date in the database.
     * @param date the date that will update the previous date with the same ID
     */
     public void updateAttendance(Date date)
     {
         dalFacade.updateAttendance(date);
     }
}
