package database;
import controller.Driver;
import exception.NoParentException;
/*
 * StoreDatabase - Suneeth Gopinathan Sujatha -
 */
import java.sql.*;
import java.util.ArrayList;

import org.hsqldb.Server;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class StoreDatabase {
	private Server hsqlServer = null;
	private static Connection connection = null;
	private static ResultSet rs = null;
	

	public StoreDatabase() {
		hsqlServer = new Server();
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);
		hsqlServer.setDatabaseName(0, "MiniDB");
		hsqlServer.setDatabasePath(0, "file:M1YDB");
		hsqlServer.start();
		
		try { // creating connection
				Class.forName("org.hsqldb.jdbcDriver");
			connection =DriverManager.getConnection("jdbc:hsqldb:MiniDB", "sa", "123");
		}catch (SQLException e) {
			System.out.println("sql error");
		}catch (ClassNotFoundException e) {
			System.out.println("class not found");
		}
	}
	public static Connection getConnection(){
		return connection;
	}
	public void createPersonTable(){ //creating table
		try {
			connection.prepareStatement("drop table person if exists;").execute();
			connection.prepareStatement("create table person(name varchar(20) not null,photo varchar(20),age integer,status varchar(20), state varchar(20),gender varchar(20),primary key(name));").execute();
		
		
			connection.prepareStatement("drop table parent if exists;").execute();
			connection.prepareStatement("create table parent(name varchar(20) not null, type varchar(20),parent1 varchar(20),parent2 varchar(20));").execute();
		
			
			connection.prepareStatement("drop table relation if exists;").execute();
			connection.prepareStatement("create table relation(name1 varchar(20) not null,name2 varchar(20),relation varchar(20));").execute();
			connection.commit();
		}catch (SQLException e2) {
			System.out.println("Error in creation table");
		}
	}
	
	public static void LoadData() throws SQLException {
		connection.prepareStatement("insert into person(name,photo,age,status,state,gender)values ('Alex Smith','',2,'student at RMIT','WA','M');").executeQuery();
		connection.prepareStatement("insert into person (name,photo,age,status,state,gender) values ('Ben Turner','BenPhoto.jpg', 35,'manager at coles','VIC','M');").executeQuery();
		connection.prepareStatement("insert into person (name,photo,age,status,state,gender) values ('Hannah White,'Hannah.png',14,'student at PLC', 'VIC','F');").executeQuery();
		connection.prepareStatement("insert into person (name,photo,age,status,state,gender) values ('Zoe Foster',灯,28,'Founder of ZFX','VIC','F');").executeQuery();
		connection.prepareStatement("insert into person (name,photo,age,status,state,gender) values ('Mark Turner','Mark.jpeg',2, 灯, 'VIC','M');").executeQuery();

		connection.prepareStatement("insert into parent (name,type,parent1,parent2) values ('Mark turner', 'YOUNGCHILD','Ben turner', 'Zoe foster');").executeQuery();
		connection.prepareStatement("insert into relation (name1,name2,relation) values ('Alex Smith', 'Ben Turner', 'friends');").executeQuery();
		connection.prepareStatement("insert into relation (name1,name2,relation) values ('Ben Turner', 'Zoe Foster', 'couple');").executeQuery();
		connection.prepareStatement("insert into relation (name1,name2,relation) values ('Ben Turner', 'Mark Turner', 'parent');").executeQuery();
		connection.prepareStatement("insert into relation (name1,name2,relation) values ('Mark Turner', 'Zoe Foster', 'parent');").executeQuery();
		
	}
	/*
	 * inserting into person table
	 */
	public static void insertPersonTable(String name,String photo,int age,String status,String state,String gender){
		try {
			connection.prepareStatement("insert into person(name,photo,age,status,state,gender)"+ "values ('"+name+"','"+photo+"',"+age+",'"+status+"','"+state+"','"+gender+"');").execute();
			if(age>16)
				Driver.addAdult(name, photo, age, status, state, gender);
			connection.commit();
		}catch (SQLException e2) {
			System.out.println("Error in insertion ");
		}
	}
	/*
	 * inserting into parent table
	 */
	public static void insertParentTable(String name , String type , String parent1, String parent2, ArrayList<String> inputs){
		try {
			int Age =Integer.parseInt(inputs.get(2));
			connection.prepareStatement("insert into parent(name,type,parent1,parent2)"+ "values ('"+name+"','"+type+"','"+parent1+"','"+parent2+"');").execute();
			if(type.equals("CHILD"))
				Driver.addChild(name, inputs.get(1), Age, inputs.get(3), inputs.get(4), inputs.get(5), parent1, parent2);
			else if(type.equals("YOUNGCHILD"))
				Driver.addYchild(name, inputs.get(1), Age, inputs.get(3), inputs.get(4), inputs.get(5), parent1, parent2);
			connection.commit();
		}catch (SQLException e2) {
			e2.printStackTrace();
		} 
	}

	public static void selectPerson(){
		try {
			rs = connection.prepareStatement("select name,photo,age,status,state,gender from person;").executeQuery();
			while(rs.next()){
				System.out.println(String.format("ID: %1s\nPhoto: %1s\nAge: %1d\nStatus : %1s\nState: %1s \nGender:%1s", rs.getString(1), rs.getString(2),rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6)));
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in selection table");
		}
		
		
	}
	/*
	 * Selecting a person and his/her credentials are returned using an arraylist
	 */
	public static ArrayList<String> selectPerson(String name){
		try {
			ArrayList<String> User_Cred= new ArrayList<String>();
			
			rs = connection.prepareStatement("select name,photo,age,status,state,gender from person where name = '"+name+"';").executeQuery();
			rs.next();
			User_Cred.add( rs.getString(1));
			User_Cred.add( rs.getString(2));
		    User_Cred.add( String.valueOf(rs.getInt(3)));
			User_Cred.add( rs.getString(4)) ;
			User_Cred.add( rs.getString(5));
			User_Cred.add( rs.getString(6));
			return User_Cred;
		} catch (SQLException e) {
			System.out.println("Error in selection table");
		}
		return null;
		
	}
	
	/*
	 * Relation insertion table
	 */
	public static void insertRelationTable(String couple1,String couple2,String Relation){
		try {
			connection.prepareStatement("insert into relation(name1,name2,relation)"+ "values ('"+couple1+"','"+couple2+"','"+Relation+"');").execute();
			connection.commit();
		}catch (SQLException e2) {
			System.out.println("Error in insertion table");
		}
	}
	/*
	 * Checks whether relation exists between couples 
	 */
	public static boolean check(String couple1,String couple2,String Relation) throws NoParentException{
		boolean check1 =false,check2 =false;
		int age1=0,age2 = 0;
		try{
			rs = connection.prepareStatement("select count(name) from person  where name = '"+couple1+"';").executeQuery();
			rs.next();
			if(rs.getInt(1)==1){
				check1=true; // parent 1 exist
				rs = connection.prepareStatement("select age from person  where name = '"+couple1+"';").executeQuery();
				rs.next();
				age1=rs.getInt(1);
			}
			rs = connection.prepareStatement("select count(name) from person  where name = '"+couple2+"';").executeQuery();
			rs.next();
			if(rs.getInt(1)==1){
				check2=true;// means the parent 2 exist
				rs = connection.prepareStatement("select age from person  where name = '"+couple2+"';").executeQuery();
				rs.next();
				age2=getAge(couple2);
			}
			if(check1==false||check2==false)
				throw new NoParentException("Entered Parent doesnt exist in the System,So you cannot Add The User");
				
			else{
				boolean couple_check=false;
				if(age1>16&&age2>16){
					rs = connection.prepareStatement("select count(name1) from relation  where name1 = '"+couple1+"' and name2 ='"+couple2+"' and relation ='"+Relation+"';").executeQuery();
					rs.next();
					if(rs.getInt(1)==1)
						couple_check=true;
					rs = connection.prepareStatement("select count(name1) from relation  where name1 = '"+couple2+"' and name2 ='"+couple1+"' and relation ='"+Relation+"';").executeQuery();
					rs.next(); // checking whether the parents enetered are couples 
					if(rs.getInt(1)==1)
						couple_check=true;
					if(!couple_check){
						throw new NoParentException("Entered Parents are not couples");
					}
					else{
						return true;
					}
				}else
					throw new NoParentException("Entered Parent may be a Child");
				

			}
		}catch (SQLException e2) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Error While Entering Parents !");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("PLease enter again")));
			alert.showAndWait();
		}
		return false;
	}
	/*
	 * Get relatio between 2 persons
	 */
	public static String getRelation(String name1, String name2){
		String relation = null;
		try {
			rs = connection.prepareStatement("select relation from relation  where name1 = '"+name1+"' and name2 ='"+name2+"';").executeQuery();
			rs.next();
			relation = rs.getString(1);
			if(relation.equals("parent")){ //if its a parent child relation then need to find which is parent and child
				if(getAge(name1)<16){//age <16 should be child
					relation = "child";
				}
				
			}	
		} catch (SQLException e) {
			int flag=0;
			try {
				rs = connection.prepareStatement("select relation from relation  where name1 = '"+name2+"' and name2 ='"+name1+"';").executeQuery();
				rs.next();
				relation = rs.getString(1);
				if(relation.equals("parent")){
					if(getAge(name1)<16){
						relation = "child";
					}
					
				}
				flag=1;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			if(flag==0)
			System.out.println("Relation error");
		}
		return relation;
		
		
	}
	/*
	 * Gets age of a person
	 */
	public static int getAge(String name){
		int age=-1;
		try {
			rs = connection.prepareStatement("select age from person  where name = '"+name+"';").executeQuery();
			rs.next();
			age= rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("Age fetching error");
		}
		return age;
	}
	/*
	 * Gets parents names of a child
	 */
	public static ArrayList<String> getParents(String name){
		ArrayList<String> Parent = new ArrayList<String>();
		try {
			Parent.add("---PARENTS---");
			rs = connection.prepareStatement("select parent1,parent2 from parent  where name = '"+name+"';").executeQuery();
			rs.next();
			Parent.add(rs.getString(1));
			Parent.add(rs.getString(2));
			return Parent;
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Parent Doesnt exist - Child or YoungCHild cannot exist");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please enter again")));
			alert.showAndWait();
			return null;
		}
	}
	/*
	 * Get children list for a parent
	 */
	public static ArrayList<String> getChildren(String name) {
		ArrayList<String> child = new ArrayList<String>();
		
		try {
			child.add("---CHILDREN---");
			rs = connection.prepareStatement("select name from parent  where parent1 = '"+name+"';").executeQuery();
			while(rs.next())
				child.add(rs.getString(1));
			rs = connection.prepareStatement("select name from parent  where parent2 = '"+name+"';").executeQuery();
			while(rs.next())
				child.add(rs.getString(1));
			return child;
			
		} catch (SQLException e) {
			System.out.println("error in childernprint");
			return null;
		}
	
	}
	public static void del_Parent(String name) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(
			           "DELETE FROM parent WHERE name ='"+name+"';");
		} catch (SQLException e) {
			System.out.println("Doesnt not exist in parent/child table");
		}
        
		
	}
	/*
	 * Deletes if there exists any relation for the deleted person
	 */
	public static void del_Relation(String name) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(
			           "DELETE FROM relation WHERE name1 ='"+name+"';");
		} catch (SQLException e) {
			try{
			stmt = connection.createStatement();
			stmt.executeUpdate(
			           "DELETE FROM relation WHERE name2 ='"+name+"';");
			}catch(SQLException e1){
				System.out.println("Doesnt exist in Relation table");
			}
			
		}
		
	}
	/*
	 * Deletes from person table for the corresponding user
	 */
	public static void del_Person(String name) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(
			           "DELETE FROM person WHERE name ='"+name+"';");
			Driver.Delete_object(name);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Successfully Deleted ! ");
			alert.showAndWait();
		} catch (SQLException e) {
			System.out.println("Doesnt not exist in parent/child table");
		}
	}
	/*
	 * delete if child - parent relation exists
	 */
	public static void del_Child(String name) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(
			           "DELETE FROM parent WHERE name ='"+name+"';");
		} catch (SQLException e) {
			System.out.println("Doesnt not exist in parent/child table");
		}
	}
	
	/*
	 * checks whether an adult has a child 
	 */
	public static int check_child(String name){
		int flag=0;
		try {
			rs = connection.prepareStatement("select count(parent1) from parent  where parent1 = '"+name+"';").executeQuery();
			rs.next();
			if(rs.getInt(1)>0)
				flag=1;
			else{
				flag=0;
				rs = connection.prepareStatement("select count(parent2) from parent  where parent2 = '"+name+"';").executeQuery();
				rs.next();
				if(rs.getInt(1)>0)
					flag=1;
			
			}
		} catch (SQLException e) {
			System.out.println("Adult doesnt have a child`");
		}

		return flag;
	}
	/*
	 * checks whther the names exists 
	 */
	public static boolean name_exists(String name_rel){
		boolean flag=false;
		try {
			rs = connection.prepareStatement("select count(name) from person  where name = '"+name_rel+"';").executeQuery();
			rs.next();
			if(rs.getInt(1)==1)
				flag=true;
			
		} catch (SQLException e) {
			System.out.println("Error occured in checking whether name exists !");
		}
		return flag;
		
	}
	public static String CheckRelationExists(String name, String name_rel) {
		try {
			rs = connection.prepareStatement("select count(name1) from relation  where name1 = '"+name+"' and name2 ='"+name_rel+"';").executeQuery();
			rs.next();
			if(rs.getInt(1)==0){
				rs = connection.prepareStatement("select count(name1) from relation  where name1 = '"+name_rel+"' and name2 ='"+name+"';").executeQuery();
				rs.next();
				if(rs.getInt(1)==0)
					return "Nil";
				else{
					rs = connection.prepareStatement("select relation from relation  where name1 = '"+name_rel+"' and name2 ='"+name+"';").executeQuery();
					rs.next();
					return rs.getString(1);
				}
			}
			else{
				rs = connection.prepareStatement("select relation from relation  where name1 = '"+name+"' and name2 ='"+name_rel+"';").executeQuery();
				rs.next();
				return rs.getString(1);
			}
		}catch(SQLException e){
				System.out.println("Error while checking whether relation exist!");
				return null;
		}
	}
	/*
	 * Checks whether 2 persons are already a couple 
	 */
	public static int AlreadyCouple(String name, String name_rel){
		try {
			rs = connection.prepareStatement("select count(name1) from relation  where name1 = '"+name+"' and name2 ='"+name_rel+"'and relation = 'couple';").executeQuery();
			rs.next();
			if(rs.getInt(1)==0){
				rs = connection.prepareStatement("select count(name1) from relation  where name1 = '"+name_rel+"' and name2 ='"+name+"'and relation = 'couple';").executeQuery();
				rs.next();
				if(rs.getInt(1)==0)
					return 0;
				else{
					
					return 1;
				}
			}
			else
				return 1;
			
		}catch(SQLException e){
				System.out.println("Error while checking whether relation exist!");
				return 0;
		}
	}
	
		

}
