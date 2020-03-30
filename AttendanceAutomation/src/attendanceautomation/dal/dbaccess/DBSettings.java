/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal.dbaccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Bruger
 */
public class DBSettings {

    private static DBSettings instance;
    private SQLServerDataSource dataSource;
    private List<Connection> connections = new CopyOnWriteArrayList<>();
    private Deque<Connection> releasedConnections = new ArrayDeque();

    /**
     * Sets the user credentials, ip, and port configuration
     *
     * @throws IOException
     */
    private DBSettings() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("DBSettings.txt"));
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        dataSource.setServerName(props.getProperty("server"));
    }

    public static DBSettings getInstance() {
        if (instance == null) {
            try {
                instance = new DBSettings();
            } catch (IOException e) {

            }
        }
        return instance;
    }

    /**
     * establishes a connection to the database
     *
     * @return a Connection to the database
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {


        
        Connection connection = null;

        if (releasedConnections.isEmpty()) {
            connection = dataSource.getConnection();
            connections.add(connection);
        } else {
            connection = releasedConnections.poll();
            connections.add(connection);
        }
        System.out.println("Connections in use: " + connections.size() + "\n");
        System.out.println("Connections in storage: " + releasedConnections.size() + "\n");
        
        return connection;
    }

    public void releaseConnection(Connection connection) {
        connections.remove(connection);
        releasedConnections.add(connection);
        System.out.println("Connections in use: " + connections.size() + "\n");
        System.out.println("Connections in storage: " + releasedConnections.size() + "\n");
    }

    public void closeAllConnections() {
      
        System.out.println("Closing connections... Current size: " + connections.size() + releasedConnections.size());
        
        try {

            for (Connection connection : connections) {
                connection.close();
            }

            for (Connection connection : releasedConnections) {
                connection.close();
            }

            connections.clear();
            releasedConnections.clear();
            
                    System.out.println("Connections closed Current size: " + connections.size() + releasedConnections.size());

        } catch (SQLException ex) {
        }
    }

}
