package exception;

import javafx.scene.control.Alert;

public class NotToBeColleagueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeColleagueException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
