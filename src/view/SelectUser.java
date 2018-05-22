package view;
/*
 * Author - Vishal
 * Small window used to select users
 */
import controller.Driver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SelectUser  {
	static ComboBox<String> Name = new ComboBox<String>();
	static Button go = new Button("Go		");
	static Button close = new Button("Back		");
	static Stage select;
	public static void display() {
		select= new Stage();
		select.setTitle("Select User");
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
	    Name= new ComboBox<String>();
	    Name.getItems().addAll(Driver.ListUser());
	    Name.setPromptText("Choose ");
	    go.setOnAction(e->{
	    	select.close();
	    	UserWindow.display(Name.getValue());// gives names of the users who are there 
	    	
	    });
	    close.setOnAction(e-> {
			select.close();
			MiniNet.showBack();
		});
	    GridPane.setConstraints(Name, 0, 0);
	 	GridPane.setConstraints(go, 1,0);
	 	GridPane.setConstraints(close, 1, 1);
        grid.getChildren().addAll(Name);
		grid.getChildren().add(close);
		grid.getChildren().add(go);
		Scene scene = new Scene(grid,400,400);
		select.setScene(scene);
		select.show();
		
	}
	public static void showSelectUser(){
		select.show();
	}

}
