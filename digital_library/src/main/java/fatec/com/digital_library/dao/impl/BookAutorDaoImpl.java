package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.BookAutorDAO;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.utility.DatabaseConnection;

public class BookAutorDaoImpl implements BookAutorDAO {
	
	private StringBuilder builder = new StringBuilder();
	private String query;
	private String dml;
	
	@Override
	public List<Autor> fetchAutorsForBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		ArrayList<Autor> autorList = new ArrayList<Autor>();
		
		builder.append("SELECT autor.name FROM autor, book_autor, book");
		builder.append("  WHERE autor.autor_id = book_autor.autor_fk");
		builder.append("  AND book.book_id = book_autor.book_fk");
		builder.append("  AND book.isbn = ?");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, book.getIsbn());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setName(rs.getString(1));
				autorList.add(autor);
			}
			
			ps.close();
			rs.close();
			con.close();
			return autorList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String fetchAutorsForBook(String isbn) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		StringBuilder autorNameBuilder = new StringBuilder();
		
		builder.append("SELECT autor.name FROM autor, book_autor, book");
		builder.append("  WHERE autor.autor_id = book_autor.autor_fk");
		builder.append("  AND book.book_id = book_autor.book_fk");
		builder.append("  AND book.isbn = ?");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, isbn);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				autorNameBuilder.append(rs.getString(1));
				
				if (!rs.isLast()) {
					autorNameBuilder.append(", ");
				}
			}
			ps.close();
			rs.close();
			con.close();
			return autorNameBuilder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addAutorsForBook(Book book) {
		PreparedStatement ps = null;
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
				builder.append("          WHERE name = ?))");
				
				String dml = builder.toString();
				
				try {
					ps = con.prepareStatement(dml);
					ps.setString(1, book.getTitle());
					ps.setString(2, autor.getName());
					
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
	
	@Override
	public boolean updateAutorsForBook(Book book) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("DELETE FROM library.book_autor ");
		builder.append(" WHERE book_fk = (SELECT book_id FROM library.book ");
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
				addAutorsForBook(book);
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

}
