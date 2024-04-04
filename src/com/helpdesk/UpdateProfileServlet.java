package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			String pers_name = request.getParameter("name");
			String per_email = request.getParameter("email");
			String per_contact = request.getParameter("contact");
			String log_uname = request.getParameter("uname");
			String log_password = request.getParameter("password");
			String log_cpass = request.getParameter("cpassword");

	        if (pers_name.isEmpty() || per_email.isEmpty() || per_contact.isEmpty() || log_uname.isEmpty() || log_password.isEmpty() || log_cpass.isEmpty()) {
	            pw.println("<br>All fields are required. Please fill in all the fields.");
	            pw.println("<script>");
	            pw.println("setTimeout(function(){ "
	                    + "window.history.back();"
	                    + " }, 1000);");
	            pw.println("</script>");
	            return;
	        }

	        if (!log_password.equals(log_cpass)) {
	            pw.println("<br>Passwords do not match. Please enter the same password in both fields.");
	            pw.println("<script>");
	            pw.println("setTimeout(function(){ "
	                    + "window.history.back();"
	                    + " }, 1000);");
	            pw.println("</script>");
	            return;
	        }

	        // Email pattern validation
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	        Pattern pattern = Pattern.compile(emailRegex);
	        Matcher matcher = pattern.matcher(per_email);
	        if (!matcher.matches()) {
	            pw.println("<br>Invalid email format. Please enter a valid email address.");
	            pw.println("<script>");
	            pw.println("setTimeout(function(){ "
	                    + "window.history.back();"
	                    + " }, 1000);");
	            pw.println("</script>");
	            return;
	        }

	        // Contact number validation
	        if (!per_contact.matches("\\d{10}")) {
	            pw.println("<br>Contact number must contain exactly 10 digits.");
	            pw.println("<script>");
	            pw.println("setTimeout(function(){ "
	                    + "window.history.back();"
	                    + " }, 1000);");
	            pw.println("</script>");
	            return;
	        }

	        // Password length validation
	        if (log_password.length() < 6 || log_password.length() > 10) {
	            pw.println("<br>Password must contain between 6 and 10 characters.");
	            pw.println("<script>");
	            pw.println("setTimeout(function(){ "
	                    + "window.history.back();"
	                    + " }, 1000);");
	            pw.println("</script>");
	            return;
	        }
			
			
			ps = con.prepareStatement("UPDATE users_personal_details "
					+ "JOIN user_login_details "
					+ "ON users_personal_details.user_per_id = user_login_details.user_per_id "
					+ "SET user_per_name = ?, user_per_contact = ?, user_per_email = ? ,user_username = ?, user_password = ? "
					+ "WHERE users_personal_details.user_per_id = ?;");
			ps.setString(1, pers_name);
			ps.setString(2, per_contact);
			ps.setString(3, per_email);
			ps.setString(4, log_uname);
			ps.setString(5, log_password);
			ps.setInt(6, pId );
			int r = ps.executeUpdate();
			
			HttpSession session = request.getSession();
			String per_name = (String) session.getAttribute("per_name");

			if(r>0){
					pw.println("<br>Profile Updated Successfully...!");
					pw.println("<script>");
	                pw.println("setTimeout(function(){ "
	                		 + "window.location.href = 'hr-home.html?per_name=" + per_name + "';"
	                		+ " }, 1000);");
	                pw.println("</script>");
			}
			else{
				pw.println("<br>Profile Updation Failed...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
               		 + "window.location.href = 'hr-home.html?per_name=" + per_name + "';"
                		+ " }, 1000);");
                pw.println("</script>");
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
