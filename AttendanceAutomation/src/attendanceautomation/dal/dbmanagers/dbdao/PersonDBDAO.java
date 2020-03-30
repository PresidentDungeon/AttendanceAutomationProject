/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Roles;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import attendanceautomation.dal.dbaccess.DBSettings;

/**
 *
 * @author ander
 */
public class PersonDBDAO implements IPersonDBDAO {
    
    @Override
    public boolean isLoginCorrect(String userName, String password) {
        
        Connection con = null;
        try {
            con = DBSettings.getInstance().getConnection();
            String sql = "SELECT * FROM Logins WHERE username = ? AND password = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, userName);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLServerException ex) {
            
        } catch (SQLException ex) {
            
        } finally {
            DBSettings.getInstance().releaseConnection(con);
        }
        return false;
    }
    
    @Override
    public int getPersonId(String userName, String password) {
        
        Connection con = null;
        int id = 0;
        
        try {
            con = DBSettings.getInstance().getConnection();
            String sql = "SELECT * FROM Logins WHERE username = ? AND password = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, userName);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("persID");
            }
            
            return id;
            
        } catch (SQLServerException ex) {
            
        } catch (SQLException ex) {
            
        } finally {
            DBSettings.getInstance().releaseConnection(con);
        }
        
        return -1;
        
    }
    
    @Override
    public Roles getRoleById(int id) {
        
        Connection con = null;
        
        try {
            con = DBSettings.getInstance().getConnection();
            String sql = "SELECT isStudent FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                
                boolean isStudent = rs.getBoolean("isStudent");
                
                if (isStudent == true) {
                    return Roles.STUDENT;
                } else {
                    
                    return Roles.TEACHER;
                }
            }
        } catch (SQLServerException ex) {
            
        } catch (SQLException ex) {
        } finally {
            DBSettings.getInstance().releaseConnection(con);
        }
        
        return null;
    }
    
}
