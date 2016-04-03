package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.AutorDAO;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.utility.DatabaseConnection;

public class AutorDAOImpl implements AutorDAO {
	
	private String query;
	private String dml;
	private StringBuilder builder;
	
	@Override
	public List<Autor> fetchAutors() {
		List<Autor> autorList = new ArrayList<Autor>();
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		ResultSet rs;
		
		builder.append("SELECT name, date_of_death, date_of_birth, country_of_birth, state_of_birth, city_of_birth, country_of_death, state_of_death, city_of_death, biography FROM library.autor ");
		query = builder.toString();
			
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setName(rs.getString(1));
				java.util.Date javaDate = new java.sql.Date(rs.getDate(2).getTime());
				autor.setDeathDate(javaDate);
				javaDate = new java.sql.Date(rs.getDate(3).getTime());
				autor.setBirthDate(javaDate);
				autor.setCountryOfBirth(rs.getString(4));
				autor.setStateOfBirth(rs.getString(5));
				autor.setCityOfBirth(rs.getString(6));
				autor.setCountryOfDeath(rs.getString(7));
				autor.setStateOfDeath(rs.getString(8));
				autor.setCityOfDeath(rs.getString(9));
				autor.setBiography(rs.getString(10));
				autorList.add(autor);
			}	
			
			ps.close();
			rs.close();
			con.close();
			
			return autorList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autorList;
	}
}
