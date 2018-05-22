package exception;



import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;


public class NoSuchAgeException extends Exception {
	/**
	 *  Author- Suneeth 
	 *  Description - when trying to enter a person whose age is negative or over 150. (You
		can treat age as integer)
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchAgeException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Age Error Enter from 1 - 150 !");
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Age should be between 1 - 150")));
		alert.showAndWait();
	}
}
