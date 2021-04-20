package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import action.Canceling;
import action.MyAction;
import action.Reserve;
import entities.Guest;
import entities.Hotel;
import entities.Room;

public class MainClass extends Frame{
	
	private static Connection connection = null;
	private static String nameDb = null;
	private MainClass me = this;
	private Hotel hotel = null;
	
	private ReserveActionDialog reserveAction;
	
	private Button reserve = new Button("Reserve");
	private Button view = new Button("View");
	private Panel infoPanel = new Panel(new GridLayout(1, 3));
	
	private Label oneVal;
	private Label twoVal;
	private Label threeVal;
	private Label fourVal;
	 


	public MainClass(String path) throws IOException, ClassNotFoundException, SQLException {
		MainClass.setNameDb(path);
		MainClass.getConnection();
		hotel = new Hotel();
		prepareGui();
		addComponents();
		addListeners();
		
	}
	
private void addListeners() {
		// TODO Auto-generated method stub
		//Windows closing listener
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					me.persistAll();
					MainClass.getConnection().close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				dispose();
			}
		});
		
		//Button reserve listener
		reserve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reserveAction = new ReserveActionDialog(me, "Reservation", false);
				reserveAction.setVisible(true);
			}
		});
		
		
		view.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ViewDialog viewd = new ViewDialog(me, "Reservation info", false);
				viewd.setVisible(true);
				
			}
		});
			
	}

