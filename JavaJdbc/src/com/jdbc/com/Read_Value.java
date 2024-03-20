package com.jdbc.com;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Read_Value {
	public static void main(String args[]) throws SQLException
	{
		Connection connection = null;
        Statement statement = null;
        ResultSet rs=null;
        ConnectDB obj_ConnectDB = new ConnectDB();
        connection = obj_ConnectDB.get_connection();
        try {
        	String query="select * from employee";
        	statement=connection.createStatement();
        	rs=statement.executeQuery(query);
        	while(rs.next())
        	{
        		System.out.print(rs.getString("sl_no"));
        		System.out.print(rs.getString("name"));
        		System.out.println(rs.getString("address"));
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
        	connection.close();
        }
	}

}
