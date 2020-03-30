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
 
    public Student getStudentById(int id);
    ObservableList<Date> getStudentDays(int studentID);
    public List<Student> getStudentsInClass(Classroom classroom);
    public boolean attendance(int studentID, Date date);
    public boolean updateAbsence(Date date);
    public Date getDate(int studentID);
    
}
