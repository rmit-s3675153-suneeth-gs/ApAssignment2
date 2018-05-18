package view;

import java.util.ArrayList;

import controller.Driver;
import database.StoreDatabase;
import exception.NoSuchAgeException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddUser {
	private static String user[]={"ADULT","CHILD","YOUNGCHILD"};
	static String gender[]= {"Male","Female"};
	static String state[]={"ACT", "NSW", "NT", "QLD", "SA", "TAS", "VIC","WA"};
	static TextField detailText[]=new TextField[4];
	static Label detailLabel[]=new Label[6];
	static Button AddUser;
	static Button Back;
	static ComboBox<String> Type;
	static ComboBox<String> Gender;
	static ComboBox<String> State;
	public static void display() {
		Stage Add= new Stage();
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
	    Type= new ComboBox<String>();
		Type.getItems().addAll(user);
		Type.setPromptText("Choose : ");
		GridPane.setConstraints(Type, 0, 0);
	    detailLabel[0] = new Label("Name");
	    detailText[0] =new TextField();
	    GridPane.setConstraints(detailLabel[0], 0, 1);
		GridPane.setConstraints(detailText[0], 1, 1);
		
		detailLabel[1] = new Label("Photo");
		detailLabel[1].setOpacity(80);
		detailText[1] =new TextField();
		GridPane.setConstraints(detailLabel[1], 0, 2);
		GridPane.setConstraints(detailText[1], 1, 2);
		
		detailLabel[2] = new Label("Age");
		detailText[2] =new TextField();
		GridPane.setConstraints(detailLabel[2], 0, 3);
		GridPane.setConstraints(detailText[2], 1, 3);
		
		detailLabel[3] = new Label("Status");
		detailText[3] =new TextField();
		GridPane.setConstraints(detailLabel[3], 0, 4);
		GridPane.setConstraints(detailText[3], 1, 4);
		
		detailLabel[4] = new Label("State");
		State =new ComboBox<String>();
		State.getItems().addAll(state);
		State.setPromptText("Choose : ");
		GridPane.setConstraints(detailLabel[4], 0, 5);
		GridPane.setConstraints(State, 1, 5);
		
		detailLabel[5] = new Label("Gender");
		Gender =new ComboBox<String>();
		Gender.getItems().addAll(gender);
		Gender.setPromptText("Choose : ");
		GridPane.setConstraints(detailLabel[5], 0, 6);
		GridPane.setConstraints(Gender, 1, 6);
		
		AddUser = new Button("Create User");
		Back = new Button("Back to Menu");
		GridPane.setConstraints(AddUser, 1, 17);
		GridPane.setConstraints(Back, 8, 17);
		
		AddUser.setOnAction(e->{
			ArrayList<String> Inputs = new ArrayList<String>();
			String com_type;
			com_type=Type.getValue();
			Inputs.add(detailText[0].getText());
			Inputs.add(detailText[1].getText());
			Inputs.add(detailText[2].getText());
			Inputs.add(detailText[3].getText());
			Inputs.add(State.getValue());
			Inputs.add(Gender.getValue());
			int age = Integer.parseInt(Inputs.get(2));
			try {
				Driver.User_Add_Check(com_type, Inputs);
				if(Driver.User_Null_Check(Inputs))
				if(com_type.equals("CHILD")||com_type.equals("YOUNGCHILD")){
					if(age>3&&age<=16)
						if(com_type.equals("YOUNGCHILD")){
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText("YoungChild cannot have age from 4-16!");
							alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Check Again !")));
							alert.showAndWait();
							return;
						}
							
					if(age<=3)
						if(com_type.equals("CHILD")){
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText("Child cannot have age from 0-3!");
							alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Check Again !")));
							alert.showAndWait();
							return;
						}
					if(age>16)
						if(com_type.equals("CHILD")||com_type.equals("YOUNGCHILD")){
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText("Not an Adult-Verify Age Limit!");
							alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Check Again !")));
							alert.showAndWait();
							return;
						}
					
					StoreDatabase.insertPersonTable(Inputs.get(0),Inputs.get(1),age,Inputs.get(3),Inputs.get(4),Inputs.get(5));
					AddParent.display(com_type,Inputs);
				}
				else{
					if(age>16)
						StoreDatabase.insertPersonTable(Inputs.get(0),Inputs.get(1),age,Inputs.get(3),Inputs.get(4),Inputs.get(5));
					else{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Adult-Age should be > 16Verify Age Limit!");
						alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Check Again !")));
						alert.showAndWait();
						return;
					}
				}
				Add.close();
				MiniNet.showBack();
				
			} catch (NoSuchAgeException e1) {
				System.out.println("Age Error");
			}
			
		});
		
		Back.setOnAction(e-> {
			Add.close();
			MiniNet.showBack();
		});
//		VBox layout = new VBox(20);
//		layout.setPadding(new Insets(0, 10, 0, 10));
//		VBox.setMargin(detailText[0], new Insets(0, 250, 0, 200));
		grid.getChildren().addAll(Type);
		grid.getChildren().addAll(detailLabel);
		grid.getChildren().addAll(detailText);
		grid.getChildren().addAll(State,Gender,AddUser,Back);
		Scene scene = new Scene(grid,700,700);
		Add.setScene(scene);
		Add.show();
		
		
	
	}

}
