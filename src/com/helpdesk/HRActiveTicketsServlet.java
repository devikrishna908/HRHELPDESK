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
import javax.servlet.http.HttpSession;

@WebServlet("/activeTicket")
public class HRActiveTicketsServlet extends HttpServlet{

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

			HttpSession session = request.getSession();
			Integer per_id = (Integer) session.getAttribute("per_id");

			ps = con.prepareStatement("SELECT t.ticket_id, t.ticket_date, tc.cat_name,"
					+ " up1.user_per_name AS raised_by, ta.ticket_allocated_date as Allotted_On, t.ticket_desc, ta.ticket_allocation_id "
					+ " FROM ticket t "
					+ " JOIN users_personal_details up1 ON t.user_per_id = up1.user_per_id"
					+ " JOIN ticket_category tc ON t.ticket_cat_id = tc.cat_id "
					+ " JOIN ticket_allocation ta ON t.ticket_id = ta.ticket_id "
					+ " JOIN users_personal_details up2 ON ta.allot_per_id = up2.user_per_id "
					+ " WHERE ta.ticket_state!='inactive' AND ta.allot_per_id= "+per_id);
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
			pw.println("<div class='table-container'>"); // Start of table container
			pw.println("<h2>ACTIVE TICKETS</h2>");

			pw.println("<table border ='1'>");

			pw.println("<tr>");
				pw.println("<th>TICKET ID</th>");
				pw.println("<th>TICKET RAISED ON</th>");
				pw.println("<th>TICKET CATEOGORY</th>");
				pw.println("<th>TICKET RAISED BY</th>");
				pw.println("<th>TICKET ALLOTTED ON</th>");
				pw.println("<th>TICKET DESCRIPTION</th>");
				pw.println("<th>UPDATE STATUS</th>");
				pw.println("<th>VIEW DETAILS</th>");
			pw.println("</tr>");
			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getDate(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getDate(5) + "</td>");
				pw.println("<td>" + rs.getString(6) + "</td>");
				pw.println("<td><a href = 'updateTicketStatus?id=" + rs.getInt(7) +"'>UPDATE STATUS</a></td>");
				pw.println("<td><a href = 'viewStatus?id=" + rs.getInt(1) +"'>VIEW DETAILS</a></td>");
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
