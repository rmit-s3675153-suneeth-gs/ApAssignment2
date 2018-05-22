package exception;

import javafx.scene.control.Alert;

public class NotToBeColleagueException extends Exception {

	/**
	 *  Author- Suneeth 
	 *  Description -when trying to connect a child in a colleague relation.
	 */
	private static final long serialVersionUID = 1L;
	public NotToBeColleagueException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
