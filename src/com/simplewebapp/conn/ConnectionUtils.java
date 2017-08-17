package com.simplewebapp.conn;

import java.sql.Connection;


public class ConnectionUtils {
	public static Connection getConnection() throws ClassNotFoundException{
		return DBConnection.getDatabaseConnection();
	}
	
	public static void closeQuietly(Connection con){
		try {
			con.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void rollBackQuietly(Connection con){
		try {
			con.rollback();
		} catch (Exception e) {
			
		}
	}
}
