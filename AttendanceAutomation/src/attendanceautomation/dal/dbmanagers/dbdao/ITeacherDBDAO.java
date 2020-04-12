/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Teacher;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public interface ITeacherDBDAO {
    
    /**
     * Returns the teacher found based on the ID.
     * @param teacherID the ID of the teacher being searched for
     * @return The teacher found
     */
    public Teacher getTeacherById(int teacherID);
    
    /**
     * Returns a list of all classrooms that the teacher teaches.
     * @param teacherID The ID of the teacher being searched for.
     * @return A list of all classrooms that the entered teacher teaches.
     */
    public ObservableList<Classroom> getTeacherClasses(int teacherID);
    
    
}
