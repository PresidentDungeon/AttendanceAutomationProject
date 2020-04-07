/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.facades;

import attendanceautomation.be.Date;
import attendanceautomation.be.Person;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public interface IDalFacade {
    
    /**
     * Returns the person found by the entered username and password.
     * @param username The username of the person
     * @param password The password of the person
     * @return The person found based on the username and password
     */
     public Person login(String username, String password);
     
    /**
     * Update or adds the date to the student based on whether or not a date is already saved for the current day.
     * @param studentID The ID of the student the date should be added or updated to
     * @param date The date to be added or updated
     */
     public void toBeAttending (int studentID, Date date);
     
    /**
     * Returns an observableList of all dates used by the student.
     * @param studentID The ID of the student being searched for
     * @return observableList of all the students dates
     */
     public ObservableList<Date> getStudentDays(int studentID);
     
    /**
     * Updates the specified date in the database.
     * @param date the date that will update the previous date with the same ID
     */
     public void updateAttendance(Date date);
    
}
