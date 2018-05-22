package exception;

import javafx.scene.control.Alert;

public class NotToBeClassmateException extends Exception {

	/**
	 *  Author- Suneeth 
	 *  Description - when trying to make a young child in a classmate relation.
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeClassmateException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
