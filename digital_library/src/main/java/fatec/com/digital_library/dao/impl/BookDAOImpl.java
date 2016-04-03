package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.utility.DatabaseConnection;

public class BookDAOImpl implements BookDAO {

	private String query;
	private String dml;
	private StringBuilder builder;
	

	@Override
	public boolean addBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		builder.append("INSERT INTO library.book( ");
		builder.append("title, format, editor_fk, page_number, publication_date, summary, idx, sale_price, ");
		builder.append("stock, cost_price, profit_margin, isbn) ");
		builder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		dml = builder.toString();

		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getFormat());
			ps.setString(3, book.getEditor().getName());
			ps.setShort(4,  book.getPageNumber());
			java.sql.Date sqlDate = new java.sql.Date(book.getPublicationDate().getTime());
			ps.setDate(5, sqlDate);
			ps.setString(6, book.getSummary());
			ps.setString(7, book.getIndex());
			ps.setDouble(8, book.getSalePrice());
			ps.setInt(9, book.getStockQuantity());
			ps.setDouble(10, book.getCostPrice());
			ps.setDouble(11, book.getProfitMargin());
			ps.setString(12, book.getIsbn());
			
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				if (addAutorsForBook(book)) {
					if (addCategoriesForBook(book)){
						con.close();
						return true;
					} else {
						con.rollback();
						con.close();
						return false;
					}
				} else {
					con.rollback();
					con.close();
					return false;
				}
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("DELETE FROM library.book ");
		builder.append(" WHERE isbn = ? ");
		dml = builder.toString();
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, book.getIsbn());
			
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
	public boolean fetchBook(Book book) {
		return false;
	}

	@Override
	public boolean fetchBooks() {
		return false;
	}
	
	public boolean addAutorsForBook(Book book) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			DatabaseConnection dbCon;
			dbCon = new DatabaseConnection();
			Connection con = dbCon.getConnection();
			con.setAutoCommit(false);
			
			for (Autor autor : book.getAutorList()) {
				builder = new StringBuilder();
				builder.append("INSERT INTO library.book_autor (book_fk, autor_fk) ");
				builder.append("VALUES((SELECT book_id FROM library.book");
				builder.append("          WHERE title = ?) ");
				builder.append("     , (SELECT autor_id FROM library.autor ");
				builder.append("          WHERE name = ? AND city_of_birth = ? AND state_of_birth = ?))");
				
				String dml = builder.toString();
				
				try {
					ps = con.prepareStatement(dml);
					ps.setString(1, book.getTitle());
					ps.setString(2, autor.getName());
					ps.setString(3, autor.getCityOfBirth());
					ps.setString(4, autor.getStateOfBirth());
					
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
	
//	public List<Category> fetchCategoriesForBook(Book book) {
//		List<Category> categoryList = new ArrayList<Category>();
//		DatabaseConnection dbCon;
//		PreparedStatement ps;
//		Connection con;
//		dbCon = new DatabaseConnection();
//		builder = new StringBuilder();
//		con = dbCon.getConnection();
//		ResultSet rs;
//		
//		for (Category category : book.getCategory()) {
//			builder.append("SELECT category_id FROM library.category ");
//			builder.append(" WHERE category_name = ?");
//			query = builder.toString();
//			
//			try {
//				ps = con.prepareStatement(query);
//				ps.setString(1, category.getCategory());
//				rs = ps.executeQuery();
//				
//				while (rs.next()) {
//					Category categoryWithIds = new Category();
//					categoryWithIds.setId(rs.getInt(1));
//					categoryList.add(categoryWithIds);
//				}			
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return categoryList;
//		}
//		return categoryList;
//	}
	
	/*public Integer fetchAutorsForBook(Book book, Connection con) {
		Integer bookAutorId = 0;
		builder = new StringBuilder();
		builder.append("SELECT book_autor_id FROM ");
		builder.append("library.book_autor, library.book, library.autor ");
		builder.append("WHERE book.book_id = book_autor.book_fk ");
		builder.append("AND autor.autor_id = book_autor.autor_fk ");
		builder.append("")
		String query
		ps = con.prepareStatement(sql)
		return bookAutorId;
	}*/

}
