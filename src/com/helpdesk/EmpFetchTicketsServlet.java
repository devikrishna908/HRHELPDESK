package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmpFetchTicketsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	ServletContext context = getServletContext();
	int per_id = (Integer) context.getAttribute("per_id");

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk", "root", "mysql");

			ps = con.prepareStatement("Select * from ticket where user_per_id = ? order by ticket_id desc");
			ps.setInt(1, per_id);
			rs = ps.executeQuery();

			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();

			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getInt(2) + "</td>");
				pw.println("<td><a href = 'editItem?id=" + rs.getInt(1) +"'>EDIT</a></td>");
				pw.println("<td><a href = 'deleteItem?id=" + rs.getInt(1) +"'>DELETE</a></td>");
				pw.println("</tr>");
			}

			pw.println("</table>");
			pw.println("</body></html>");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
