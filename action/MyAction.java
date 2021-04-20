package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Hotel;
import main.MainClass;

abstract public class MyAction {
	 protected String name;
	 protected String surname;
	 protected int idNum, days;
	 protected int value, persones, roomNumber;
	 protected Hotel hotel;
	 
	public MyAction(String name, String surname, int idNum, int days, int value, int persones, int roomNumber, Hotel hotel) {
		super();
		this.name = name;
		this.surname = surname;
		this.idNum = idNum;
		this.days = days;
		this.value = value;
		this.persones = persones;
		this.roomNumber = roomNumber;
		this.hotel = hotel;
	}
	 
	public static MyAction returnReservation(int idNum) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet set = null;
		
		try {
			conn = MainClass.getConnection();
			String sql = "SELECT * FROM reservation WHERE idGuest = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idNum);
			set = ps.executeQuery();
			if(set.next()) {
				 set.getInt(3); 
				set.getInt(4);
				 set.getDouble(5);
				 
				  set.getInt(6);
				return new Reserve(null, null, idNum, set.getInt(4), (int)set.getDouble(5), set.getInt(3), set.getInt(6), null);
			}

			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getIdNum() {
		return idNum;
	}

	public int getDays() {
		return days;
	}

	public int getValue() {
		return value;
	}

	public int getPersones() {
		return persones;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public Hotel getHotel() {
		return hotel;
	}


	public abstract void perform();
	 
}
