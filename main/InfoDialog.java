package main;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InfoDialog extends Dialog{
	private Label label = new Label();
	private TextArea area = new TextArea();

	public InfoDialog(MainClass owner, String title, boolean modal) {
		super(owner, title, modal);
		// TODO Auto-generated constructor stub
		setSize(350, 100);
		setLayout(new GridLayout());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
	}
	public void setInfo(String info) {
		label.setText(info);
		add(label);
	}
	
	public void setInfoArea(String info) {
		area.setText(info);
		add(area);
	}
}
