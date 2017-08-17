package com.simplewebapp.utils;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.simplewebapp.beans.Product;
import com.simplewebapp.beans.UserAccount;

public class DBUtils {
	public static  UserAccount findUser(Connection con,String userName,String password) throws SQLException{
		String sql = "select a.user_name,a.password, a.gender from user_account a"+
					" where a.user_name=? and a.password=?";
		PreparedStatement pstmt = con.prepareStatement(sql); 
		pstmt.setString(1,userName);
		pstmt.setString(2, password);
		ResultSet rSet = pstmt.executeQuery();
		if(rSet.next()){
			String gender = rSet.getString("gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}
	public static  UserAccount findUser(Connection con,String userName) throws SQLException{
		String sql = "select a.user_name,a.password, a.gender from user_account a"+
					" where a.user_name=?";
		PreparedStatement pstmt = con.prepareStatement(sql); 
		pstmt.setString(1,userName);
	
		ResultSet rSet = pstmt.executeQuery();
		if(rSet.next()){
			String gender = rSet.getString("gender");
			String password = rSet.getString("password");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}
	
	public static ArrayList<Product> queryProduct(Connection con) throws SQLException{
		String sql = "select a.code,a.name,a.price from product a";
		PreparedStatement psmt = con.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		ArrayList<Product> list = new ArrayList<Product>();
		if(rs.next()){
			String code = rs.getString("code");
			String name = rs.getString("name");
			float price = rs.getFloat("price");
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			list.add(product);
		}
		return list;
	}
	public static Product findProduct(Connection con,String code) throws SQLException{
		String sql = "Select * from product where code=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1,code);
		ResultSet rs = psmt.executeQuery();
		if(rs.next()){
			String name = rs.getString("name");
			float price = rs.getFloat("price");
			Product product = new Product();
			product.setName(name);
			product.setPrice(price);
			return product;
		}
		return null;
	}
	
	public static void updateProduct(Connection con, Product product) throws SQLException{
		String sql = "Update Product set Name=?,Price=?, where Code=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, product.getName());
		psmt.setFloat(2, product.getPrice());
		psmt.setString(3, product.getCode());
		psmt.executeUpdate(sql);
	}
	
	public static void deleteProduct(Connection con,String code) throws SQLException{
		String sql="delete product where code=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1,code);
		psmt.executeUpdate(sql);
	}
	
	public static void insertProduct(Connection con,Product product) throws SQLException{
		String sql = "Insert into product(code,name,price) values (?,?,?)";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, product.getCode());
		psmt.setString(2, product.getName());
		psmt.setFloat(3, product.getPrice());
		psmt.executeUpdate(sql);
	}
}
