package fatec.com.digital_library.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CityStateLoader {
	
	private List<String> cityList = new ArrayList<String>();
	private List<String> stateList = new ArrayList<String>();
	
	public List<String> loadCity() {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		StringBuilder builder = new StringBuilder();
		String query;
		con = dbCon.getConnection();

		builder.append("SELECT city_name  FROM library.city ");
		query = builder.toString();

		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				cityList.add(rs.getString(1));
			}

			ps.close();
			rs.close();
			con.close();
			return cityList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> loadState() {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		StringBuilder builder = new StringBuilder();
		String query;
		con = dbCon.getConnection();

		builder.append("SELECT state  FROM library.state ");
		query = builder.toString();

		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				stateList.add(rs.getString(1));
			}

			ps.close();
			rs.close();
			con.close();
			return stateList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
