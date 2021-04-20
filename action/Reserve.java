package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.MainClass;
import entities.*;

public class Reserve extends MyAction {

	public Reserve(String name, String surname, int idNum, int days, int value, int persones, int roomNumber, Hotel hotel) {
		super(name, surname, idNum, days, value, persones, roomNumber, hotel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = MainClass.getConnection();
			String sql = "INSERT INTO reservation(idGuest, idRoom, nufOfDays, Bill, roomNumber) VALUES(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			//ps.setInt(1, 1);
			ps.setInt(1, super.idNum);
			ps.setInt(2, super.persones);
			ps.setInt(3, super.days);
			ps.setInt(4, value*days);
			ps.setInt(5, roomNumber);
			ps.execute();
			
			if(Guest.findUser(idNum) == null) {
				sql = "INSERT INTO guest(Name, Surname, idCard) VALUES(?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, super.name);
				ps.setString(2, super.surname);
				ps.setInt(3, super.idNum);
				ps.execute();
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
