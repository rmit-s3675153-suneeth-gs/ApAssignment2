package view;

import java.util.ArrayList;

import database.StoreDatabase;
import exception.NoParentException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/*
 * AddParent - this class is used to add Parents to CHild and YoungChild
 */
public class AddParent {

	public static void display(String com_type, ArrayList<String> inputs){
		Stage parent = new Stage();
		parent.setTitle("Add Parent");
		Label lab[] = new Label[2];
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
		TextField text[] = new TextField[2];
		
		lab[0]= new Label("Parent 1");
		GridPane.setConstraints(lab[0], 0, 1);// label
		text[0] = new TextField();// text field
		GridPane.setConstraints(text[0], 1, 1);
		lab[1]= new Label("Parent 2");
		GridPane.setConstraints(lab[1], 0,2);
		text[1] = new TextField();
		GridPane.setConstraints(text[1],1,2);
		Button Enter =new Button("Enter");
		GridPane.setConstraints(Enter,1,3);
		Button Back =new Button("Back");
		GridPane.setConstraints(Back,1,4);
		
		grid.getChildren().addAll(lab);
		grid.getChildren().addAll(text);
		grid.getChildren().add(Back);
		grid.getChildren().add(Enter);
		
		Enter.setOnAction(e->{
			boolean status = false;
			try {
				status = StoreDatabase.check(text[0].getText(),text[1].getText(),"couple"); // checks whther the parents are couple or not
			} catch (NoParentException e1) {
				e1.printStackTrace();
			}
			String parent1 =text[0].getText();
			String parent2 = text[1].getText();
			if(status){
				if(com_type.equals("CHILD")||com_type.equals("YOUNGCHILD")){  // if satisfies added into the table
					StoreDatabase.insertParentTable(inputs.get(0),com_type,parent1,parent2,inputs);
					StoreDatabase.insertRelationTable(inputs.get(0), parent1, "parent");
					StoreDatabase.insertRelationTable(inputs.get(0), parent2, "parent");
				}
				StoreDatabase.selectPerson();
				parent.close();
				MiniNet.showBack();
			}
				
				
		});
		
		Back.setOnAction(e->{
			parent.close();
			MiniNet.showBack();
		});
		Scene scene = new Scene(grid,400,200);
		parent.setScene(scene);
		parent.show();
	}

}
