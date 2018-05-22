package view;

import java.util.ArrayList;
import java.util.Optional;

import controller.Driver;
import database.StoreDatabase;
import exception.NoAvailableException;
import exception.NoParentException;
import exception.NotToBeClassmateException;
import exception.NotToBeColleagueException;
import exception.NotToBeCoupledException;
import exception.NotToBeFriendsException;
import exception.TooYoungException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Person;
/*
 * Author Suneeth - This class consists of adding Relation
 * Show Parents/Child relation
 * Direct Relation
 * Delete Account
 * Display User Details
 */
public class UserWindow {
		static Scene Add,scene;
		static Stage UseWin;
		static String  name;
	public static void display(String value) {
		UseWin= new Stage();
		UseWin.setTitle("User Window");
		name =value;
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        //Button for the window
        Button b1= new Button("   Add Relation		");
        Button b2= new Button("Show Parents/Child	");
        Button b3= new Button("Find Direct Relation ");
        Button b4= new Button("   Delete Account	");
        Button b5= new Button("     Display User	");
        Button b6= new Button("       Exit			");
	   
		GridPane.setConstraints(b1, 10, 0);//adding in layout
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
			MiniNet.showBack();
		});
		grid.getChildren().addAll(b1,b2,b3,b4,b5,b6);
		scene = new Scene(grid,600,600);
		UseWin.setScene(scene);
		UseWin.show();
		
		
	}
	private static void ShowParents_Child(Scene scene) {
			ArrayList<String> Par_Child_List = new ArrayList<String>();
			Par_Child_List = Driver.Parent_Child(name); // Checks for Parents if its child or Children if its a parent
			
			GridPane grid = grid();
	        Button butBack = new Button("Back");
	        
	        ListView<String> list = new ListView<String>();
	        ObservableList<String> items =FXCollections.observableArrayList (Par_Child_List);// List the ArrayList
	        list.setItems(items);
			GridPane.setConstraints(butBack, 7, 6);
			
			butBack.setOnAction(e->{
				UseWin.setScene(scene);
			});
	        grid.getChildren().addAll(list);
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
			butName.setOnAction(e->{ // Checks if theres any relation existing
				String Relation = StoreDatabase.getRelation(name,textName.getText());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if(Relation==null)
					alert.setHeaderText("Not Friends");
				else
					alert.setHeaderText("Relation between "+name+"&"+textName.getText()+" "+Relation.toUpperCase());
				alert.showAndWait();
			});
			
			butBack.setOnAction(e->{
				UseWin.setScene(scene);
			});
	        grid.getChildren().addAll(relName,butName,butBack,textName);
	        Add = new Scene (grid,600,600);
			UseWin.setScene(Add);
		
	}
	private static void Delete(Scene scene) { //deleting account
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Do you want to delete - "+name); //asking for confirmation
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    try {
				Driver.Delete(name);
			} catch (NoParentException e) {
				System.err.println(e);;
			}
		} else {
			
		}
	}
	@SuppressWarnings("unchecked")
	private static void DisplayUser(Scene scene) { // Dispaying User Details
		GridPane grid = grid();
		TableView<Person> table;
        Button butBack = new Button("Back");
        
		GridPane.setConstraints(butBack, 4, 4);
		//table columns and its values for an user
		TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Person, String> photoColumn = new TableColumn<>("Photo");
        photoColumn.setMinWidth(60);
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("Photo"));

        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(60);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
        
        TableColumn<Person, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(60);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));

        TableColumn<Person, String> stateColumn = new TableColumn<>("State");
        stateColumn.setMinWidth(60);
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("State"));
      
        TableColumn<Person, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(60);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("Gender"));

        table = new TableView<>();
        table.setItems(getPerson());
        table.getColumns().addAll(nameColumn , photoColumn , ageColumn , statusColumn , stateColumn , genderColumn);

        grid.getChildren().addAll(table);
		
		butBack.setOnAction(e->{
			UseWin.setScene(scene);
		});
        grid.getChildren().add(butBack);
        Add = new Scene (grid,650,300);
		UseWin.setScene(Add);
	
	}
	private static void AddRelation(Scene scene){
		
        GridPane grid = grid();
        Label relName = new Label("Relation");
        Button butName = new Button("Enter");
        TextField textName = new TextField();
        Button butBack = new Button("Back");
        ComboBox<String> comName = new ComboBox<String>();
        comName.getItems().addAll("friends","couple","colleague","classmate","sibling");
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
	public static ObservableList<Person> getPerson(){// obervable list for an user
		ObservableList<Person> person = FXCollections.observableArrayList();
		ArrayList<Person> det = new ArrayList<Person>();
		int i;
		det = Driver.List();
		for(i = 0;i<det.size();i++)
				if(det.get(i).getName().equals(name))
					break;
		person.add(det.get(i));
		return person;		
	}

}
