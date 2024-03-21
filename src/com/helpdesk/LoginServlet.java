package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		int per_id;
		
		if (username == null || username.isEmpty() || pwd == null || pwd.isEmpty()) {
			
            pw.println("<script>alert('Username and Password can not be null');" 
                   		+ "window.history.back();"
            		+ "</script>");
            return;
        }
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
			ps = con.prepareStatement("SELECT * FROM user_login_details  WHERE  user_username=? AND user_password =?");
			ps.setString(1, username);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next()){
				per_id = rs.getInt(2);
				
//				pw.println(per_id);
				if(rs.getString(5).equalsIgnoreCase("HR Head")){

					ServletContext context = getServletContext();
				    context.setAttribute("per_id", per_id);				
					response.sendRedirect("hr-head-home.html");
				}
				if(rs.getString(5).equalsIgnoreCase("HR")){
					
					ServletContext context = getServletContext();
				    context.setAttribute("per_id", per_id);	
					response.sendRedirect("hr-home.html");
				}
				if(rs.getString(5).equalsIgnoreCase("Employee")){
					
					ServletContext context = getServletContext();
				    context.setAttribute("per_id", per_id);				
					response.sendRedirect("employee-home.html");
				}
			}else{
				pw.println("Invalid login credentials");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
                		+ " }, 1500);");
                pw.println("</script>");
			}

		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
