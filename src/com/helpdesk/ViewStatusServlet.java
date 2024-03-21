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

@WebServlet("/viewStatus")
public class ViewStatusServlet extends HttpServlet{

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
                        
            int tid = Integer.parseInt(request.getParameter("id"));
            ps = con.prepareStatement("SELECT "
                    + "ts.ticket_status,ts.ticket_satus_desc,ts.ticket_status_updated_date "
                    + " FROM ticket_allocation ta "
                    + " JOIN ticket_status ts ON ta.ticket_allocation_id = ts.ticket_allocation_id"
                    + " WHERE ta.ticket_id = "+tid);
            rs = ps.executeQuery();

            pw.println("<html><head>");
            pw.println("<style>");
            pw.println("table { width: 80%; border-collapse: collapse; margin: 0 auto; }"); // Removed top margin
            pw.println("th { border: 1px solid #ddd; padding: 8px; font-family: 'Times New Roman', Times, serif; font-size: 12px; color: #000000; text-align: center; }");
            pw.println("td { border: 1px solid #ddd; padding: 8px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; color: #000000; text-align: center; }");
            pw.println("th { background-color: #004b84; color: white; text-align: center; margin-right: 20px; font-family: 'Times New Roman', Times, serif; font-size: 12pt; }");
            pw.println("td a { text-decoration: underline; font-family: 'Times New Roman', Times, serif; text-align: center; font-size: 12px; color: blue; cursor: pointer; }");
            pw.println("td a:hover { color: rgb(214, 19, 5); }");
            pw.println(".table-container { text-align: center; margin-top: 10px; display: flex; justify-content: center; align-items: center; height: 30vh; }"); // Added margin-top and centered vertically
            pw.println("</style>");
            pw.println("<script>");
            pw.println("function goBack() {");
            pw.println("window.history.back();");
            pw.println("}");
            pw.println("</script>");

            pw.println("</head><body>");
            pw.println("<h2 style='text-align: center; margin-top: 80px;'>TICKET STATUS UPDATES</h2>"); // Removed top margin

            pw.println("<div class='table-container'>"); // Start of table container

            pw.println("<table border ='1'>");
            pw.println("<tr>");
            pw.println("<th>TICKET STATUS</th>");
            pw.println("<th>STATUS DESCRIPTION</th>");
            pw.println("<th>STATUS UPDATED ON</th>");
            pw.println("</tr>");
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getString(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getDate(3) + "</td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</div>"); // End of table container

            // Back button with styling
            pw.println("<div style='text-align: center; margin-top: 20px;'>"); // Style added for centering and spacing
            pw.println("<button style='font-family: serif; font-size: 12px; font-weight:bold; color: white; "
            		+ "background-color: #004b84; border: none; padding: 10px 20px; cursor: pointer;' "
            		+ "onClick='goBack()'>GO BACK</button>");
            pw.println("</div>");

            pw.println("</body></html>");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
