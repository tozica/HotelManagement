package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReserveActionDialog extends Dialog{

	private TextField name = new TextField();
	private TextField surname = new TextField();
	private TextField idCard = new TextField();
	private TextField days = new TextField();
	private TextField persones = new TextField();
	
	private Button reserve = new Button("Reserve");
	private Button cancel  = new Button("Call of");
	private MainClass owner = null;
	
	public ReserveActionDialog(MainClass owner, String title, boolean modal) {
		super(owner, title, modal);
		this.owner = owner;
		setSize(350, 350);
		setLayout(new GridLayout(6,1));
		addDialogComponent();
		addListener();

	}

	private void addDialogComponent() {
		// TODO Auto-generated method stub
		//Creating design for dialog window
		Panel namePanel = new Panel(new BorderLayout());
		Panel surnamePanel = new Panel(new BorderLayout());
		Panel idPanel = new Panel(new BorderLayout());
		Panel daysPanel = new Panel(new BorderLayout());
		Panel personesPanel = new Panel(new BorderLayout());
		
		Label nameLabel = new Label("Enter your first name");
		Label surnameLabel = new Label("Enter your last name");
		Label idLabel = new Label("Enter your id card number");
		Label daysLabel = new Label("Enter the desired number of days of stay");
		Label personesLabel = new Label("For how many people (max 4)");
		
		namePanel.add(nameLabel, BorderLayout.NORTH);
		namePanel.add(name, BorderLayout.CENTER);
		surnamePanel.add(surnameLabel, BorderLayout.NORTH);
		surnamePanel.add(surname, BorderLayout.CENTER);
		idPanel.add(idLabel, BorderLayout.NORTH);
		idPanel.add(idCard, BorderLayout.CENTER);
		daysPanel.add(daysLabel, BorderLayout.NORTH);
		daysPanel.add(days, BorderLayout.CENTER);
		personesPanel.add(personesLabel, BorderLayout.NORTH);
		personesPanel.add(persones, BorderLayout.CENTER);
	
		
		add(namePanel);
		add(surnamePanel);
		add(idPanel);
		add(daysPanel);
		add(personesPanel);
		
		//panel for buttons
		Panel tmpPanel = new Panel();
		tmpPanel.add(reserve);
		tmpPanel.add(cancel);
		
		add(tmpPanel);
		
	}

	private void addListener() {
		// TODO Auto-generated method stub
		// Windows closing action
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				dispose();
			}
		});
		
		reserve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				owner.makeReservation(name.getText(), surname.getText(), Integer.parseInt(idCard.getText()), Integer.parseInt(days.getText()),
						Integer.parseInt(persones.getText()));
				
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				owner.removeReservation(name.getText(), surname.getText(), Integer.parseInt(idCard.getText()), Integer.parseInt(days.getText()),
						Integer.parseInt(persones.getText()));
			}
		});
		
	}
	
}
