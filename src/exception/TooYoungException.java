package exception;

import javafx.scene.control.Alert;

public class TooYoungException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooYoungException(String string){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("TooYoung cannot make friends!");
		alert.showAndWait();
	}

}
