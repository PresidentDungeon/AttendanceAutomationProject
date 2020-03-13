/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

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
    
}
