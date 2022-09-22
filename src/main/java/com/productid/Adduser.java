package com.productid;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Adduser
 */
@WebServlet("/addlist")
public class Adduser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection;
	@Override
	public void init() throws ServletException {

		try {
			System.out.println("Adduser.init() method. DB connection created");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/naik", "root", "Naik9866064819@");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String productname = request.getParameter("productname");
		String productid = request.getParameter("productid");
		String productprice = request.getParameter("productprice");
		//String password = request.getParameter("password");


		try (Statement statement = connection.createStatement();) {

			// resultset = read from db where email = 'x'
			// if resultset.hasnext() { pw.write("User already exists"); }

			String query = "insert into product values('" + productname + "', '" + productid + "', '" + productprice + "' )";
			System.out.println("Query being executed: " + query);
			int rowsInserted = statement.executeUpdate(query);
			System.out.println("Number of rows inserted: " + rowsInserted);

			PrintWriter pw = response.getWriter();
			pw.write("Product details Successfully added.....!");
			pw.write("<p><a href=\"Adduser.html\">ADD FOR ITEM</a></p>");
			pw.write("<p><a href=\"Search.html\">SEARCH FOR ITEM</a></p>");
			//pw.write("<p><a href=\"addlist.html\">Home</a></p>");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void destroy() {
		try {
			System.out.println("Adduser.destroy() method. DB connection closed");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}

       
    
	