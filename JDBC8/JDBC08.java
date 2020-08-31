package camp;

import java.io.IOException;
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

/**
 * Servlet implementation class JDBC08
 */
@WebServlet("/JDBC08")
public class JDBC08 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDBC08() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		Connection db_con = null;
		PreparedStatement db_st = null;
		ResultSet result = null;
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");


		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			db_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Challenge_db?characterEncoding=UTF-8&serverTimezone=JST", "GEEK", "JOB");

			String search = request.getParameter("txtname");

			db_st = db_con.prepareStatement("SELECT * FROM profiles WHERE name LIKE ?");
			db_st.setString(1,'%' + search + '%');

			result = db_st.executeQuery();
				while(result.next()) {
					System.out.println(result.getInt("profileID"));
					System.out.println(result.getString("name"));
					System.out.println(result.getString("tel"));
					System.out.println(result.getInt("age"));
					System.out.println(result.getString("birthday"));
				}

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (db_con != null) {
				try {
					db_con.close();
				} catch(Exception e_con) {
			    	System.out.print(e_con.getMessage());
				}
			}

		    if (db_st != null) {
			    try {
					db_st.close();
			    } catch(Exception e_con) {
			    	System.out.print(e_con.getMessage());
				}
		    }

			if (result != null) {
			    try {
		        	result.close();
			    } catch(Exception e_con) {
		    	System.out.print(e_con.getMessage());
			    }
			 }
		 }
	}
}

