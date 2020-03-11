/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbmanagers.dbdao;

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

        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Logins WHERE userName = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, userName);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String role = rs.getString("role");

                if (role.equalsIgnoreCase("student")) {
                    p = new Student();
                }
                if (role.equalsIgnoreCase("teacher")) {
                    p = new Teacher();
                }
                p.setId(id);
            }
            return p;

        } catch (SQLServerException ex) {

        } catch (SQLException ex) {

        }
        return null;

    }
}
