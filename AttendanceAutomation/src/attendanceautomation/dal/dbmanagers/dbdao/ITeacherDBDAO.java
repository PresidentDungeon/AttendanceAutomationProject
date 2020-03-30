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
    
    public Teacher getTeacherById(int teacherID);
    public ObservableList<Classroom> getTeacherClasses(int teacherID);
    
    
}
