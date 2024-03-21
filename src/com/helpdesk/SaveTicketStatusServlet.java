package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveTicketStatus")
public class SaveTicketStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement ps = null;
	ResultSet rs = null;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Date date = new Date();
		java.sql.Date cdate= new java.sql.Date(date.getTime());
		
		int allocation_id = Integer.parseInt(request.getParameter("allocationId"));
		String status = request.getParameter("status");
		String description = request.getParameter("description");
		
/*		pw.println(allocation_id);
		pw.println(status);
		pw.println(description);
		pw.println(cdate);
*/
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
				
			if(status.equals("Closed")){
				ps = con.prepareStatement("UPDATE ticket_allocation SET ticket_state='inactive' WHERE ticket_allocation_id= "+allocation_id);
				ps.executeUpdate();
			}
				ps = con.prepareStatement("INSERT INTO ticket_status(ticket_allocation_id, ticket_status, ticket_satus_desc, ticket_status_updated_date) VALUES (?,?,?,?)");
				ps.setInt(1, allocation_id);
				ps.setString(2, status);
				ps.setString(3, description);
				ps.setDate(4, cdate);
				int no = ps.executeUpdate();
				
				if(no>0){
					pw.println("<br>Ticket Status Updated");
					pw.println("<script>");
	                pw.println("setTimeout(function(){ "
	                		+ "window.location.href = \"hr-home.html\";"
	                		+ " }, 1000);");
	                pw.println("</script>");
				}
				else{
					pw.println("<br>Failed to update, Something went wrong...!");
					pw.println("<script>");
	                pw.println("setTimeout(function(){ "
	                		+ "window.location.href = \"hr-home.html\";"
	                		+ " }, 1000);");
	                pw.println("</script>");
				}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if(con!=null)
				try {
					con.close();
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
