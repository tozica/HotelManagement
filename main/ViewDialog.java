package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewDialog extends Dialog{
	private Label label = new Label();
	private TextField text = new TextField();
	Button button = new Button("Ok");

	public ViewDialog(MainClass owner, String title, boolean modal) {
		super(owner, title, modal);
		// TODO Auto-generated constructor stub
		setSize(350, 200);
		setLayout(new BorderLayout());
		label.setText("Enter guest id card number !");
		add(label, BorderLayout.NORTH);
		add(text, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				owner.makeView(Integer.parseInt(text.getText()));
				
			}
		});
	}

}
