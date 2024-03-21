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

@WebServlet("/newCategory")

public class NewCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String cname = request.getParameter("categoryName");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
			
			ps = con.prepareStatement("INSERT INTO ticket_category(cat_name) VALUES(?)");
			ps.setString(1,cname);
			int no = ps.executeUpdate();
			
			if(no>0){
				pw.println("<br>Category added successfully...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
                		+ " }, 1000);");
                pw.println("</script>");				
				
			}
			else{
				pw.println("<br>Failed To Add Category, Something went wrong...!");
				pw.println("<script>");
                pw.println("setTimeout(function(){ "
                		+ "window.history.back();"
                		+ " }, 1000);");
                pw.println("</script>");				
			}
				
			
		} catch(Exception e){
			e.printStackTrace();
		}

	}
}
