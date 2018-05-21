package view;

import java.util.ArrayList;
import java.util.Optional;

import controller.Driver;
import database.StoreDatabase;
import exception.NoAvailableException;
import exception.NoParentException;
import exception.NoSuchAgeException;
import exception.NotToBeClassmateException;
import exception.NotToBeColleagueException;
import exception.NotToBeCoupledException;
import exception.NotToBeFriendsException;
import exception.TooYoungException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class UserWindow {
		static Scene Add,scene;
		static Stage UseWin;
		static String  name;
	public static void display(String value) {
		UseWin= new Stage();
		name =value;
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        Button b1= new Button("   Add Relation		");
        Button b2= new Button("Show Parents/Child	");
        Button b3= new Button("Find Direct Relation ");
        Button b4= new Button("   Delete Account	");
        Button b5= new Button("     Display User	");
        Button b6= new Button("       Exit			");
	   
		GridPane.setConstraints(b1, 10, 0);
		GridPane.setConstraints(b2, 10, 1);
		GridPane.setConstraints(b3, 10, 2);
		GridPane.setConstraints(b4, 10, 3);
		GridPane.setConstraints(b5, 10, 4);
		GridPane.setConstraints(b6, 10, 5);
		

		
		b1.setOnAction(e-> AddRelation(scene));
		b2.setOnAction(e-> ShowParents_Child(scene));
		b3.setOnAction(e-> Direct_Relation(scene));
		b4.setOnAction(e-> Delete(scene));
		b5.setOnAction(e-> DisplayUser(scene));
		
		b6.setOnAction(e-> {
			UseWin.close();
			SelectUser.showSelectUser();
		});
//		VBox layout = new VBox(20);
//		layout.setPadding(new Insets(0, 10, 0, 10));
//		VBox.setMargin(detailText[0], new Insets(0, 250, 0, 200));
		grid.getChildren().addAll(b1,b2,b3,b4,b5,b6);
		scene = new Scene(grid,600,600);
		UseWin.setScene(scene);
		UseWin.show();
		
		
	}
	private static void ShowParents_Child(Scene scene) {
			ArrayList<String> Par_Child_List = new ArrayList<String>();
			Par_Child_List = Driver.Parent_Child(name);
			int size = Par_Child_List.size(),i;
			
			GridPane grid = grid();
	        Label[] lab = new Label[size];
	        for(i =0;i< size ;i++){
	        	lab[i] = new Label(Par_Child_List.get(i));
	        	GridPane.setConstraints(lab[i], 7, i+1);
	        }
	        Button butBack = new Button("Back");;
	        System.out.println(StoreDatabase.getAge(name));
	        
	        
	   
			GridPane.setConstraints(butBack, 7, i+1);
			
			butBack.setOnAction(e->{
				UseWin.setScene(scene);
			});
	        grid.getChildren().addAll(lab);
	        grid.getChildren().add(butBack);
	        Add = new Scene (grid,600,600);
			UseWin.setScene(Add);
		
	}
	private static void Direct_Relation(Scene scene) {
		 GridPane grid = grid();
	        Label relName = new Label(" Name ");
	        Button butName = new Button("Enter");
	        TextField textName = new TextField();
	        Button butBack = new Button("Back");
	        
	        
	        GridPane.setConstraints(relName, 0, 3);
	        GridPane.setConstraints(textName, 3, 3);
			GridPane.setConstraints(butName, 5, 3);
			GridPane.setConstraints(butBack, 5, 4);
			butName.setOnAction(e->{
				String Relation = StoreDatabase.getRelation(name,textName.getText());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if(Relation==null)
					alert.setHeaderText("Not Friends");
				else
					alert.setHeaderText(Relation);
				alert.showAndWait();
			});
			
			butBack.setOnAction(e->{
				UseWin.setScene(scene);
			});
	        grid.getChildren().addAll(relName,butName,butBack,textName);
	        Add = new Scene (grid,600,600);
			UseWin.setScene(Add);
		
	}
	private static void Delete(Scene scene) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Do you want to delete - "+name);
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    try {
				Driver.Delete(name);
			} catch (NoParentException e) {
				e.printStackTrace();
			}
		} else {
			
		}
	}
	private static void DisplayUser(Scene scene) {
		 GridPane grid = grid();
		 Label [] Det = new Label[6];
		 ArrayList<String>User_Cred = new ArrayList<String>();
		User_Cred= StoreDatabase.selectPerson(name);
	        Det[0] = new Label(User_Cred.get(0));
	        Det[1] = new Label(User_Cred.get(1));
	        Det[2] = new Label(User_Cred.get(2));
	        Det[3] = new Label(User_Cred.get(3));
	        Det[4] = new Label(User_Cred.get(4));
	        Det[5] = new Label(User_Cred.get(5));
	        Button butBack = new Button("Back");
	        
	        GridPane.setConstraints(Det[0], 7, 1);
	        GridPane.setConstraints(Det[1], 7, 2);
			GridPane.setConstraints(Det[2], 7, 3);
			GridPane.setConstraints(Det[3], 7, 4);
			GridPane.setConstraints(Det[4], 7, 5);
			GridPane.setConstraints(Det[5], 7, 6);
			GridPane.setConstraints(butBack, 7, 7);
			
			butBack.setOnAction(e->{
				UseWin.setScene(scene);
			});
			grid.getChildren().addAll(Det);
	        grid.getChildren().add(butBack);
	        Add = new Scene (grid,600,600);
			UseWin.setScene(Add);
	
	}
	private static void AddRelation(Scene scene){
		
        GridPane grid = grid();
        Label relName = new Label("Relation");
        Button butName = new Button("Enter");
        TextField textName = new TextField();
        Button butBack = new Button("Back");
        ComboBox<String> comName = new ComboBox<String>();
        comName.getItems().addAll("friend","couple","colleague","parent","classmate","sibling");
        comName.setPromptText("Choose : ");
        
        GridPane.setConstraints(relName, 0, 3);
        GridPane.setConstraints(textName, 3, 3);
		GridPane.setConstraints(butName, 5, 3);
		GridPane.setConstraints(butBack, 5, 4);
		GridPane.setConstraints(comName, 2, 3);
		butName.setOnAction(e->{
			String Name_rel = textName.getText();
			try {
				Driver.RelationCheck(name,Name_rel,comName.getValue());
			} catch (TooYoungException e1) {
				System.err.println(e1);
			} catch (NotToBeFriendsException e1) {
				System.err.println(e1);
			} catch (NoAvailableException e1) {
				System.err.println(e1);
			} catch (NotToBeCoupledException e1) {
				System.err.println(e1);
			} catch (NotToBeClassmateException e1) {
				System.err.println(e1);
			} catch (NotToBeColleagueException e1) {
				System.err.println(e1);
			}
			UseWin.setScene(scene);
		});
		
		butBack.setOnAction(e->{
			UseWin.setScene(scene);
		});
        grid.getChildren().addAll(relName,butName,butBack,textName,comName);
        Add = new Scene (grid,600,600);
		UseWin.setScene(Add);
		
	}
	public static GridPane grid(){
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(30);
        grid.setHgap(10);
        return grid;
	}

}
