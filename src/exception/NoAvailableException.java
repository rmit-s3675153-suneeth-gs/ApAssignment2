package exception;

import javafx.scene.control.Alert;

public class NoAvailableException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAvailableException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}
