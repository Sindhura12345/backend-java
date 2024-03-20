package com.jdbc.com;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	public static void main(String args[])
	{
		ConnectDB obj_ConnectDB=new ConnectDB();
		System.out.println(obj_ConnectDB.get_connection());
	}
	
	
    public Connection get_connection() {
        Connection connection = null;
        String host = "localhost";
        String port = "5432";
        String db_name = "db";
        String username = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name, username, password);
            if (connection != null) {
                System.out.println("Connection established successfully.");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
		return connection;
    }
}
