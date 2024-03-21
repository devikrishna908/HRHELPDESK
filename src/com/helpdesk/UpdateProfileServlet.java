package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateUser")
public class UpdateProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement ps = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk", "root", "mysql");

			int pId = Integer.parseInt(request.getParameter("per_id"));
			String per_name = request.getParameter("name");
			String per_email = request.getParameter("email");
			String per_contact = request.getParameter("contact");
			String log_uname = request.getParameter("uname");
			String log_password = request.getParameter("password");
	//		String log_cpass = request.getParameter("cpassword");
/*			
			pw.println(pId);
			pw.println(per_name);
			pw.println(per_email);
			pw.println(per_contact);
			pw.println(log_uname);
			pw.println(log_password);
			pw.println(log_cpass);
*/
			ps = con.prepareStatement("UPDATE users_personal_details "
					+ "JOIN user_login_details "
					+ "ON users_personal_details.user_per_id = user_login_details.user_per_id "
					+ "SET user_per_name = ?, user_per_contact = ?, user_per_email = ? ,user_username = ?, user_password = ? "
					+ "WHERE users_personal_details.user_per_id = ?;");
			ps.setString(1, per_name);
			ps.setString(2, per_contact);
			ps.setString(3, per_email);
			ps.setString(4, log_uname);
			ps.setString(5, log_password);
			ps.setInt(6, pId );
			int r = ps.executeUpdate();
			if(r>0){
					pw.println("<br>Profile Updated Successfully...!");
					pw.println("<script>");
	                pw.println("setTimeout(function(){ "
	                		+ "window.history.back();"
	                		+ " }, 1000);");
	                pw.println("</script>");
			}
			else{
				pw.println("<br>Profile Updation Failed...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
                		+ " }, 1000);");
                pw.println("</script>");
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
