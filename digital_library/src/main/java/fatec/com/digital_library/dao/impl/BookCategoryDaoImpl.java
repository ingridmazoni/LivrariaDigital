package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.BookCategoryDAO;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.utility.DatabaseConnection;

public class BookCategoryDaoImpl implements BookCategoryDAO {
	
	private StringBuilder builder = new StringBuilder();
	private String query;
	private String dml;
	
	@Override
	public List<Category> fetchCategoriesForBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		ArrayList<Category> categoryList = new ArrayList<Category>();
		
		builder.append("SELECT category.category_name FROM category, book_category, book");
		builder.append("  WHERE category.category_id = book_category.category_fk");
		builder.append("  AND book.book_id = book_category.book_fk");
		builder.append("  AND book.isbn = ?");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, book.getIsbn());
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
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String fetchCategoriesForBook(String isbn) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		StringBuilder categoryNameBuilder = new StringBuilder();
		
		builder.append("SELECT category.category_name FROM category, book_category, book");
		builder.append("  WHERE category.category_id = book_category.category_fk");
		builder.append("  AND book.book_id = book_category.book_fk");
		builder.append("  AND book.isbn = ?");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, isbn);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				categoryNameBuilder.append(rs.getString(1));
				
				if (!rs.isLast()) {
					categoryNameBuilder.append(", ");
				}
			}
			ps.close();
			rs.close();
			con.close();
			return categoryNameBuilder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean updateCategoriesForBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("DELETE FROM library.book_category ");
		builder.append(" WHERE book_fk = (SELECT book_id FROM library.book  ");
		builder.append("                   WHERE isbn = ?)");
		dml = builder.toString();
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, book.getIsbn());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				addCategoriesForBook(book);
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
	public boolean addCategoriesForBook(Book book) {
		DatabaseConnection dbCon;
		dbCon = new DatabaseConnection();
		Connection con = dbCon.getConnection();
		PreparedStatement ps = null;;
		try {
			for (Category category : book.getCategory()) {
				builder = new StringBuilder();
				builder.append("INSERT INTO library.book_category (book_fk, category_fk) ");
				builder.append("VALUES((SELECT book_id FROM library.book");
				builder.append("          WHERE isbn = ?) ");
				builder.append("     , (SELECT category_id FROM library.category ");
				builder.append("          WHERE category_name = ?))");
				
				String dml = builder.toString();
				
				try {
					con.setAutoCommit(false);
					ps = con.prepareStatement(dml);
					ps.setString(1, book.getIsbn());
					ps.setString(2, category.getCategory());
					
					if (ps.executeUpdate() > 0) {
						con.commit();
					} else {
						ps.close();
						con.rollback();
						con.close();
						return false;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ps.close();
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
