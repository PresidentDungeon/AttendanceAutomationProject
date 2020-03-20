/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.facades;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Person;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import java.util.List;

/**
 *
 * @author ander
 */
public interface IDalFacade {
    
     public Person login(String username, String password);
     public List<Student> searchStudent(Teacher teacher, String studentName, Classroom classRoom);
     public void toBeAttending (Student student, Date date);
    
}
