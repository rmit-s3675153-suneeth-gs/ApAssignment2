package exception;

import javafx.scene.control.Alert;

public class NotToBeFriendsException extends Exception {

	/**
	 *  Author- Suneeth 
	 *  Description -when trying to make an adult and a child friend or connect two
		children with an age gap larger than 3.
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeFriendsException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
