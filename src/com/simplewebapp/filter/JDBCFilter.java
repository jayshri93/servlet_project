package com.simplewebapp.filter;

import java.io.IOException;
import java.sql.Connection;

import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;



import com.simplewebapp.conn.ConnectionUtils;
import com.simplewebapp.utils.MyUtils;

@WebFilter(filterName="jdbcFilter",urlPatterns={"/*"})
public class JDBCFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	private boolean needJDBC(HttpServletRequest request){
		System.out.println("JDBC Filter");
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String urlPattern = servletPath;
		if(pathInfo != null){
			urlPattern = servletPath+"/*";
			
		}
		Map<String,?extends ServletRegistration> servletRegistration = request.getServletContext()
				.getServletRegistrations();
		Collection<? extends ServletRegistration> values = servletRegistration.values();
		for(ServletRegistration sr : values ){
			Collection<String> mappings = sr.getMappings();
			if(mappings.contains(urlPattern)){
				return true;
			}
		}
		return false;
	}
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws ServletException, IOException{
		HttpServletRequest req = (HttpServletRequest) request;
		if(this.needJDBC(req)){
			System.out.println("Open Connection for: "+req.getServletPath());
			Connection con = null;
			try {
				// create connection
				con = ConnectionUtils.getConnection();
				// set auto commit false
				con.setAutoCommit(false);
				// store connection in attribute of request
				MyUtils.storeConnection(request, con);
				//go to next filter
				chain.doFilter(request, response);
				//commit change
				con.commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				ConnectionUtils.rollBackQuietly(con);
				
			}finally {
				ConnectionUtils.closeQuietly(con);
			}
			
			
		}else {
			chain.doFilter(request, response);
		}
	}

}
