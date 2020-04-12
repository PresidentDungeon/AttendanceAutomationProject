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

    /**
     * Returns the person found by the entered username and password.
     * @param username The username of the person
     * @param password The password of the person
     * @return The person found based on the username and password
     */
    @Override
    public Person login(String username, String password) {
        int userId;

        if (personManager.isLoginCorrect(username, password)) {
            userId = personManager.getPersonId(username, password);
            Roles role = personManager.getRoleById(userId);
            personManager.updateLastLogin(userId);

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

    /**
     * Updates or adds the date to the student based on whether
     * or not a date is already saved for the current day.
     * @param studentID The ID of the student the date should be added or updated to
     * @param date The date to be added or updated
     */
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

    /**
     * Returns an observableList of all dates used by the student.
     * @param studentID The ID of the student being searched for
     * @return observableList of all the students dates
     */
    @Override
    public ObservableList<Date> getStudentDays(int studentID) {
        return studentManager.getStudentDays(studentID);
    }
    
    /**
     * Updates the specified date in the database.
     * @param date the date that will update the previous date with the same ID
     */
    @Override
    public void updateAttendance(Date date)
    {
        studentManager.updateAbsence(date);
    }
}
