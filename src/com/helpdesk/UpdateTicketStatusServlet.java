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

@WebServlet("/updateTicketStatus")
public class UpdateTicketStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();

			int allocation_id = Integer.parseInt(request.getParameter("id"));

			pw.println("<html>");
			pw.println("<head>");
			pw.println("<title>STATUS UPDATE</title>");
			pw.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css'>");
			pw.println("<style>");
			pw.println("body {");
			pw.println("margin: 0;");
			pw.println("padding: 0;");
			pw.println("display: flex;");
			pw.println("justify-content: center;");
			pw.println("align-items: center;");
			pw.println("height: 100vh;");
			pw.println("}");
			pw.println(".container {");
			pw.println("width: 50%;");
			pw.println("margin: 50px auto;");
			pw.println("text-align: center;");
			pw.println("background-color: #f0f0f0;");
			pw.println("border: 2px solid #ccc;");
			pw.println("border-radius: 10px;");
			pw.println("padding: 20px;");
			pw.println("background-color: #f0f0f0;");
			pw.println("}");
			pw.println("h2 {");
			pw.println("margin-top: 40px;");
			pw.println("text-align: center;");
			pw.println("margin-bottom: 40px;");
			pw.println("}");
			pw.println("input[type='text'] {");
			pw.println("width: calc(100% - 70px);");
			pw.println("padding: 10px;");
			pw.println("font-family: serif;");
			pw.println("font-size: 16px;");
			pw.println("border: 1px solid #ccc;");
			pw.println("border-radius: 5px;");
			pw.println("box-sizing: border-box;");
			pw.println("text-align: center;");
			pw.println("margin: 0 auto;");
			pw.println("}");
			pw.println(".button-container {");
			pw.println("text-align: center;");
			pw.println("margin-top: 40px;");
			pw.println("}");
			pw.println("button {");
			pw.println("padding: 10px 20px;");
			pw.println("font-size: 12pt;");
			pw.println("font-family: serif;");
			pw.println("border: none;");
			pw.println("cursor: pointer;");
			pw.println("border-radius: 5px;");
			pw.println("margin: 0 5px;");
			pw.println("border: none;");
			pw.println("}");
			pw.println("button.update {");
			pw.println("background-color: #4CAF50;");
			pw.println("color: white;");
			pw.println("}");
			pw.println("button.cancel {");
			pw.println("background-color: #f44336;");
			pw.println("color: white;");
			pw.println("}");
	        pw.println("select {");
	        pw.println("outline: none;");
	        pw.println("border: 1px solid #ccc;"); /* Border around the input */
	        pw.println("border-radius: 5px;"); /* Rounded corners for the input */
	        pw.println("padding: 8px;");
	        pw.println("margin-bottom: 3px;");
	        pw.println("padding-left: 30px;");
	        pw.println("font-size: 12pt;");
	        pw.println("font-family: 'Times New Roman', Times, serif;");
	        pw.println("}");
	        pw.println("textarea {");
	        pw.println("font-family: serif;");
	        pw.println("width: 100%;");
	        pw.println("font-size: 12pt;");
	        pw.println("}");
			pw.println("</style>");
			pw.println("<script>");
			pw.println("function cancelAction() {");
			pw.println("alert('Cancel button clicked. Redirecting to homepage...');");
			pw.println("window.history.back();");
			pw.println("}");
			pw.println("</script>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<div class='container'>");
			pw.println("<h2>TICKET STATUS UPDATE</h2>");
			pw.println("<form id='statusUpdate' action='saveTicketStatus' method='get'>");
			pw.println("<input type='hidden' id='allot_id' name='allocationId' value="+allocation_id+"><br><br>");                
	        pw.println("<label class='status'>Choose Ticket Status</label><br><br>");
	        pw.println("<select id='status' name='status'>");
	        pw.println("<option value='select'>Select Ticket Status</option>"
	        		+ "<option value='Open'>Open</option>"
	        		+ "<option value='In Progress'>In Progress</option>"
	        		+ "<option value='Pending'>Pending</option>"
	        		+ "<option value='Resolved'>Resolved</option>"
	        	    + "<option value='Closed'>Closed</option>");
	        pw.println("</select><br><br>");
	        pw.println("<label class='status'>Ticket Description</label><br><br>");
	        pw.println("<textarea id=\"message\" name=\"description\" rows=\"6\" cols=\"100\"></textarea><br><br>");
			pw.println("<div class='button-container'>");
			pw.println("<button type='submit' class='update'>Update</button>");
			pw.println("<button type='button' class='cancel' onclick='cancelAction()'>Cancel</button>");
			pw.println("</div>");
			pw.println("</form>");
			pw.println("</div>");
			pw.println("</body>");
			pw.println("</html>");

			pw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
