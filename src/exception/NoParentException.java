package exception;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class NoParentException extends Exception {

	/**
	 *  Author- Suneeth 
	 *  Description -when a child or young child has no parent or has only one parent, e.g.
		adding a child to one adult, or to two adults who are not a couple. That also happens when trying
		to delete an adult who has at least one dependent. (In this world a couple that have at least one kid
		become inseparable and immortal.)
	 *  
	 */
	private static final long serialVersionUID = 1L;
	public NoParentException(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Couple Adult is immortal with child - So cannot Delete")));
		alert.showAndWait();
	}

}
