package view;

import java.util.ArrayList;

import controller.Driver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListUser {
	static ArrayList<String> names = new ArrayList<String>();
	public static void display() {
		int i;
		Stage List= new Stage();
		List.setTitle("User's List");
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        System.out.println("asdsad");
        names=Driver.ListUser();
        Label lab[]=new Label[names.size()];
        for(i=0;i<names.size();i++){
        	lab[i]= new Label(i+1+". "+names.get(i));
        	GridPane.setConstraints(lab[i], 20, i+1);
        }
        Button Back = new Button("Back to menu");
        GridPane.setConstraints(Back, 20, i+1);
        Back.setOnAction(e-> {
			List.close();
			MiniNet.showBack();
		});
        grid.getChildren().addAll(lab);
		grid.getChildren().add(Back);
		Scene scene = new Scene(grid,700,700);
		List.setScene(scene);
		List.show();
	}

}
