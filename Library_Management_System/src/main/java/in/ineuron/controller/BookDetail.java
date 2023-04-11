package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(
		urlPatterns = { 
				"/information"
		}, 
		initParams = { 
				@WebInitParam(name = "url", value = "jdbc:mysql:///librarymanagement"), 
				@WebInitParam(name = "name", value = "root"), 
				@WebInitParam(name = "password", value = "root123")
		})
public class BookDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded successfully.......");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void init() throws ServletException {
		String url = getInitParameter("url");
		String name= getInitParameter("name");
		String password = getInitParameter("password");
		
		System.out.println( url+ "" + name+ " " +password);
		
		try {
		connection = DriverManager.getConnection(url,name,password);
		
		if(connection != null)
		{
			System.out.println("Connection establisher successfully......");
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		PrintWriter write = response.getWriter();
		response.setContentType("text/html");
		
		String bookid = request.getParameter("book_id");
		String bookname = request.getParameter("book_name");
		String authorname= request.getParameter("author_name");
		String booktype= request.getParameter("book_type");
		String bookprice = request.getParameter("book_price");
				
		String insertquery = "insert into bookinformation (Book_id, Book_Name, Author_name, Book_type, Book_price ) values (?,?,?,?,?) ";
		
		try {
			if(connection != null) {
			pstmt = connection.prepareStatement(insertquery);
			System.out.println(" Statement created Successfully .....");
			
			if(pstmt != null) {
				pstmt.setString(1, bookid);
				pstmt.setString(2, bookname);
				pstmt.setString(3, authorname);
				pstmt.setString(4, booktype);
				pstmt.setString(5, bookprice);
				
				int row = pstmt.executeUpdate();
				if(row == 1) {
					write.println("<h1 style='color:red; text-align:center'> Book Inserted Successfully </h1>");
				}
				else {
					write.println("<h1 style='color:red; text-align:center'> Opps ! some error Occured </h1>");
				}
			}
			}
		}catch(SQLException se) {
			se.printStackTrace();
			
		}
		
		
	
		
		
	}
	
	@Override
	public void destroy() {
		try {
			if(connection != null) {
				connection.close();
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}
		
	}
	
	}


