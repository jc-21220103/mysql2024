package jp.ac.jc21;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/product" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final String dbServer = "192.168.54.231";
	final String dbPort = "3306";
	final String dbName = "test2023";
	final String user = "test2023";
	final String pass = "test2023";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "jdbc:mysql://" + dbServer + "/" + dbName;
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("<h2>Connect to : ").append(url).append("</h2>");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, pass);

			String sql1 = "SELECT MAKER_CODE,MAKER_NAME FROM MAKER";  // SQLの修正
			String sql2 = "SELECT PRODUCT_CODE,PRODUCT_NAME,MAKER_CODE FROM PRODUCT";
			String id = request.getParameter("ID");
			String sql3 = "SELECT PRODUCT_CODE,PRODUCT_NAME,MAKER_CODE FROM PRODUCT";
			if(id != null) {
				sql3 += " WHERE MAKER_CODE = ?";
			}

			PreparedStatement statement1 = conn.prepareStatement(sql1);
			
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			
			PreparedStatement statement3 = conn.prepareStatement(sql3);
			
			if(id != null) {
				statement3.setString(1, id);
			}

			ResultSet rs1 = statement1.executeQuery();
			ResultSet rs2 = statement2.executeQuery();
			ResultSet rs3 = statement3.executeQuery();

			ArrayList<String[]> result1 = new ArrayList<>();
			while (rs1.next()==true) {
				String[] s1 = new String[2];
				s1[0]=rs1.getString("MAKER_CODE");
				s1[1]=rs1.getString("MAKER_NAME");
				//s[2]=rs.getString("price");
				result1.add(s1);
			}
			
			ArrayList<String[]> result2 = new ArrayList<>();
			while (rs2.next()==true) {
				String[] s2 = new String[3];
				s2[0]=rs2.getString("PRODUCT_CODE");
				s2[1]=rs2.getString("PRODUCT_NAME");
				s2[2]=rs2.getString("MAKER_CODE");
				result2.add(s2);
			}
			
			ArrayList<String[]> result3 = new ArrayList<>();
			while (rs3.next()==true) {
				String[] s3 = new String[3];
				s3[0]=rs3.getString("PRODUCT_CODE");
				s3[1]=rs3.getString("PRODUCT_NAME");
				s3[2]=rs3.getString("MAKER_CODE");
				result3.add(s3);
			}

			request.setAttribute("maker", result1);
			request.setAttribute("product2", result2);
			request.setAttribute("product3", result3);
			RequestDispatcher rd =
					request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}