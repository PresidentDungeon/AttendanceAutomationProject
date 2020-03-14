/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Teacher;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import attendanceautomation.dal.dbaccess.DBSettings;

/**
 *
 * @author ander
 */
public class TeacherDBDAO {

    private DBSettings dbs;

    public TeacherDBDAO() {
        try {
            dbs = new DBSettings();
        } catch (IOException e) {

        }
    }

    public Teacher getTeacherById(int id) {

        Teacher teacher = null;

        try (Connection con = dbs.getConnection()) {
            
            String sql = "SELECT * FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate lastView = LocalDate.parse(rs.getString("lastAccess"));

                teacher = new Teacher(name, email);
                teacher.setId(id);
                teacher.setLastAccess(lastView);
                teacher.setClasses(getTeacherClasses(id));

            }

            return teacher;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }

        return teacher;

    }
    
    public ObservableList<Classroom> getTeacherClasses(int teacherID) {
        
        ObservableList<Classroom> classes = FXCollections.observableArrayList();
        try (Connection con = dbs.getConnection()) {
            
            String sql = "SELECT * FROM TeacherClass JOIN Class on TeacherClass.classID = Class.ID WHERE TeacherClass.persID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, teacherID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                int classID = rs.getInt("classID");
                String className = rs.getString("Name");

                Classroom c = new Classroom(className);
                c.setId(classID);
                classes.add(c);

            }
            
            return classes;
            
        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        
        return classes;
    }
    
}
