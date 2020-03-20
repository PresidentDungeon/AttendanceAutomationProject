/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import attendanceautomation.dal.dbaccess.DBSettings;
import java.sql.Statement;

/**
 *
 * @author ander
 */
public class StudentDBDAO {

    private DBSettings dbs;

    public StudentDBDAO() {
        try {
            dbs = new DBSettings();
        } catch (IOException e) {

        }
    }

    public Student getStudentById(int id) {
        Student student = null;

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate lastView = LocalDate.parse(rs.getString("lastAccess"));

                student = new Student(name, email);
                student.setId(id);
                student.setLastAccess(lastView);
                student.setDays(getStudentDays(id));

            }

            return student;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }

        return student;
    }

    public ObservableList<Date> getStudentDays(int studentID) {
        ObservableList<Date> days = FXCollections.observableArrayList();

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Attending WHERE Attending.persID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int dateId = rs.getInt("ID");
                boolean isAttending = rs.getBoolean("isAttending");
                String description = rs.getString("description");
                LocalDate localDate = LocalDate.parse(rs.getString("date"));

                Date date = new Date(localDate, isAttending, description);
                date.setId(dateId);
                days.add(date);
            }

            return days;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }

        return days;
    }

    public List<Student> getStudentsInClass(Classroom classroom) {
        List<Student> studentsInClass = new ArrayList<>();

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM StudentClass WHERE classID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, classroom.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("persID");
                Student student = getStudentById(studentId);
                student.getClassRoom().add(classroom);
                studentsInClass.add(student);
            }

            return studentsInClass;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        return studentsInClass;
        
    }
    
    public boolean attendance(Student student, Date date) {
        try (Connection con = dbs.getConnection()) {
            
            String sql = "INSERT INTO Attending (persID, isAttending, description, date) VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, student.getId());
            stmt.setBoolean(2, date.isAttendance());
            stmt.setString(3, date.getDescription());
            stmt.setString(4, date.getDate().toString());
            
            ResultSet rs = stmt.executeQuery();
            
            return true;
        } catch (SQLServerException ex) {
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateAbsence(Date date) {
        try (Connection con = dbs.getConnection()) {
            
            String sql = "UPDATE Attending SET isAttending = ?, description = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setBoolean(1, date.isAttendance());
            stmt.setString(2, date.getDescription());
            stmt.setInt(3, date.getId());
            
            ResultSet rs = stmt.executeQuery();
            
            return true;
        } catch (SQLServerException ex) {
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public Date getDate(Student student) {
        Date date = null;
        
        try (Connection con = dbs.getConnection()) {
            
            String sql = "SELECT * FROM Attending WHERE PersID = ? AND date = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, student.getId());
            stmt.setString(2, LocalDate.now().toString());
       
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int Id = rs.getInt("ID");
                boolean isAttending = rs.getBoolean("isAttending");
                String description = rs.getString("description");
             
                date = new Date(LocalDate.now(), isAttending, description);
                date.setId(Id);
            }
            
            return date;
        } catch (SQLServerException ex) {
            return date;
        } catch (SQLException ex) {
            return date;
        }
    }

    public ObservableList<Student> searchStudent(String studentName, Classroom classroom) {
        ObservableList<Student> searchedStudents = FXCollections.observableArrayList();


        try (Connection con = dbs.getConnection()) {
            
            String sql = "SELECT StudentClass.PersID FROM StudentClass JOIN Person on StudentClass.persID = Person.ID WHERE Person.name LIKE ? AND StudentClass.classID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, "%" + studentName + "%");
            stmt.setInt(2, classroom.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("persID");
                searchedStudents.add(getStudentById(studentId));
            }

            return searchedStudents;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        return searchedStudents;

    }

    public static void main(String[] args) {
        StudentDBDAO d = new StudentDBDAO();
     
        Student s = new Student("Peter", "dadad");
        s.setId(1);
    }
    
}
