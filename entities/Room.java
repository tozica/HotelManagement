package entities;

public class Room {
	private int roomNumber, roomType;
	private boolean free;
	public Room(int roomNumber, int roomType, boolean free) {
		super();
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.free = free;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getRoomType() {
		return roomType;
	}
	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
}
