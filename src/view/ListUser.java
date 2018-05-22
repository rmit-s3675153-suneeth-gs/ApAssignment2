package view;
/*
 * Author -Vishal
 * Lists out the user data using a table view
 */
import java.util.ArrayList;

import controller.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Person;

public class ListUser {
	static ArrayList<String> names = new ArrayList<String>();
	static TableView<Person> table;
	@SuppressWarnings("unchecked")
	public static void display() {
		
		Stage List= new Stage();
		List.setTitle("User's Details");
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        Button Back = new Button("Back to menu");
        GridPane.setConstraints(Back,5,0);
        Back.setOnAction(e-> {
			List.close();
			MiniNet.showBack();
		});
        
        TableColumn<Person, String> nameColumn = new TableColumn<>("Name"); //Column is created for each attributes
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
        table.setMinSize(500, 500);
        table.getColumns().addAll(nameColumn , photoColumn , ageColumn , statusColumn , stateColumn , genderColumn);
        grid.getChildren().add(Back);
        grid.getChildren().addAll(table);
		
		Scene scene = new Scene(grid,800,800);
		List.setScene(scene);
		List.show();
	}
	public static ObservableList<Person> getPerson(){ // An observable list for user object is created Person
		ObservableList<Person> person = FXCollections.observableArrayList();
		ArrayList<Person> det = new ArrayList<Person>();
		ArrayList<Integer> deleted = new ArrayList<Integer>();
		det = Driver.List();
		deleted = Driver.Deleted_List();
		for(int i = 0;i<det.size();i++){
			int flag=0;
			for(int j= 0;j<deleted.size();j++){
				if(deleted.get(j)==i){
					flag=1;
					break;
				}}
			if(flag==0)
				person.add(det.get(i));
		}
		return person; // it is passed through
		
	}

}
