/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.facades;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Person;
import attendanceautomation.be.Roles;
import attendanceautomation.be.Teacher;
import attendanceautomation.dal.dbmanagers.dbdao.PersonDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.StudentDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.TeacherDBDAO;

/**
 *
 * @author ander
 */
public class DalFacade implements IDalFacade{
    
    private PersonDBDAO personManager = new PersonDBDAO();
    private StudentDBDAO studentManager = new StudentDBDAO();
    private TeacherDBDAO teacherManager = new TeacherDBDAO();
    
    public Person login(String username, String password) {
        int userId;
        
        if (personManager.isLoginCorrect(username, password)) {
            userId = personManager.getPersonId(username, password);
            Roles role = personManager.returnRoleById(userId);
            
            if (role == Roles.STUDENT) {
                return studentManager.getStudentById(userId);
            } else if (role == Roles.TEACHER) {
                Teacher teacher = teacherManager.getTeacherById(userId);
                
                for (Classroom c : teacher.getClassrooms()) {
                    
                    c.setStudents(studentManager.getStudentsInClass(c.getId()));      
                }
                
                return teacher;
            }
        }
        
        return null;
        
    }
    
}
