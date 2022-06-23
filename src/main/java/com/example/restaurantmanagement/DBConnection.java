package com.example.restaurantmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static  Connection connection = null;

    public static Connection getConnections() {
        if (connection != null) {
            System.out.println("Connected");
        }else {
            try {
                String dbURL = "jdbc:sqlserver://GALAXY:1433;encrypt=false;databaseName=Restaurant_Management;user=sa;password=123";
                Connection connection = DriverManager.getConnection(dbURL);
            } catch (SQLException ex) {
                System.err.println("Cannot connect database, " + ex);
            }
            return connection;
        }
        return null;
    }

    public static void closeConnections() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}



