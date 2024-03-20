package com.jdbc.com;

import java.sql.Connection;

import java.sql.Statement;

public class Edit_Value {
	public static void main(String args[])
	{
		Connection connection = null;
        Statement statement = null;
        
        ConnectDB obj_ConnectDB = new ConnectDB();
        connection=obj_ConnectDB.get_connection();
        try {
        	String query="update employee set name='ramu' where sl_no='1'";
        	statement=connection.createStatement();
        	statement.executeUpdate(query);
        	System.out.println("Update Done Successful");
        	}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}

}
