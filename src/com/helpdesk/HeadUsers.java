package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeadUsers extends HttpServlet{

	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk", "root", "mysql");
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
						
			ps = con.prepareStatement("SELECT up.user_per_id, up.user_per_name, up.user_per_contact,up.user_per_email,ul.user_username,ul.user_password,ul.user_role FROM users_personal_details up JOIN user_login_details ul ON up.user_per_id = ul.user_per_id;");
			rs = ps.executeQuery();


			pw.println("<html><head>");
            pw.println("<style>");
            pw.println("table { width: 80%; border-collapse: collapse; margin: 10px auto; }");
            pw.println("th { border: 1px solid #ddd; padding: 8px; font-family: 'Times New Roman', Times, serif; font-size: 12px; color: #000000; text-align: center; }");
            pw.println("td { border: 1px solid #ddd; padding: 8px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; color: #000000; text-align: center; }");
            pw.println("th { background-color: #004b84; color: white; text-align: center; margin-right: 20px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; }");
            pw.println("td a { text-decoration: underline; font-family: 'Times New Roman', Times, serif; text-align: center; font-size: 12px; color: blue; cursor: pointer; }");
            pw.println("td a:hover { color: rgb(214, 19, 5); }");
            pw.println(".table-container { text-align: center; }");
            pw.println("</style>");
  			pw.println("</head><body>");
			pw.println("<a href='new-user.html' class='create-link'>CREATE NEW USER</a><br>");
			pw.println("<div class='table-container'>"); 
			pw.println("<h2>USER DETAILS</h2>");

			pw.println("<table border ='1'>");

			pw.println("<tr>");
				pw.println("<th>USER ID</th>");
				pw.println("<th>NAME</th>");
				pw.println("<th>CONTACT</th>");
				pw.println("<th>EMAIL</th>");
				pw.println("<th>USERNAME</th>");
				pw.println("<th>PASSWORD</th>");
				pw.println("<th>ROLE</th>");
				pw.println("<th>EDIT</th>");
				pw.println("<th>DELETE</th>");
			pw.println("</tr>");
			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getString(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getString(5) + "</td>");
				pw.println("<td>" + rs.getString(6) + "</td>");
				pw.println("<td>" + rs.getString(7) + "</td>");
				pw.println("<td><a href = 'editUser?pid=" + rs.getInt(1) +"'>EDIT</a></td>");
				pw.println("<td><a id='delete' onclick='confirmDelete("+rs.getInt(1)+")'>DELETE</a></td>");
				pw.println("</tr>");
			}

			pw.println("</table>");
			pw.println("</div>");
			pw.println("</body></html>");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
