/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public interface IStudentDBDAO {
 
    /**
     * Returns the student based on the specified ID.
     * @param id the ID of the student
     * @return The student with the specified ID
     */
    public Student getStudentById(int id);
    
    /**
     * Returns an observableList of all dates used by the student.
     * @param studentID The ID of the student being searched for
     * @return observableList of all the students dates
     */
    public ObservableList<Date> getStudentDays(int studentID);
    
    /**
     * Returns a list of all students in the specified class.
     * @param classroom The classroom containing the students.
     * @return A list of all persons in the classroom
     */
    public List<Student> getStudentsInClass(Classroom classroom);
    
    /**
     * Adds the date to the specified students list of dates. 
     * @param studentID The ID of the student
     * @param date The date to be added
     * @return boolean determining wheter or not the method was successful
     */
    public boolean attendance(int studentID, Date date);
    
    /**
     * Updates the specified date in the database.
     * @param date the date that will update the previous date with the same ID
     */
    public boolean updateAbsence(Date date);
    
    /**
     * Returns the Date object found for the current day.
     * @param studentID The ID of the student being searched for
     * @return If found, the date of the current day of the specified student
     */
    public Date getDate(int studentID);
    
}
