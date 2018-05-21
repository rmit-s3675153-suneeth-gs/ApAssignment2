package exception;

import javafx.scene.control.Alert;

public class NotToBeCoupledException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeCoupledException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
