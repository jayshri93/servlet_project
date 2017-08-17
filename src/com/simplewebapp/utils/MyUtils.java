package com.simplewebapp.utils;

import java.net.CookieStore;
import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplewebapp.beans.UserAccount;

public class MyUtils {
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	public static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	
	//store connection
	public static void storeConnection(ServletRequest request,Connection con){
		request.setAttribute(ATT_NAME_CONNECTION, con);
	}
	// get stored connection
	public static Connection getStoredConnection(ServletRequest request){
		Connection con = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		return con;
	}
	
	// store user info n session
	public static void storeLoginUser(HttpSession session,UserAccount loginedUser){
		session.setAttribute("loginedUser", loginedUser);
	}
	
	// get the user information stored in the session
	public static UserAccount getLoginedUser(HttpSession session){
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		return loginedUser;
	}
	
	//store info in cookie
	public static void storeUserToCookie(HttpServletResponse response,UserAccount user){
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
		// store it for 1 day
		cookieUserName.setMaxAge(24*60*60);
		response.addCookie(cookieUserName);
	}
	
	//Get from cookie
	public static String getUserNameInCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie:cookies){
				if(ATT_NAME_USER_NAME.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	// Delete cookie
	public static void deleteUserCookie(HttpServletResponse response){
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}
}
