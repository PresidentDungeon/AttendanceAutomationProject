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
public class StudentDBDAO implements IStudentDBDAO{

    
    /**
     * Returns the student based on the specified ID.
     * @param id the ID of the student
     * @return The student with the specified ID
     */
    @Override
    public Student getStudentById(int id) {
        
        Connection con = null;
        Student student = null;

        try{
            con = DBSettings.getInstance().getConnection();
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
        finally{
        DBSettings.getInstance().releaseConnection(con);
        }

        return student;
    }

    /**
     * Returns an observableList of all dates used by the student.
     * @param studentID The ID of the student being searched for
     * @return observableList of all the students dates
     */
    @Override
    public ObservableList<Date> getStudentDays(int studentID) {
        
        Connection con = null;
        ObservableList<Date> days = FXCollections.observableArrayList();

        try{
            con = DBSettings.getInstance().getConnection();
            String sql = "SELECT * FROM Attending WHERE Attending.persID = ? ORDER BY date ASC;";
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
        finally{
           DBSettings.getInstance().releaseConnection(con);
        }

        return days;
    }

    /**
     * Returns a list of all students in the specified class.
     * @param classroom The classroom containing the students.
     * @return A list of all persons in the classroom
     */
    @Override
    public List<Student> getStudentsInClass(Classroom classroom) {
       
        Connection con = null;
        List<Student> studentsInClass = new ArrayList<>();

        try{
            con = DBSettings.getInstance().getConnection();
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
        finally{
        DBSettings.getInstance().releaseConnection(con);
        }
        return studentsInClass;
        
    }
    
    /**
     * Adds the date to the specified students list of dates. 
     * @param studentID The ID of the student
     * @param date The date to be added
     * @return boolean determining wheter or not the method was successful
     */
    @Override
    public boolean attendance(int studentID, Date date) {
        
        Connection con = null;
        try{
            con = DBSettings.getInstance().getConnection();
            String sql = "INSERT INTO Attending (persID, isAttending, description, date) VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, studentID);
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
        finally{
            DBSettings.getInstance().releaseConnection(con);
        }
    }
    
    /**
     * Updates the specified date in the database.
     * @param date the date that will update the previous date with the same ID
     */
    @Override
    public boolean updateAbsence(Date date) {
        
        Connection con = null;
        try{
            con = DBSettings.getInstance().getConnection();
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
        finally{
        DBSettings.getInstance().releaseConnection(con);
        }
    }
    
    /**
     * Returns the Date object found for the current day.
     * @param studentID The ID of the student being searched for
     * @return If found, the date of the current day of the specified student
     */
    @Override
    public Date getDate(int studentID) {
        
        Connection con = null;
        Date date = null;
        
        try{
            con = DBSettings.getInstance().getConnection();
            String sql = "SELECT * FROM Attending WHERE PersID = ? AND date = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, studentID);
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
        finally{
        DBSettings.getInstance().releaseConnection(con);
        }
    }
    
}
