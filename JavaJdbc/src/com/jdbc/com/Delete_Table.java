package com.jdbc.com;

import java.sql.Connection;
import java.sql.Statement;

public class Delete_Table {
    public static void main(String args[]) {
        Connection connection = null;
        Statement statement = null;

        ConnectDB obj_ConnectDB = new ConnectDB();
        connection = obj_ConnectDB.get_connection();
        
        try {
            String query = "DROP TABLE student";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Deleted Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
