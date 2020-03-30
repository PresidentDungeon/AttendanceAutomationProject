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
public class PersonDBDAO implements IPersonDBDAO{

    private DBSettings dbs;

    public PersonDBDAO() {
        try {
            dbs = new DBSettings();
        } catch (IOException e) {

        }
    }

    @Override
    public boolean isLoginCorrect(String userName, String password) {
        
        try (Connection con = dbs.getConnection()){ 
            String sql = "SELECT * FROM Logins WHERE username = ? AND password = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        
        return false;

    }

    @Override
    public int getPersonId(String userName, String password) {

        int id = 0;
        
        try  (Connection con = dbs.getConnection()){
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

        }
        
        return -1;

    }

    @Override
    public Roles getRoleById(int id) {

        try (Connection con = dbs.getConnection()) {
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

        }
        
        return null;
    }

}
