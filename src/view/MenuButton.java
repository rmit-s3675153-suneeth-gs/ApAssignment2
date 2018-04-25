package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import controller.AddUserActionListener;
import controller.ListActionListener;
import controller.SelectActionListener;

public class MenuButton extends JToolBar {
	private final static int BUTTON_COUNT=4;
	private JButton Buttons[] = new JButton[BUTTON_COUNT];
	String a[] ={"ListUser","SelectUser","AddUser","Exit"};
	public MenuButton(){
		JPanel Pane = new JPanel();
		Pane.setLayout(new BoxLayout(Pane, BoxLayout.Y_AXIS));
		for(int i =0;i<BUTTON_COUNT;i++){
			Buttons[i]=new JButton(a[i]);
			
			Pane.add(Buttons[i]);
		}
		add(Pane);
		Buttons[0].addActionListener(new ListActionListener());
		Buttons[1].addActionListener(new SelectActionListener());
		Buttons[2].addActionListener(new AddUserActionListener());
		
	}
}
