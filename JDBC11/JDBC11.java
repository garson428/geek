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
 * Servlet implementation class JDBC11
 */
@WebServlet("/JDBC11")
public class JDBC11 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDBC11() {
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

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		int txtID = Integer.valueOf(request.getParameter("txtID"));
		String txtName = request.getParameter("txtName");
		String txtTel = request.getParameter("txtTel");
		int txtAge = Integer.valueOf(request.getParameter("txtAge"));
		String txtBirthday = request.getParameter("txtBirthday");

		Connection db_con = null;
		PreparedStatement db_st = null;
		ResultSet result = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			db_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Challenge_db?characterEncoding=UTF-8&serverTimezone=JST", "GEEK", "JOB");

			db_st = db_con.prepareStatement("UPDATE profiles SET name=?, tel=?, age=?, birthday=? WHERE profileID=?");

			db_st.setString(1,txtName);
			db_st.setString(2,txtTel);
			db_st.setInt(3,txtAge);
			db_st.setString(4, txtBirthday);
			db_st.setInt(5,txtID);

			int num = db_st.executeUpdate();

			System.out.println(num);

			db_st = db_con.prepareStatement("SELECT * FROM profiles");
			result = db_st.executeQuery();

			System.out.println("e");

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
