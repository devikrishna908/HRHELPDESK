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

@WebServlet("/editCategory")
public class EditCategoryServlet extends HttpServlet {

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

			int cid = Integer.parseInt(request.getParameter("cid"));

			ps = con.prepareStatement("SELECT * FROM ticket_category WHERE cat_id=?");
			ps.setInt(1, cid);
			rs = ps.executeQuery();

			pw.println("<html>");
			pw.println("<head>");
			pw.println("<title>Edit Category</title>");
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
			pw.println("font-size: 12px;");
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
			pw.println("</style>");
			pw.println("<script>");
			pw.println("function cancelAction() {");
			pw.println("alert('Cancel button clicked. Redirecting to homepage...');");
			pw.println("window.location.href = 'hr-head-home.html';");
			pw.println("}");
			pw.println("</script>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<div class='container'>");
			pw.println("<h2>EDIT CATEGORY</h2>");
			pw.println("<form id='categoryForm' action='updateCategory' method='get'>");
			pw.println("<label for='categoryName'>Category Name</label><br>");
			while(rs.next()){
			    pw.println("<input type='hidden' id='categoryId' name='categoryId' value="+rs.getInt(1)+"><br><br>");                
			    pw.println("<input type='text' id='categoryName' name='categoryName' value='"+rs.getString(2)+"'><br><br>");                
			}
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
