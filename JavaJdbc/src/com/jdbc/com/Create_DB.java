package com.jdbc.com;

import java.sql.Connection;
import java.sql.Statement;

public class Create_DB {
    public static void main(String args[]) {
        Connection connection = null;
        Statement statement = null;
        ConnectDB obj_ConnectDB = new ConnectDB();
        connection = obj_ConnectDB.get_connection();
        
        try {
            String query = "CREATE DATABASE demo";
            statement = connection.createStatement();
            int result = statement.executeUpdate(query);
            if (result == 0) {
                System.out.println("Database Created Successfully.");
            } else {
                System.out.println("Failed to create database.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
