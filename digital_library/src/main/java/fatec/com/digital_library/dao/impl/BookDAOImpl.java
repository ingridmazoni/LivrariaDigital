package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.utility.DatabaseConnection;

public class BookDAOImpl implements BookDAO {

	private String query;
	private String dml;
	private StringBuilder builder;
	

	@Override
	public boolean addBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		ResultSet rs;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		System.out.println("FOI");
		builder.append("INSERT INTO library.book( ");
		builder.append("title, format, editor_fk, page_number, publication_date, summary, idx, sale_price, ");
		builder.append("stock, cost_price, profit_margin, category_fk, isbn) ");
		builder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
			ps.setDouble(8,  book.getSalePrice());
			ps.setInt(9, book.getStockQuantity());
			ps.setDouble(10, book.getCostPrice());
			ps.setDouble(11, book.getProfitMargin());
			ps.setString(12, book.getCategory().getCategory());
			ps.setString(13, book.getIsbn());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				if (addAutorsForBook(book)) {
					return true;
				} else {
					ps.close();
					con.rollback();
					con.close();
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeBook(Book book) {
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
		DatabaseConnection dbCon;
		dbCon = new DatabaseConnection();
		Connection con = dbCon.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		for (Autor autor : book.getAutorList()) {
			builder = new StringBuilder();
			builder.append("INSERT INTO library.book_autor (book_fk, autor_fk) ");
			builder.append("VALUES((SELECT book_id FROM library.book");
			builder.append("          WHERE title = ?) ");
			builder.append("     , (SELECT autor_id FROM library.autor ");
			builder.append("          WHERE name = ? AND surname = ?))");
			
			String dml = builder.toString();
			
			try {
				con.setAutoCommit(false);
				ps = con.prepareStatement(dml);
				ps.setString(1, book.getTitle());
				ps.setString(2, autor.getName());
				ps.setString(3, autor.getSurName());
				
				if (ps.executeUpdate() > 0) {
					ps.close();
					con.commit();
					con.close();
					return true;
				} else {
					ps.close();
					con.rollback();
					System.out.println("rollback");
					con.close();
					return false;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
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
