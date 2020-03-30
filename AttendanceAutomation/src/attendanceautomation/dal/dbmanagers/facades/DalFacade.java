/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.facades;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Person;
import attendanceautomation.be.Roles;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.dal.dbmanagers.dbdao.IPersonDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.IStudentDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.ITeacherDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.PersonDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.StudentDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.TeacherDBDAO;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class DalFacade implements IDalFacade {

    private IPersonDBDAO personManager = new PersonDBDAO();
    private IStudentDBDAO studentManager = new StudentDBDAO();
    private ITeacherDBDAO teacherManager = new TeacherDBDAO();

    public Person login(String username, String password) {
        int userId;

        if (personManager.isLoginCorrect(username, password)) {
            userId = personManager.getPersonId(username, password);
            Roles role = personManager.getRoleById(userId);

            if (role == Roles.STUDENT) {
                return studentManager.getStudentById(userId);
            } else if (role == Roles.TEACHER) {
                Teacher teacher = teacherManager.getTeacherById(userId);

                for (Classroom c : teacher.getClassrooms()) {
                    c.setStudents(studentManager.getStudentsInClass(c));
                }

                return teacher;
            }
        }

        return null;

    }

    @Override
    public void toBeAttending(int studentID, Date date) {
        
        Date d = studentManager.getDate(studentID);
        
        if (d == null) {
            studentManager.attendance(studentID, date);
        } else {
            date.setId(d.getId());
            studentManager.updateAbsence(date);
        }
    }

    @Override
    public ObservableList<Date> getStudentDays(int studentID) {
        return studentManager.getStudentDays(studentID);
    }
    
    @Override
    public void updateAttendance(Date date)
    {
        studentManager.updateAbsence(date);
    }
}
