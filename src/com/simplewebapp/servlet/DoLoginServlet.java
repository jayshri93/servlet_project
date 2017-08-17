package com.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplewebapp.beans.UserAccount;
import com.simplewebapp.utils.DBUtils;
import com.simplewebapp.utils.MyUtils;

@WebServlet("/doLogin")
public class DoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DoLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String rememberMeString = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeString);
		
		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;
		if(userName == null || password == null ||userName.length() == 0 || password.length() == 0){
			hasError = true;
			errorString = "Require username and password";
			
		}else{
			Connection con = MyUtils.getStoredConnection(request);
			try {
				user = DBUtils.findUser(con, userName, password);
				if(user == null){
					hasError = true;
					errorString = "User Name or Password invalid";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				hasError = true;
				errorString=e.getMessage();
			}
			
		}
		// if error forward to loginView.jsp
		if(hasError){
			user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			dispatcher.forward(request, response);
			
		}
		// if no error
		// store user info and redirect to user info page
		else{
			HttpSession session = request.getSession();
			MyUtils.storeLoginUser(session, user);
			
			//if remember me
			if(remember){
				MyUtils.storeUserToCookie(response, user);
			}else{
				MyUtils.deleteUserCookie(response);
			}
			
			response.sendRedirect(request.getContextPath()+"/userInfo");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
