package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewReportHead")
public class HeadReportViewServlet extends HttpServlet {
    

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		// Database connection parameters
        String DB_URL = "jdbc:mysql://localhost:3306/hrhelpdesk";
        String DB_USER = "root";
        String DB_PASSWORD = "mysql";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the database connection
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Set response content type
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();

            // Retrieve start and end dates from request parameters
            String startDate = request.getParameter("start-date");
            String endDate = request.getParameter("end-date");

            // Prepare SQL statement to fetch data based on date range
            String sql = "SELECT t.ticket_id, t.ticket_date, tc.cat_name, up1.user_per_name AS raised_by, "
                    + "up2.user_per_name AS allotted_to FROM ticket t "
                    + "JOIN users_personal_details up1 ON t.user_per_id = up1.user_per_id "
                    + "JOIN ticket_category tc ON t.ticket_cat_id = tc.cat_id "
                    + "LEFT JOIN ticket_allocation ta ON t.ticket_id = ta.ticket_id "
                    + "LEFT JOIN users_personal_details up2 ON ta.allot_per_id = up2.user_per_id "
                    + "WHERE t.ticket_date BETWEEN ? AND ? " + "ORDER BY t.ticket_id DESC;";

            // Create prepared statement
            ps = con.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);

            // Execute the query
            rs = ps.executeQuery();

            // Generate HTML response
            pw.println("<html><head>");
            pw.println("<style>");
		    pw.println("h2 { margin-top: 80px; margin-bottom: 50px; margin-left: 30px; }"); 
            pw.println("table { width: 80%; border-collapse: collapse; margin: 10px auto; }");
            pw.println("th, td { border: 1px solid #ddd; padding: 8px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; color: #000000; text-align: center; }");
            pw.println("th { background-color: #004b84; color: white; }");
            pw.println("td a { text-decoration: underline; color: blue; cursor: pointer; }");
            pw.println("td a:hover { color: rgb(214, 19, 5); }");
            pw.println(".table-container { text-align: center; }");
            pw.println("</style>");
            pw.println("<script>");
            pw.println("function goBack() {");
            pw.println("window.history.back();");
            pw.println("}");
            pw.println("</script>");

            pw.println("</head><body>");


            // Table container
            pw.println("<div class='table-container'>");
            pw.println("<h2>TICKETS FROM  "+ startDate + "  TO  "+endDate+"</h2>");
            pw.println("<table border ='1'>");
            pw.println("<tr>");
            pw.println("<th>TICKET ID</th>");
            pw.println("<th>TICKET RAISED DATE</th>");
            pw.println("<th>TICKET CATEGORY</th>");
            pw.println("<th>TICKET RAISED BY</th>");
            pw.println("<th>TICKET ALLOTTED TO</th>");
            pw.println("<th>STATUS VIEW</th>");
            pw.println("</tr>");
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getDate(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getString(4) + "</td>");
                pw.println("<td>" + rs.getString(5) + "</td>");
                pw.println("<td><a href='viewStatus?id=" + rs.getInt(1) + "'>VIEW STATUS</a></td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
            pw.println("</div>");
            pw.println("<div style='text-align: center; margin-top: 60px;'>"); // Style added for centering and spacing
            pw.println("<button style='font-family: serif; font-size: 12px; font-weight:bold; color: white; "
            		+ "background-color: #004b84; border: none; padding: 10px 20px; cursor: pointer;' "
            		+ "onClick='goBack()'>GO BACK</button>");
            pw.println("</div>");

            pw.println("</body></html>");
        } catch (ClassNotFoundException | SQLException ex) {
           
        }
	}
}
