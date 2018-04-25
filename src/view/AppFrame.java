package view;

import java.awt.BorderLayout;

import javax.swing.*;

import controller.AddUserActionListener;
import controller.Driver;
import controller.ListActionListener;
import controller.SelectActionListener;

public class AppFrame extends JFrame {
	public static void main(String [] args){
		Driver d = new Driver();
		d.ReadDetailsFile();
		new AppFrame();
	}
	public AppFrame(){
		setSize(400,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new MenuButton());
		setVisible(true);
	}

}
