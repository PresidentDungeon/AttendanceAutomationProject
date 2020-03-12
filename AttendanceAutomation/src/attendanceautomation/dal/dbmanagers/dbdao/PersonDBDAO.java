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

    public boolean isLoginCorrect(String userName, String password) {

        try (Connection con = dbs.getConnection()) {
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

    public int getPersonId(String userName, String password) {

        int id = 0;

        try (Connection con = dbs.getConnection()) {
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

    public Roles returnRoleById(int id) {

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT role FROM Person WHERE Person.ID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                boolean isStudent = rs.getBoolean("role");

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
