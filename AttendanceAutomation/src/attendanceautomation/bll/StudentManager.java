/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.dal.dbmanagers.facades.DalFacade;
import java.util.List;

/**
 *
 * @author ander
 */
public class StudentManager {
    
    private DalFacade dalFacade;

    public StudentManager() {
        dalFacade = new DalFacade();
    }
    
    public List<Student> searchStudent(Teacher teacher, String studentName, Classroom classRoom)
    {
        return dalFacade.searchStudent(teacher, studentName, classRoom);
    }
    
    
    
}
