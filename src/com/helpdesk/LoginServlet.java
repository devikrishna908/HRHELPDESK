package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String per_name;
		
		if (username == null || username.isEmpty() || pwd == null || pwd.isEmpty()) {
			
            pw.println("<script>alert('Username and Password can not be null');" 
                   		+ "window.history.back();"
            		+ "</script>");
            return;
        }
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
		//	ps = con.prepareStatement("SELECT * FROM user_login_details  WHERE  user_username=? AND user_password =?");
			ps = con.prepareStatement("SELECT up.user_per_id,up.user_per_name,ul.user_role "
					+ "FROM user_login_details ul, users_personal_details up  "
					+ "WHERE up.user_per_id=ul.user_per_id "
					+ "AND user_username=? AND user_password =?");
			ps.setString(1, username);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next()){
				per_id = rs.getInt(1);
				per_name = rs.getString(2);
				
//				pw.println(per_id);
				if(rs.getString(3).equalsIgnoreCase("HR Head")){

					HttpSession session = request.getSession();
					session.setAttribute("per_id", per_id);
					session.setAttribute("per_name", per_name);
					response.sendRedirect("hr-head-home.html?per_name=" + per_name);
				}
				if(rs.getString(3).equalsIgnoreCase("HR")){
					
					HttpSession session = request.getSession();
					session.setAttribute("per_id", per_id);
					session.setAttribute("per_name", per_name);
					response.sendRedirect("hr-home.html?per_name=" + per_name);
				}
				if(rs.getString(3).equalsIgnoreCase("Employee")){
					
					HttpSession session = request.getSession();
					session.setAttribute("per_id", per_id);
					session.setAttribute("per_name", per_name);
					response.sendRedirect("employee-home.html?per_name=" + per_name);
				}
			}else{
			    pw.println("<script>");
			    pw.println("alert('Invalid login credentials');");
			    pw.println("window.history.back();"); // Go back to the previous page
			    pw.println("</script>");
			}

		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
