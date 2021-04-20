package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Guest;
import entities.Hotel;
import entities.Room;
import main.MainClass;

public class Canceling extends MyAction{

	public Canceling(String name, String surname, int idNum, int days, int value, int persones, int roomNumber, Hotel hotel) {
		super(name, surname, idNum, days, value, persones, roomNumber, hotel);
		// TODO Auto-generated constructor stub
	}

	public static int findReservation(int idNum) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet set = null;
		
		try {
			conn = MainClass.getConnection();
			String sql = "SELECT COUNT(*) FROM reservation WHERE idGuest = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idNum);
			set = ps.executeQuery();
			if(set.next()) {
				return set.getInt(1);
			}

			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public void perform() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			conn = MainClass.getConnection();
			String sql = "SELECT * FROM reservation WHERE idGuest = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idNum);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Room tmp = new Room(rs.getInt(6), rs.getInt(3), true);
				super.hotel.addRoom(tmp);
				super.hotel.updateRoom(true, tmp.getRoomNumber());
			}
			
			sql = "DELETE FROM reservation WHERE idGuest = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, super.idNum);
			ps.execute();
		
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
