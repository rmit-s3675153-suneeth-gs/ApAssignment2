package view;
import java.sql.SQLException;

/*
 * MiniNet - Author - Suneeth
 * this is where main method is invoked Main Window
 */
import controller.Driver;
import database.StoreDatabase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MiniNet extends Application {
	static Stage  window;
	public MiniNet(){
		
	}
	public static void main(String[] args) throws SQLException{
		Driver d = new Driver();
		StoreDatabase sd= new StoreDatabase();
		sd.createPersonTable(); // creating tables
		d.readRelationsFile();	//reading from relation file	
		d.ReadDetailsFile();	//reading from the people file
		launch(args);
	}

	@Override
	public void start(Stage Pstage) throws Exception {
		window=Pstage;
		window.setTitle("MININET");
		Button b1 = new Button("  AddUser ");
		Button b2 = new Button(" ListUser ");
		Button b3 = new Button("SelectUser");
		Button b4 = new Button("   Exit   ");
		b1.setOnAction(e-> {
			AddUser.display();
			Pstage.close();
		});
		
		b2.setOnAction(e->{
			ListUser.display();
			Pstage.close();
		});
		
		b3.setOnAction(e->{
			SelectUser.display();
			Pstage.close();
		});
		b4.setOnAction(e->System.exit(0));
		VBox layout = new VBox(30);
		VBox.setMargin(b1, new Insets(10, 0, 0, 150));
		VBox.setMargin(b2, new Insets(10, 0, 0, 150));
		VBox.setMargin(b3, new Insets(10, 0, 0, 150));
		VBox.setMargin(b4, new Insets(10, 0, 0, 150));
		layout.getChildren().addAll(b1,b2,b3,b4);
		Scene scene = new Scene(layout,400,400);
		
		window.setScene(scene);
		window.show();
	}	
	public static void showBack(){
		window.show();
	}

}
