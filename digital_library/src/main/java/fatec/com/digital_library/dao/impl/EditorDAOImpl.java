package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.EditorDAO;
import fatec.com.digital_library.entity.Address;
import fatec.com.digital_library.entity.Editor;
import fatec.com.digital_library.utility.DatabaseConnection;

public class EditorDAOImpl implements EditorDAO {

	private String query;
	private String dml;
	private StringBuilder builder;
	
	@Override
	public boolean addEditor(Editor editor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeEditor(Editor editor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Editor fetchEditor(Editor editor) {
		
		return null;
	}

	@Override
	public List<Editor> fetchEditors() {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		ArrayList<Editor> editorList = new ArrayList<Editor>();
		
		builder.append("SELECT name, address_fk, telephone, cnpj FROM library.editor");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Editor editor = new Editor();
				editor.setName(rs.getString(1));
				editor.setCnpj(rs.getString(4));
				editor.setPhoneNumber(rs.getString(3));
				editor.setAddress(fetchAddressForEditor(rs.getInt(2)));
				
				if (editor.getAddress() == null) {
					throw new RuntimeException("No address for editor");
				}
				
				editorList.add(editor);
			}
			ps.close();
			rs.close();
			con.close();
			return editorList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Address fetchAddressForEditor(Integer addressId) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		Address address = null;
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("SELECT street_name, building_number, area_code, state, city  FROM library.address ");
		builder.append(  "WHERE address_id = ?");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, addressId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				address = new Address();
				address.setStreetName(rs.getString(1));
				address.setBuildingNumber(rs.getInt(2));
				address.setAreaCode(rs.getString(3));
				address.setState(rs.getString(4));
				address.setCity(rs.getString(5));
			}
			
			ps.close();
			rs.close();
			con.close();
			return address;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
}
