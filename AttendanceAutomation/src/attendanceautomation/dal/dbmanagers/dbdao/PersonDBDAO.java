/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

import attendanceautomation.be.Roles;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import attendanceautomation.dal.dbaccess.DBSettings;
import java.time.LocalDate;

/**
 *
 * @author ander
 */
public class PersonDBDAO implements IPersonDBDAO {
    
    /**
     * Returns a boolean based on whether the login information given is valid or not.
     * @param userName The username of the account
     * @param password The password of the account
     * @return boolean value representing whether or not the account is active
     */
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
    
    /**
     * Returns the ID of the person found with the given login information.
     * @param userName The username of the account
     * @param password The password of the account
     * @return ID of the person based on the entered login
     */
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
    
    /**
     * Returns the role of the person containing a specific ID.
     * @param id The ID of the person being searched for
     * @return The role of the person entered
     */
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

    /**
     * Updates the last login on the person entered.
     * @param personID The ID of the person logging in.
     */
    @Override
    public void updateLastLogin(int personID) {
        
         Connection con = null;
        
        try {
            con = DBSettings.getInstance().getConnection();
            String sql = "UPDATE Person SET lastAccess = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, LocalDate.now().toString());
            stmt.setInt(2, personID);
            
            stmt.execute();

        } catch (SQLServerException ex) {
            
        } catch (SQLException ex) {
        } finally {
            DBSettings.getInstance().releaseConnection(con);
        }
    }
    
}
