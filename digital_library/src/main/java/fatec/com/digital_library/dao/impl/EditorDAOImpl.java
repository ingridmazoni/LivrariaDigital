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
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		con = dbCon.getConnection();

		try {
			con.setAutoCommit(false);
			if (addAddress(editor.getAddress())) {
				builder = new StringBuilder();
				builder.append("INSERT INTO library.editor (name, address_fk, telephone, cnpj) ");
				builder.append(" VALUES (?,?,?,?)");
				dml = builder.toString();
				ps = con.prepareStatement(dml);
				ps.setString(1, editor.getName());
				ps.setInt(2, getAddressId(editor.getAddress()));
				ps.setString(3, editor.getPhoneNumber());
				ps.setString(4, editor.getCnpj());

				if (ps.executeUpdate() > 0) {
					ps.close();
					con.commit();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeEditor(Editor editor) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();

		builder.append("DELETE FROM library.editor ");
		builder.append(" WHERE cnpj = ? ");
		dml = builder.toString();

		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, editor.getCnpj());

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
	public Editor fetchEditor(Editor editor) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		Editor edit = null;
		builder.append("SELECT cnpj FROM library.editor WHERE cnpj = ?");
		query = builder.toString();

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, editor.getCnpj());
			rs = ps.executeQuery();

			while (rs.next()) {
				edit = new Editor();
				edit.setCnpj(rs.getString(1));
			}

			ps.close();
			rs.close();
			con.close();
			return edit;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro em buscar editora");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		System.out.println("Editora nao cadastrada");
		return null;
	}

	public boolean addAddress(Address address) {
		if (getAddressId(address) <= 0) {
			DatabaseConnection dbCon;
			PreparedStatement ps;
			Connection con;
			dbCon = new DatabaseConnection();
			builder = new StringBuilder();
			con = dbCon.getConnection();
			builder.append("INSERT INTO library.address(street_name, building_number, area_code, state, city) ");
			builder.append("VALUES (?, ?, ?, ?, ?)");
			dml = builder.toString();

			try {
				con.setAutoCommit(false);
				ps = con.prepareStatement(dml);
				ps.setString(1, address.getStreetName());
				ps.setInt(2, address.getBuildingNumber());
				ps.setString(3, address.getAreaCode());
				ps.setString(4, address.getState());
				ps.setString(5, address.getCity());

				if (ps.executeUpdate() > 0) {
					ps.close();
					con.commit();
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			System.out.println("Endereco ja existe ou houve erro. Verifique");
			return true;
		}
	}

	public Integer getAddressId(Address address) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		Integer id = 0;

		builder.append(
				"SELECT address_id FROM library.address WHERE street_name = ? AND building_number = ? AND area_code = ?");
		query = builder.toString();

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, address.getStreetName());
			ps.setInt(2, address.getBuildingNumber());
			ps.setString(3, address.getAreaCode());
			rs = ps.executeQuery();

			while (rs.next()) {
				id = rs.getInt(1);
			}

			ps.close();
			rs.close();
			con.close();

			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro em getAddressID");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		System.out.println("Address ID nulo");
		return -1;
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
		builder.append("WHERE address_id = ?");
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

	@Override
	public boolean updateEditor(Editor editor, String cnpj) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE library.editor ");
		builder.append("SET name = ?, telephone = ?, cnpj = ? ");
		builder.append("WHERE cnpj = ?");

		try {
			if (updateAddress(editor.getAddress(), cnpj)) {
				con = dbCon.getConnection();
				con.setAutoCommit(false);

				ps = con.prepareStatement(builder.toString());
				ps.setString(1, editor.getName());
				ps.setString(2, editor.getPhoneNumber());
				ps.setString(3, editor.getCnpj());
				ps.setString(4, cnpj);

				if (ps.executeUpdate() > 0) {
					ps.close();
					con.commit();
					con.close();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateAddress(Address address, String cnpj) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		builder.append("UPDATE library.address ");
		builder.append("SET street_name = ?, building_number = ?, area_code = ?, state = ?, city = ? ");
		builder.append("WHERE street_name = ? ");
		builder.append("AND building_number = ? ");
		builder.append("AND area_code = ? ");
		dml = builder.toString();
		Address adrs = fetchAddressForEditor(cnpj);
		try {
			con = dbCon.getConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(dml);
			ps.setString(1, address.getStreetName());
			ps.setInt(2, address.getBuildingNumber());
			ps.setString(3, address.getAreaCode());
			ps.setString(4, address.getState());
			ps.setString(5, address.getCity());
			ps.setString(6, adrs.getStreetName());
			ps.setInt(7, adrs.getBuildingNumber());
			ps.setString(8, adrs.getAreaCode());

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

	public Address fetchAddressForEditor(String cnpj) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		Address adrs = null;
		builder = new StringBuilder();
		con = dbCon.getConnection();

		builder.append("SELECT street_name, building_number, area_code, state, city  FROM library.address ");
		builder.append("WHERE address_id = (SELECT address_fk FROM library.editor WHERE cnpj = ?)");
		query = builder.toString();

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, cnpj);
			rs = ps.executeQuery();

			while (rs.next()) {
				adrs = new Address();
				adrs.setStreetName(rs.getString(1));
				adrs.setBuildingNumber(rs.getInt(2));
				adrs.setAreaCode(rs.getString(3));
				adrs.setState(rs.getString(4));
				adrs.setCity(rs.getString(5));
			}

			ps.close();
			rs.close();
			con.close();
			return adrs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adrs;
	}
}
