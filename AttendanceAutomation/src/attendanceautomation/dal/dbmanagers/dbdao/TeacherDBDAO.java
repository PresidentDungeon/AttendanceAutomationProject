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
public class TeacherDBDAO implements ITeacherDBDAO{


    /**
     * Returns the teacher found based on the ID.
     * @param teacherID the ID of the teacher being searched for
     * @return The teacher found
     */
    @Override
    public Teacher getTeacherById(int teacherID) {

        Connection con = null;
        Teacher teacher = null;

        try{
            con = DBSettings.getInstance().getConnection();
            String sql = "SELECT * FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, teacherID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate lastView = LocalDate.parse(rs.getString("lastAccess"));

                teacher = new Teacher(name, email);
                teacher.setId(teacherID);
                teacher.setLastAccess(lastView);
                teacher.setClasses(getTeacherClasses(teacherID));

            }

            return teacher;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        finally{
        DBSettings.getInstance().releaseConnection(con);
        }

        return teacher;

    }
    
    /**
     * Returns a list of all classrooms that the teacher teaches in.
     * @param teacherID The ID of the teacher being searched for.
     * @return A list of all classrooms that the entered teacher teaches.
     */
    @Override
    public ObservableList<Classroom> getTeacherClasses(int teacherID) {
        
        Connection con = null;
        ObservableList<Classroom> classes = FXCollections.observableArrayList();
        try{
            con = DBSettings.getInstance().getConnection();
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
        finally{
        DBSettings.getInstance().releaseConnection(con);
        }
        return classes;
    }
    
}
