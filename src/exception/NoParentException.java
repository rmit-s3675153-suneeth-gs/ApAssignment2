package exception;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class NoParentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoParentException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("An exception occurred!");
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Couple Adult is immortal with child - So cannot Delete")));
		alert.showAndWait();
	}

}
