package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteTicket")
public class DeleteTicketServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk", "root", "mysql");

			int tid = Integer.parseInt(request.getParameter("id"));
			ps = con.prepareStatement("DELETE "
					+ "FROM ticket "
					+ "WHERE ticket_id = ?;");
			ps.setInt(1, tid);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				pw.println("<br>Ticket Deleted Successfully...!");
				pw.println("<script>");
				pw.println("setTimeout(function(){ " 		                		
				+ "window.location.href = 'hr-head-home.html';"
				+" }, 1000);");
				pw.println("</script>");
			} else {
				pw.println("<br>Deletion Failed...!");
				pw.println("<script>");
				pw.println("setTimeout(function(){ " 
				+ "window.location.href = 'hr-head-home.html';"
						+ " }, 1000);");
				pw.println("</script>");

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

}
