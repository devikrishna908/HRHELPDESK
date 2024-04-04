package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hrReports")
public class HRHeadReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Set response content type
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

			// Generate HTML response
			pw.println("<html><head>");
			pw.println("<style>");
			pw.println(".date-picker-container { display: flex; justify-content: center; margin-top: 50px; margin-bottom: 40px; }");
			pw.println(".form-container { display: flex; flex-direction: column; align-items: center; margin-top: 20px; }");
			pw.println("input[type='date'] { margin-bottom: 20px;}");
			pw.println("label { margin-left: 30px; margin-right: 10px;}");
			pw.println("button[type='submit'] {");
		    pw.println("    padding: 10px 20px;");
		    pw.println("    font-size: 14px;");
		    pw.println("    border: none;");
		    pw.println("    border-radius: 4px;");
		    pw.println("    background-color: #004b84;");
		    pw.println("    color: #fff;");
			pw.println("    font-family: serif;");
	        pw.println("    margin-left: 40%;"); 
		    pw.println("    cursor: pointer;");
		    pw.println("}");			
		    pw.println("h2 { margin-top: 80px; margin-bottom: 0; margin-left: 30px; }"); 
		    pw.println("</style>");
		    
	        pw.println("<script>");
	        pw.println("function validateForm() {");
	        pw.println("    var startDate = document.getElementById('start-date').value;");
	        pw.println("    var endDate = document.getElementById('end-date').value;");
	        pw.println("    if (!startDate || !endDate) {");
	        pw.println("        alert('Please select both start and end dates.');");
	        pw.println("        return false;");
	        pw.println("    }");
	        pw.println("    return true;");
	        pw.println("}");
	        pw.println("</script>");

			pw.println("</head><body>");

			// Date picker form
			pw.println("<h2>TICKETS ON SELECTED DATE</h2>");
			pw.println("<div class='form-container'>");
			pw.println("<form method='GET' action='viewReportHead' onsubmit='return validateForm();'>");
			pw.println("<div class='date-picker-container'>");
			pw.println("<label for='start-date'>Start Date:</label>");
			pw.println("<input type='date' id='start-date' name='start-date'>");
			pw.println("<label for='end-date'>End Date:</label>");
			pw.println("<input type='date' id='end-date' name='end-date'>");
			pw.println("<br>");
			pw.println("</div>");
			pw.println("<button type='submit' name='submit'>SHOW TICKETS</button>");
			pw.println("</form>");
			pw.println("</div>");

			pw.println("</body></html>");
	}
}
