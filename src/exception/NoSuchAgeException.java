package exception;


import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;


public class NoSuchAgeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchAgeException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("An exception occurred!");
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Age should be between 1 - 150")));
		alert.showAndWait();
	}
}
