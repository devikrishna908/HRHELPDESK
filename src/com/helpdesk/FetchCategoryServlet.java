package com.helpdesk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FetchCategoryServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
			ps = con.prepareStatement("SELECT * FROM ticket_category");
			rs = ps.executeQuery();
			
			StringBuilder options = new StringBuilder();
			while(rs.next()){
				int id = rs.getInt(1);
                String name = rs.getString(2);
                options.append("<option value=\"" + id + "\">" + name + "</option>");
			}
			
			pw.println(options.toString());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
