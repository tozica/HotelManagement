package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.MainClass;

public class Guest {
	private String name, surname;
	private int idCard;
	
	public Guest(String name, String surname, int idCard) {
		super();
		this.name = name;
		this.surname = surname;
		this.idCard = idCard;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public static Guest findUser(int idUser) {
		Connection conn = null;
		PreparedStatement pr = null;
		ResultSet rs = null;
		Guest guest = null;
		try {
			conn = MainClass.getConnection();
		
			String sql = "SELECT * FROM guest WHERE idCard = ? ";
			pr = conn.prepareStatement(sql);
			pr.setInt(1, idUser);
			
			rs = pr.executeQuery();
			
			if(rs.next() == false)
				return null;
			
			String name = rs.getString(1);
			String surname = rs.getString(2);
			int idCard = rs.getInt(3);
			
			guest = new Guest(name, surname, idCard);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			try {
				rs.close();
				pr.close();
				//conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
		
		return guest;
	}
}
