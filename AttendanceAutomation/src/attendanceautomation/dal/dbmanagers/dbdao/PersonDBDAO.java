/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Person;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.dal.DBAccess.DBSettings;

/**
 *
 * @author ander
 */
public class PersonDBDAO {

    private DBSettings dbs;

    public PersonDBDAO() {
        try {
            dbs = new DBSettings();
        } catch (IOException e) {

        }
    }

    public Person login(String userName, String password) {

        Person p = null;
        int id = 0;

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Logins WHERE userName = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("Id");
            }

            if (id != 0) {
                p = returnPersonById(id);
            }

            return p;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        return p;

    }

    public Person returnPersonById(int id) {

        Person p = null;

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT role FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                boolean isStudent = rs.getBoolean("role");

                if (isStudent = true) {

                    return getStudentById(id);

                } else {

                    return getTeacherById(id);

                }

            }

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        return p;

    }

    public Student getStudentById(int id) {
        Student student = null;
        ObservableList<Date> days = FXCollections.observableArrayList();

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate lastView = LocalDate.parse(rs.getString("lastAccess"));

                sql = "SELECT * FROM Attending WHERE Attending.persID = ?;";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    int dateId = rs.getInt("ID");
                    boolean isAttending = rs.getBoolean("isAttending");
                    String description = rs.getString("description");
                    LocalDate localDate = LocalDate.parse(rs.getString("date"));

                    Date date = new Date(localDate, isAttending, description);
                    date.setId(dateId);
                    days.add(date);
                }

                student = new Student(name, email);
                student.setId(id);
                student.setLastAccess(lastView);
                student.setDays(days);

            }

            return student;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }

        return student;
    }

    public Teacher getTeacherById(int id) {

        Teacher teacher = null;
        ObservableList<Classroom> classes = FXCollections.observableArrayList();

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate lastView = LocalDate.parse(rs.getString("lastAccess"));

                sql = "SELECT FROM TeacherClass JOIN Class on TeacherClass.classID = Class.ID WHERE TeacherClass.persID = ?;";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();

                while (rs.next()) {

                    int classID = rs.getInt("classID");
                    String className = rs.getString("Name");

                    Classroom c = new Classroom(className);
                    c.setId(classID);
                    c.setStudents(getStudentsInClass(classID));
                    classes.add(c);

                }

                teacher = new Teacher(name, email);
                teacher.setId(id);
                teacher.setLastAccess(lastView);
                teacher.setClasses(classes);

            }

            return teacher;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }

        return teacher;

    }

    public List<Student> getStudentsInClass(int ClassId) {
        List<Student> studentsInClass = new ArrayList<>();

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM StudentClass WHERE classID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, ClassId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int studentId = rs.getInt("persID");
                studentsInClass.add(getStudentById(studentId));
            }

            return studentsInClass;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        return studentsInClass;

    }

    
    
    public static void main(String[] args) {
        
        PersonDBDAO test = new PersonDBDAO();
        
        Person p = test.returnPersonById(2);
        System.out.println(p.getName());
        
    }
    
}
