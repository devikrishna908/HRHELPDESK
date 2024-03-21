package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			int pid =Integer.parseInt(request.getParameter("pid"));
			
			ps = con.prepareStatement("SELECT up.user_per_id, up.user_per_name, up.user_per_contact,"
					+ "up.user_per_email, ul.user_username, ul.user_password "
					+ "FROM users_personal_details up "
					+ "JOIN user_login_details ul "
					+ "ON up.user_per_id = ul.user_per_id "
					+ "WHERE up.user_per_id=?;");
			ps.setInt(1, pid);
			rs = ps.executeQuery();

			pw.println("<html><head><title>ProfileUpdate</title><link rel='stylesheet' "
			        + "href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css'>");
			pw.println("<style>");
			pw.println(".form-container {");
			pw.println("    padding: 40px;");
			pw.println("    margin: 0 auto; /* Center the form */");
			pw.println("    text-align: center;");
			pw.println("    width: 50%; /* Adjust the width as needed */");
			pw.println("    background-color: #C5DBE5; /* Background color */");
			pw.println("    border-radius: 10px; /* Rounded corners */");
			pw.println("    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2); /* Shadow effect */");
			pw.println("}");
			pw.println(".input-icon {");
			pw.println("    display: flex;");
			pw.println("    flex-direction: column;");
			pw.println("    align-items: flex-start; /* Align items to start at the same vertical column */");
			pw.println("    margin-bottom: 20px;");
			pw.println("}");
			pw.println(".input-icon input[type='text'], .input-icon input[type='password'], .input-icon input[type='email'] {");
			pw.println("    width: 100%;");
			pw.println("    outline: none;");
			pw.println("    border-radius: 4px;");
			pw.println("    border: 1px solid #3d3d3d;");
			pw.println("    padding: 8px;");
			pw.println("    font-size: 14px;");
			pw.println("    font-family: 'Times New Roman', Times, serif;");
			pw.println("    margin-top: 0px;");
			pw.println("}");
			pw.println(".input-button {");
			pw.println("    margin-top: 30px;");
			pw.println("    margin-bottom: 10px;");
			pw.println("    display: flex;");
			pw.println("    justify-content: center;");
			pw.println("}");
			pw.println(".input-button input[type='submit'], .input-button input[type='button'] {");
			pw.println("    height: 35px;");
			pw.println("    padding: 8px;");
			pw.println("    font-size: 12px;");
			pw.println("    border: none;");
			pw.println("    margin-left: 10px;");
			pw.println("    margin-top: 30px;");
			pw.println("    font-family: 'Times New Roman', Times, serif;");
			pw.println("    font-weight: bold;");
			pw.println("    color: white;");
			pw.println("    background-color: #1a2e35;");
			pw.println("    width: 80px;");
			pw.println("}");
			pw.println("h2 {");
			pw.println("    font-size: 32px;");
			pw.println("    font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;");
			pw.println("    text-align: center;");
			pw.println("    color: #1a2e35;");
			pw.println("    margin-bottom: 50px;");
			pw.println("    margin-top: 40px;");
			pw.println("}");
			pw.println("</style>");
			pw.println("<script>");
			pw.println(" function cancelAction() { alert('Cancel button clicked. Redirecting to homepage...');window.location.href = 'hr-head-home.html'; }");
			pw.println("</script></head>");
			pw.println("<body><h2>EDIT USER PROFILE</h2><div class='form-container'>");
			pw.println("<form name='updateuser' method='get' action='updateUser'>");
			
			while(rs.next()){
			    pw.println("<input type='hidden' id='name' name='per_id' value='" + rs.getString(1) + "'>");
			    pw.println("<div class='input-icon'>");
			    pw.println("    <label for='name'>Name of the User</label><br>");
			    pw.println("    <input type='text' id='name' name='name' value='" + rs.getString(2) + "'>");
			    pw.println("</div>");
			    pw.println("<div class='input-icon'>");
			    pw.println("    <label for='email'>Enter Email ID</label><br>");
			    pw.println("    <input type='email' id='email' name='email' value='" + rs.getString(4) + "'>");
			    pw.println("</div>");
			    pw.println("<div class='input-icon'>");
			    pw.println("    <label for='contact'>Enter Contact Number</label><br>");
			    pw.println("    <input type='text' id='contact' name='contact' value='" + rs.getString(3) + "'>");
			    pw.println("</div>");
			    pw.println("<div class='input-icon'>");
			    pw.println("    <label for='uname'>Enter User Name</label><br>");
			    pw.println("    <input type='text' id='uname' name='uname' value='" + rs.getString(5) + "'>");
			    pw.println("</div>");
			    pw.println("<div class='input-icon'>");
			    pw.println("    <label for='password'>Enter Password</label><br>");
			    pw.println("    <input type='password' id='password' name='password' value='" + rs.getString(6) + "'>");
			    pw.println("</div>");
			    pw.println("<div class='input-icon'>");
			    pw.println("    <label for='cpassword'>Confirm Password</label><br>");
			    pw.println("    <input type='password' id='cpassword' name='cpassword' value='" + rs.getString(6) + "'>");
			    pw.println("</div>");
			}
			pw.println("<div class='input-button'>");
			pw.println("    <input type='submit' value='UPDATE'>");
			pw.println("    <input type='button' value='CANCEL' onClick ='cancelAction()'>");
			pw.println("</div>");
			pw.println("</form></div></body></html>");
		
			
			pw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
