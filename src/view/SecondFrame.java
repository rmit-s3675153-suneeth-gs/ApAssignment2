package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

import controller.Driver;

public class SecondFrame extends JFrame {
	ArrayList<String> Names = new ArrayList<String>();
	
	public SecondFrame(){
		setSize(400,400);
		Names=Driver.ListUser();
		JPanel Pane = new JPanel();
		Pane.setLayout(new BoxLayout(Pane, BoxLayout.Y_AXIS));
		JLabel Label[] =new JLabel[Names.size()];
		for(int i =0;i<Names.size();i++){
			Label[i] = new JLabel(Names.get(i));
			Pane.add(Label[i]);
		}
		add(Pane);
		setVisible(true);
	}

}
