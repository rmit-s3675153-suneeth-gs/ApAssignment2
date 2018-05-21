package exception;

import javafx.scene.control.Alert;

public class NotToBeClassmateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeClassmateException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
