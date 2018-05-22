package exception;

import javafx.scene.control.Alert;

public class NoAvailableException extends Exception {
	/**
	 * Author- Suneeth - Description -when trying to make two adults a couple and at least one of them is
already connected with another adult as a couple.
	 */
	private static final long serialVersionUID = 1L;

	public NoAvailableException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}