protected void persistAll() {
	Connection conn = null;
	PreparedStatement pr = null;
	try {
		conn = MainClass.getConnection();
		String sql = "UPDATE hotel SET numOf1pR = ?, numOf2pR = ?, numOf3pR = ?, numOf4pR = ? WHERE ID = ? ";
		pr = conn.prepareStatement(sql);
		pr.setInt(1, hotel.getOnePersone());
		pr.setInt(2, hotel.getTwoPersones());
		pr.setInt(3, hotel.getThreePersones());
		pr.setInt(4, hotel.getFourPersones());
		pr.setInt(5, 1);

		pr.execute();

		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		try {
			pr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}

private void addLabels() {
		// info panel
		Panel tmpNameOfCol = new Panel(new GridLayout(5,1));
		Panel tmpValueOfCol = new Panel(new GridLayout(5,1));
		Panel costPanel = new Panel(new GridLayout(5,1));
		
		Label text = new Label("Stats for free rooms:");
		Label one = new Label();
		Label two = new Label();
		Label three = new Label();
		Label four = new Label();
		
		oneVal = new Label("" + hotel.getOnePersone());
		twoVal = new Label("" + hotel.getTwoPersones());
		threeVal = new Label("" + hotel.getThreePersones());
		fourVal = new Label("" + hotel.getFourPersones());
		
		Label textCost = new Label("Dollars per night");
		Label oneCost = new Label("" + hotel.getRoom().getCostForOne());
		Label twoCost = new Label("" + hotel.getRoom().getCostForTwo());
		Label threeCost = new Label("" + hotel.getRoom().getCostForThree());
		Label fourCost = new Label("" + hotel.getRoom().getCostForFour());
		
		costPanel.add(textCost);
		costPanel.add(oneCost);
		costPanel.add(twoCost);
		costPanel.add(threeCost);
		costPanel.add(fourCost);
				
		
		one.setText("Single room");
		two.setText("Double room");
		three.setText("Triple room");
		four.setText("Quadruple room");
		
		tmpNameOfCol.add(text);
		tmpNameOfCol.add(one);
		tmpNameOfCol.add(two);
		tmpNameOfCol.add(three);
		tmpNameOfCol.add(four);
		
		tmpValueOfCol.add(new Label("Empty"));
		tmpValueOfCol.add(oneVal);
		tmpValueOfCol.add(twoVal);
		tmpValueOfCol.add(threeVal);
		tmpValueOfCol.add(fourVal);
		

		
		infoPanel.add(tmpNameOfCol);
		infoPanel.add(tmpValueOfCol);
		infoPanel.add(costPanel);
				
}

	private void addComponents() {
		
		Panel namePanel = new Panel(new GridLayout());
		Label nameOfHotel = new Label("Hotel " + hotel.getName(), Font.BOLD);
		
		namePanel.add(nameOfHotel);
		
		//info panel
		addLabels();
		addButtons();
		
		
		
		add(infoPanel, BorderLayout.CENTER);
		add(namePanel, BorderLayout.NORTH);
		

	}

	private void addButtons() {
		// gui for buttons reserve and view
		Panel tmpPanel = new Panel(new GridLayout(5,1));
		Panel tmpPanel1 = new Panel();
		
		Panel reservePanel = new Panel();
		Panel viewReservation = new Panel();
		
		
		reservePanel.add(reserve);
		viewReservation.add(view);
		
		
		tmpPanel1.add(reservePanel);
		tmpPanel1.add(viewReservation);
		
		tmpPanel.add(new Label());
		tmpPanel.add(new Label());
		tmpPanel.add(tmpPanel1);
		
		add(tmpPanel, BorderLayout.EAST);
		
	}

	private void prepareGui() {
		setTitle("Reservation program");
		setSize(800, 600);
		setResizable(true);
		setLayout(new BorderLayout());
		setVisible(true);
		
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		if(connection == null) {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+ nameDb);
		}
		
		
		return connection;
	}

	private static void moveDB(String source_) throws IOException {
		String dest = System.getProperty("user.dir");
		
		Path source = Paths.get(source_);
		Path newdir = Paths.get(dest);
		Files.copy(source, newdir.resolve(nameDb), StandardCopyOption.REPLACE_EXISTING);
		//mainClass.setNameDb(source);
	}

	public void makeReservation(String name, String surname, int idNum, int days, int value) {
		// TODO Auto-generated method stub
		int bill = 0;
		int avSpace = 0;
		Room room = null;
		switch (value) {
		case 1:
			bill = hotel.getRoom().getCostForOne();
			avSpace = hotel.getOnePersone();
			if(avSpace != 0) {
				hotel.setOnePersone(hotel.getOnePersone() - 1);
				this.setOneVal(hotel.getOnePersone());
				room = hotel.getOne().getFirst();
				hotel.getOne().removeFirst();
			}
			break;
		case 2:
			bill = hotel.getRoom().getCostForTwo();
			avSpace = hotel.getTwoPersones();
			if(avSpace != 0) {
				hotel.setTwoPersones(hotel.getTwoPersones() - 1);
				this.setTwoVal(hotel.getTwoPersones());
				room = hotel.getTwo().getFirst();
				hotel.getTwo().removeFirst();
			}
			break;
		case 3:
			bill = hotel.getRoom().getCostForThree();
			avSpace = hotel.getThreePersones();
			if(avSpace != 0) {
				hotel.setThreePersones(hotel.getThreePersones() - 1);
				this.setThreeVal(hotel.getThreePersones());
				room = hotel.getThree().getFirst();
				hotel.getThree().removeFirst();
			}
			break;
		case 4:
			bill = hotel.getRoom().getCostForFour();
			avSpace = hotel.getFourPersones();
			if(avSpace != 0) {
				hotel.setFourPersones(hotel.getFourPersones() - 1);
				this.setFourVal(hotel.getFourPersones());
				room = hotel.getFour().getFirst();
				hotel.getFour().removeFirst();
			}
			break;

		default:
			break;
		}
		
		if(avSpace == 0) {
			InfoDialog info = new InfoDialog(this, "No space", false);
			info.setInfo("We have no rooms available now for selected number of persones");
			info.setVisible(true);
		}
		else {
			hotel.makeReservation(name, surname, idNum, days, value, bill, room.getRoomNumber());
			hotel.updateRoom(false, room.getRoomNumber());
			InfoDialog info = new InfoDialog(this, "Success", false);
			info.setInfo("You have successfully booked a room, your bill is " + bill*days + " dollars!");
			info.setVisible(true);
		}
	}
	
	public void removeReservation(String name, String surname, int idNum, int days, int value) {
		// TODO Auto-generated method stub
		int add = Canceling.findReservation(idNum);
		if(add == 0) {
			InfoDialog info = new InfoDialog(this, "Reservation does not exist", false);
			info.setInfo("reservation with this user does not exist");
			info.setVisible(true);
			return;
		}
		
		switch (value) {
		case 1:
			hotel.setOnePersone(hotel.getOnePersone() + add);
			this.setOneVal(hotel.getOnePersone());
			break;
		case 2:
			hotel.setTwoPersones(hotel.getTwoPersones() + add);
			this.setTwoVal(hotel.getTwoPersones());
			break;
		case 3:
			hotel.setThreePersones(hotel.getThreePersones() + add);
			this.setThreeVal(hotel.getThreePersones());
			break;
		case 4:
			hotel.setFourPersones(hotel.getFourPersones() + add);
			this.setFourVal(hotel.getFourPersones());
			break;

		default:
			break;
		}
		
		hotel.removeReservation(name, surname, idNum, days, value);
	}

	public void makeView(int card) {
		// TODO Auto-generated method stub
		InfoDialog dialog = new InfoDialog(me, "Details", false);
		StringBuilder sb = new StringBuilder();
		sb.append("Reservation info for ");
		Guest guest = Guest.findUser(card);
		MyAction reservation = MyAction.returnReservation(card);
		sb.append(guest.getName());
		sb.append(" ");
		sb.append(guest.getSurname());
		sb.append(":\n");
		sb.append("Number of days: ");
		sb.append(reservation.getDays());
		sb.append("\n");
		sb.append("Number of persones: ");
		sb.append(reservation.getPersones());
		sb.append("\n");
		sb.append("Room number: ");
		sb.append(reservation.getRoomNumber());
		sb.append("\n");
		sb.append("Bill: $");
		sb.append(reservation.getValue());
		dialog.setInfoArea(sb.toString());
		dialog.resize(250,200);
		dialog.setVisible(true);

	}

	public static void setNameDb(String path) {
		MainClass.nameDb = path;
	}
	
	public void setOneVal(int oneVal) {
		this.oneVal.setText("" + oneVal);
	}

	public void setTwoVal(int twoVal) {
		this.twoVal.setText("" + twoVal);
	}

	public void setThreeVal(int threeVal) {
		this.threeVal.setText("" + threeVal);
	}

	public void setFourVal(int fourVal) {
		this.fourVal.setText("" + fourVal);
	}
	
	public static void main(String[] args) {
	
		try {		
			new MainClass(args[0]);
	
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}






}
