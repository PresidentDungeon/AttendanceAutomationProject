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
import attendanceautomation.dal.dbmanagers.dbdao.PersonDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.StudentDBDAO;
import attendanceautomation.dal.dbmanagers.dbdao.TeacherDBDAO;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class DalFacade implements IDalFacade {

    private PersonDBDAO personManager = new PersonDBDAO();
    private StudentDBDAO studentManager = new StudentDBDAO();
    private TeacherDBDAO teacherManager = new TeacherDBDAO();

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
    public List<Student> searchStudent(Teacher teacher, String studentName, Classroom classRoom) {

        ObservableList<Student> searchedStudents = FXCollections.observableArrayList();

        if (classRoom.getId() == 0) {
            for (Classroom c : teacher.getClassrooms()) {
                searchedStudents.addAll(studentManager.searchStudent(studentName, c));
            }
        } else {
            searchedStudents = studentManager.searchStudent(studentName, classRoom);
        }
        return searchedStudents;
    }

    @Override
    public void toBeAttending(Student student, Date date) {
        
        Date d = studentManager.getDate(student);
        
        if (d == null) {
            studentManager.attendance(student, date);
        } else {
            date.setId(d.getId());
            studentManager.updateAbsence(date);
        }
    }

    public ObservableList<Date> getStudentDays(Student student) {
        return studentManager.getStudentDays(student.getId());
    }
    
}
