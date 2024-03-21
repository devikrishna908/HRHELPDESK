package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loadMyTicketsServlet")
public class LoadMyTicketServlet extends HttpServlet{

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
			
			ServletContext context = getServletContext();
			int per_id = (Integer) context.getAttribute("per_id");
						
			ps = con.prepareStatement("SELECT t.ticket_id, t.ticket_date, tc.cat_name, up2.user_per_name AS allotted_to, t.ticket_desc "
										+"FROM ticket t "
										+"JOIN users_personal_details up1 ON t.user_per_id = up1.user_per_id "
										+"JOIN ticket_category tc ON t.ticket_cat_id = tc.cat_id "
										+"LEFT JOIN ticket_allocation ta ON t.ticket_id = ta.ticket_id "
										+"LEFT JOIN users_personal_details up2 ON ta.allot_per_id = up2.user_per_id "
										+ "WHERE t.user_per_id="+per_id
										+" ORDER BY t.ticket_id DESC;");
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
			pw.println("<h2>ALL TICKETS RAISED</h2>");

			pw.println("<table border ='1'>");

			pw.println("<tr>");
				pw.println("<th>TICKET ID</th>");
				pw.println("<th>TICKET RAISED DATE</th>");
				pw.println("<th>TICKET CATEOGORY</th>");
				pw.println("<th>TICKET ALLOTED TO</th>");
				pw.println("<th>TICKET DESCRIPTION</th>");
				pw.println("<th>STATUS VIEW</th>");
			pw.println("</tr>");
			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getDate(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getString(5) + "</td>");
				pw.println("<td><a href = 'viewStatus?id=" + rs.getInt(1) +"'>VIEW STATUS</a></td>");
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
