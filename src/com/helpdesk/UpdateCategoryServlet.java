package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateCategory")

public class UpdateCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement ps = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk", "root", "mysql");

			int cId = Integer.parseInt(request.getParameter("categoryId"));
			String cat_name = request.getParameter("categoryName");
			ps = con.prepareStatement("UPDATE ticket_category SET cat_name=? WHERE cat_id = ?");
			ps.setString(1, cat_name);
			ps.setInt(2, cId );
			int r = ps.executeUpdate();
			if(r>0){
					pw.println("<br>Category Updated Successfully...!");
					pw.println("<script>");
	                pw.println("setTimeout(function(){ "
	                		+ "window.history.back();"
	                		+ " }, 1000);");
	                pw.println("</script>");
			}
			else{
				pw.println("<br>Category Updation Failed...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
                		+ " }, 1000);");
                pw.println("</script>");
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
