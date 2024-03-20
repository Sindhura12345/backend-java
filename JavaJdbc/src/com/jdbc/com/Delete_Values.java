package com.jdbc.com;

import java.sql.Connection;
import java.sql.Statement;

public class Delete_Values {
	public static void main(String args[])
	{
		Connection connection = null;
        Statement statement = null;
        
        ConnectDB obj_ConnectDB = new ConnectDB();
        connection=obj_ConnectDB.get_connection();
        try {
        	String query="delete from employee where sl_no='3'";
        	statement=connection.createStatement();
        	statement.executeUpdate(query);
        	System.out.println("Deletion Successful");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}

}
