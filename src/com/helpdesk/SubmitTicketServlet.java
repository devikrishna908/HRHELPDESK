package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitTicketServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement ps = null;
	ResultSet rs = null;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Date date = new Date();
		java.sql.Date cdate= new java.sql.Date(date.getTime());

		ServletContext context = getServletContext();
		int per_id = (Integer) context.getAttribute("per_id");
		
		String ticket_description = request.getParameter("message");
		int ticket_category_id = Integer.parseInt(request.getParameter("category"));
		
/*		pw.println(ticket_description);
		pw.println(ticket_category);
		pw.println(per_id);
		pw.println(cdate);
*/
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
			ps = con.prepareStatement("INSERT INTO ticket (user_per_id, ticket_cat_id, ticket_date, ticket_desc) VALUES (?,?,?,?)");
			ps.setInt(1, per_id);
			ps.setInt(2, ticket_category_id);
			ps.setDate(3, cdate);
			ps.setString(4, ticket_description);
			int no = ps.executeUpdate();
			
			if(no>0){
				pw.println("<br>Ticket Raised Successfully");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
                		+ " }, 1500);");
                pw.println("</script>");
			}
			else{
				pw.println("<br>Failed To Raise, Something went wrong...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
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
