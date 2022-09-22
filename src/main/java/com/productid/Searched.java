package com.productid;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Searched
 */
@WebServlet("/searchid")
public class Searched extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection connection;

	@Override
	public void init() throws ServletException {

		try {
			System.out.println("Adduser.init() method. DB connection created");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/naik", "root", "Naik9866064819@");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Statement statement = connection.createStatement();) {

			// resultset = read from db where email = 'x'
			// if resultset.hasnext() { pw.write("User already exists"); }
			PrintWriter pw = response.getWriter();
			ResultSet results = statement.executeQuery("select * from product");
			String productSearch = request.getParameter("search");
			//String query="select * from product where productid=?";
			//PreparedStatement ps = connection.prepareStatement("select * from product where productid=?");
			PrintWriter out = response.getWriter();
			if(productSearch == null)
			{
				//PrintWriter out = response.getWriter();
				out.println("<table>");
				out.println("<tr>");
				out.println("<th>Product Name</th>");
				out.println("<th>Product ID</th>");
				out.println("<th>Product Price</th>");
				out.println("</tr>");
				while (results.next()) {
					out.println("<tr>");
					out.println("<td>" + results.getString(1) + "</td>");
					out.println("<td>" + results.getString(2) + "</td>");
					out.println("<td>" + results.getString(3) + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				pw.write("<p><a href=\"Search.html\">SEARCH FOR ITEM</a></p>");
				
			}
			else
			{
				String sql_res= " select * from product where productid = " + productSearch;
                ResultSet inTable = statement.executeQuery(sql_res);
                
                //if not empty then print all product details
                if(inTable.next())
                	//PrintWriter out = response.getWriter();
                	out.println(inTable.getString(1) + ": " + inTable.getString(2) + " " 
                		+ inTable.getString(3) );
                //empty so print error message
                else
                	out.println("There was no element with product ID: " + productSearch + " found in the table, please try again");
			}
			//out.println("<p><a href=\"userhome.html\">Home</a></p>");
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


