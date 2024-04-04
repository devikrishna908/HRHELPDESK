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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeadAllotmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement ps = null;
	ResultSet rs = null;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Date date = new Date();
		java.sql.Date cdate= new java.sql.Date(date.getTime());
		int ticket_id = Integer.parseInt(request.getParameter("tkt_id"));
		int alloted_hr_id = Integer.parseInt(request.getParameter("selectHR"));
		
/*		pw.println(ticket_id);
		pw.println(alloted_hr_id);
		pw.println(cdate);
*/
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
			ps = con.prepareStatement("INSERT INTO ticket_allocation(ticket_id, allot_per_id, ticket_allocated_date,ticket_state) VALUES (?,?,?,?)");
			ps.setInt(1, ticket_id);
			ps.setInt(2, alloted_hr_id);
			ps.setDate(3, cdate);
			ps.setString(4, "active");
			int no = ps.executeUpdate();
			
			if(no>0){
				
				pw.println("<br>Ticket Allotted Successfully");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.location.href = 'hr-head-home.html';"
                		+ " }, 1500);");
                pw.println("</script>");
			}
			else{
				pw.println("<br>Failed to allot the ticket, Something went wrong...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.location.href = 'hr-head-home.html';;"
                		+ " }, 1500);");
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
