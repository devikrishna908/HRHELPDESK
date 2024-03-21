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


@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String uname = request.getParameter("uname");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrhelpdesk","root","mysql");
			ps = con.prepareStatement("INSERT INTO users_personal_details (user_per_name, user_per_contact, user_per_email) VALUES (?,?,?)", java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,name);
			ps.setString(2,contact);
			ps.setString(3,email);
			int no = ps.executeUpdate();
			
			if(no>0){
				rs =ps.getGeneratedKeys();
				if(rs.next()){
					int per_id =rs.getInt(1);
					ps = con.prepareStatement("INSERT INTO user_login_details(user_per_id, user_username, user_password, user_role) VALUES(?,?,?,?)");
					ps.setInt(1, per_id);
					ps.setString(2, uname);
					ps.setString(3, password);
					ps.setString(4, role);
					no = ps.executeUpdate();
					if(no>0){

						pw.println("<br>User Added Successfully...!");
						pw.println("<script>");
		                pw.println("setTimeout(function(){ "
		                		+ "window.history.back();"
		                		+ " }, 1500);");
		                pw.println("</script>");
					}
					else{
						pw.println("alert('Failed To Add User, Something went wrong...!');");
						
						pw.println("<br>Failed To Add User, Something went wrong...!");
						pw.println("<script>");
		                pw.println("setTimeout(function(){ "
		                		+ "window.history.back();"
		                		+ " }, 1500);");
		                pw.println("</script>");
					}
				}
			}
			else{
				pw.println("<br>Failed To Add User, Something went wrong...!");
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
