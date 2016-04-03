package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.CategoryDAO;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.utility.DatabaseConnection;

public class CategoryDAOImpl implements CategoryDAO {
	
	private String query;
	private String dml;
	private StringBuilder builder;

	@Override
	public List<Category> fetchAllCategories() {
		List<Category> categoryList = new ArrayList<Category>();
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		ResultSet rs;
		
		builder.append("SELECT category_name FROM library.category ");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Category category = new Category();
				category.setCategory(rs.getString(1));
				categoryList.add(category);
			}
			
			ps.close();
			rs.close();
			con.close();
			
			return categoryList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return categoryList;
	}

}
