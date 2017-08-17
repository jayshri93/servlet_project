package com.simplewebapp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	 
	
	public static Connection getDatabaseConnection() throws ClassNotFoundException{
			Connection connection = null;
			String url = "jdbc:postgresql://localhost:5432/servletdb";
			String user = "postgres";
			String pwd = "jayu123";
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver found");
			try {
				 connection = DriverManager.getConnection(url,user,pwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return connection;
	}
}
