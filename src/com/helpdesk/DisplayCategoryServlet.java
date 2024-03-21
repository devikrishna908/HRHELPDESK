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

@WebServlet("/hrTicketCategory")
public class DisplayCategoryServlet extends HttpServlet{

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
						
			ps = con.prepareStatement("SELECT * FROM ticket_category");
			rs = ps.executeQuery();


			pw.println("<html><head>");
            pw.println("<style>");
            pw.println("table { width: 80%; border-collapse: collapse; margin: 10px auto; }");
            pw.println("th, td { border: 1px solid #ddd; padding: 8px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; color: #000000; text-align: center; }");
            pw.println("th { background-color: #004b84; color: white; text-align: center; margin-right: 20px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; }");
            pw.println("td a { text-decoration: underline; font-family: 'Times New Roman', Times, serif; text-align: center; font-size: 12px; color: blue; cursor: pointer; }");
            pw.println("td a:hover { color: rgb(214, 19, 5); }");
            pw.println(".table-container { text-align: center; }");
            pw.println("</style>");
  			pw.println("</head><body>");
			pw.println("<a href='new-category.html' class='create-link'>ADD NEW CATEGORY</a><br>");
			pw.println("<div class='table-container'>"); 
			pw.println("<h2>TICKET CATEGORY DETAILS</h2>");

			pw.println("<table border ='1'>");

			pw.println("<tr>");
				pw.println("<th>CATEGORY ID</th>");
				pw.println("<th>CATEGORY NAME</th>");
				pw.println("<th>EDIT</th>");
				pw.println("<th>DELETE</th>");
			pw.println("</tr>");
			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getString(2) + "</td>");
				pw.println("<td><a href = 'editCategory?cid=" + rs.getInt(1) +"'>EDIT</a></td>");
				pw.println("<td><a id='delete' onclick='confirmDeleteCategory("+rs.getInt(1)+")'>DELETE</a></td>");
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
