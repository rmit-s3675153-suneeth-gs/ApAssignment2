package exception;

import javafx.scene.control.Alert;

public class NotToBeCoupledException extends Exception {

	/**
	 *  Author- Suneeth 
	 *  Description - when trying to make a couple when at least one member is not an
		adult.
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeCoupledException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
