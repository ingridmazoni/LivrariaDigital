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

	@Override
	public boolean createCategory(Category category) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		builder.append("INSERT INTO library.category(category_name) ");
		builder.append("VALUES (?)");
		dml = builder.toString();
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, category.getCategory());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeCategory(Category category) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("DELETE FROM library.category ");
		builder.append(" WHERE category_name = ? ");
		dml = builder.toString();
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, category.getCategory());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				return true;
			} else {
				ps.close();
				con.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public Category fetchCategory(Category category) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("SELECT category_name FROM library.category ");
		builder.append(" WHERE category_name = ?");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, category.getCategory());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Category cat = new Category();
				cat.setCategory(rs.getString(1));
			}
			ps.close();
			rs.close();
			con.close();
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public boolean updateCategory(Category category, String oldName) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		builder.append("UPDATE library.category ");
		builder.append("SET category_name = ? ");
		builder.append("WHERE category_name = ?");
		dml = builder.toString();
		
		try {
			con = dbCon.getConnection();
			con.setAutoCommit(false);
			
			ps = con.prepareStatement(dml);
			ps.setString(1, category.getCategory());
			ps.setString(2, oldName);
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
