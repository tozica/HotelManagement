package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.MainClass;

public class RoomType {
	private int costForOne, costForTwo, costForThree, costForFour;
	
	RoomType(){
		readValues();
	}

	private void readValues() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {
			conn = MainClass.getConnection();
		
			String sql = "SELECT value FROM room WHERE numOfPerson = ? ";
			pr = conn.prepareStatement(sql);
			//pr.setInt(1, 1);
			//rs = pr.executeQuery();
			costForOne = execute(pr,rs, 1);
			costForTwo = execute(pr,rs, 2);
			costForThree = execute(pr,rs, 3);
			costForFour = execute(pr,rs, 4);
	
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			try {
				pr.close();
				//conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
		
	}

	private int execute(PreparedStatement pr, ResultSet rs, int i) throws SQLException {
		pr.setInt(1, i);
		rs = pr.executeQuery();
		int ret = rs.getInt(1);
		rs.close();
		return ret;
	}

	public int getCostForOne() {
		return costForOne;
	}

	public int getCostForTwo() {
		return costForTwo;
	}

	public int getCostForThree() {
		return costForThree;
	}

	public int getCostForFour() {
		return costForFour;
	}
}
