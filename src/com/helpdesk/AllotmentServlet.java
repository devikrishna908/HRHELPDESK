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

public class AllotmentServlet extends HttpServlet{

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
			
			int id =Integer.parseInt(request.getParameter("tid"));
			
			ps = con.prepareStatement("SELECT t.ticket_id,tc.cat_name,up.user_per_name,ticket_desc FROM ticket t,users_personal_details up, ticket_category tc where t.ticket_cat_id=tc.cat_id and t.user_per_id=up.user_per_id and t.ticket_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			pw.println("<html><head>");
			pw.println("<script>");
			pw.println(" function cancelAction() { alert('Cancel button clicked. Redirecting to homepage...');window.location.href = 'hr-head-home.html'; }");
			pw.println("</script>");
			pw.println("<style>");
			pw.println("h2 {color: #1a2e35;font-size: 40px; padding-top: 40px;}");
			pw.println(".labelhead { display: inline-block; font-weight: bold;font-family:serif; margin-bottom: 5px;color: #000000;font-size: 14pt;}");
			pw.println("input[type='text'] { border: 1px solid transparent; font-weight: bold;font-family:serif;font-size: 14pt; background-color: white;margin-bottom: 16pt;padding: 8px;}");
			pw.println("input[type='submit'] { padding: 4px; font-size: 12px;border: none; margin-top: 30px;margin-right: 20px; border-radius : 4px;background-color: rgb(60, 152, 206);font-family: 'Times New Roman', Times, serif;font-weight: bold;width: 100px;height: 30px;border-radius: 4px;}");
			pw.println("input[type='button'] { padding: 4px; font-size: 12px;border: none; margin-top: 30px;margin-right: 20px; border-radius : 4px;background-color: rgb(60, 152, 206);font-family: 'Times New Roman', Times, serif;font-weight: bold;width: 100px;height: 30px;border-radius: 4px;}");
			pw.println("label { display: block; font-weight: bold;text-align: left; margin-bottom: 5px;color: #000000;font-size: 12px;}");
			pw.println(".content { display: flex; flex-direction: column; align-items: center; padding: 10px; margin-top: 10px;}");
			pw.println("select {font-size: 14px;width: 25%;outline: none; border-radius: 2px;height: 25px;font-family: 'Times New Roman', Times, serif;}");
			pw.println("</style></head>");
			pw.println("<body>");
			pw.println("<div class='content'>");
			pw.println("<h2>TICKET ALLOTMENT</h2>");
			
			pw.print("<form name ='edit-item' action='allotTicket' method='get'>");
			while (rs.next()) { 
			    pw.println("<br><br><label class='labelhead'> TICKET ID : </label><input type='text' value="+rs.getString(1)+" name='tkt_id' readonly>");
			    pw.println("<br><label class='labelhead'>"+rs.getString(2)+"</label><br><br>");
			    pw.println("<br><br><label class='labelhead'>Ticket Raised By : </label>"+rs.getString(3));
			    pw.println("<br><br><label class='labelhead'>Issue Described by Employee : </label> "+rs.getString(4));
			} 
			ps = con.prepareStatement("SELECT up.user_per_id,up.user_per_name from users_personal_details up,user_login_details ul where up.user_per_id = ul.user_per_id and user_role='HR';");
			rs = ps.executeQuery();
		    pw.println("<br><br><label class='labelhead'>Ticket Allotted To :  </label> ");
			pw.println("<select name='selectHR'>");
	        while (rs.next()) {
	            int hrid = rs.getInt(1);
	            String hrname = rs.getString(2);
	            pw.println("<option value='" + hrid + "'>" + hrname + "</option>");
	        }
	        
	        pw.println("</select>");
			pw.print("<br><br><input type='submit' value ='SUBMIT'/>");
			pw.print("<input type='button' value ='CANCEL' onClick='cancelAction()'/>");
			pw.print("</form>");
			pw.println("</div>");
			pw.print("</body>");
			pw.print("</html>");
			pw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
