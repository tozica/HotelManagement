package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import action.Canceling;
import action.MyAction;
import action.Reserve;
import main.MainClass;

public class Hotel {
	private int onePersone, twoPersones, threePersones, fourPersones;
	private String name;
	private MyAction action;
	private RoomType room = new RoomType();
	private LinkedList<Room> one, two, three, four;
	public Hotel() {
		one = new LinkedList<Room>();
		two = new LinkedList<Room>();
		three = new LinkedList<Room>();
		four = new LinkedList<Room>();
		readData();
		readRooms();
		
	}
	
	private void readRooms() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {
			conn = MainClass.getConnection();
		
			String sql = "SELECT * FROM room_rent WHERE free = ? ";
			pr = conn.prepareStatement(sql);
			pr.setInt(1, 1);
			rs = pr.executeQuery();
			while(rs.next()) {
				Room tmp = new Room(rs.getInt(2), rs.getInt(3), rs.getInt(4) == 1 ? true : false);
				addRoom(tmp);
			}
			

			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void updateRoom(boolean bool, int roomNum) {
		Connection conn = null;
		PreparedStatement pr = null;
		try {
			conn = MainClass.getConnection();
			String sql = "UPDATE room_rent SET free = ? WHERE roomNum = ? ";
			pr = conn.prepareStatement(sql);
			pr.setInt(1, bool ? 1 : 0);
			pr.setInt(2, roomNum);

			pr.execute();

			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addRoom(Room tmp) {
		switch (tmp.getRoomType()) {
		case 1:
			one.add(tmp);
			break;
		case 2:
			two.add(tmp);
			break;
		case 3:
			three.add(tmp);
			break;
		case 4:
			four.add(tmp);
			break;
		default:
			break;
		}
		
	}

	private void readData() {
		Connection conn = null;
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {
			conn = MainClass.getConnection();
		
			String sql = "SELECT * FROM hotel WHERE ID = ? ";
			pr = conn.prepareStatement(sql);
			pr.setInt(1, 1);
			
			rs = pr.executeQuery();
			
			name = rs.getString(2);
			onePersone = rs.getInt(3);
			twoPersones = rs.getInt(4);
			threePersones = rs.getInt(5);
			fourPersones = rs.getInt(6);
			
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
	}
	

	

	public void makeReservation(String name, String surname, int idNum, int days, int persones, int bill, int roomNumber) {
		// TODO Auto-generated method stub
		
		
		action = new Reserve(name, surname, idNum, days, bill, persones, roomNumber, this);
		action.perform();
		
	}
	public void removeReservation(String name, String surname, int idNum, int days, int value) {
		// TODO Auto-generated method stub
		action = new Canceling(name, surname, idNum, days, 0, 0, 0, this);
		action.perform();
		
	}

	
	public MyAction getAction() {
		return action;
	}
	public RoomType getRoom() {
		return room;
	}
	public int getOnePersone() {
		return onePersone;
	}
	public void setOnePersone(int onePersone) {
		this.onePersone = onePersone;
	}
	public int getTwoPersones() {
		return twoPersones;
	}
	public void setTwoPersones(int twoPersones) {
		this.twoPersones = twoPersones;
	}
	public int getThreePersones() {
		return threePersones;
	}
	public void setThreePersones(int threePersones) {
		this.threePersones = threePersones;
	}
	public int getFourPersones() {
		return fourPersones;
	}
	public void setFourPersones(int fourPersones) {
		this.fourPersones = fourPersones;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Room> getOne() {
		return one;
	}
	public LinkedList<Room> getTwo() {
		return two;
	}
	public LinkedList<Room> getThree() {
		return three;
	}
	public LinkedList<Room> getFour() {
		return four;
	}

}
