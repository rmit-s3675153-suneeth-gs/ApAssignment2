package controller;
/*
 * Driver -Suneeth
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import database.StoreDatabase;
import exception.NoAvailableException;
import exception.NoParentException;
import exception.NoSuchAgeException;
import exception.NotToBeClassmateException;
import exception.NotToBeColleagueException;
import exception.NotToBeCoupledException;
import exception.NotToBeFriendsException;
import exception.TooYoungException;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import model.Adult;
import model.Child;
import model.Person;
import model.YoungChild;

public class Driver {
	
	private static int PeopleC=0;
	static Person[] List = new Person[1000];
	static ArrayList<Integer> Del_Index= new ArrayList<Integer>();
	private Map<HashMap<String, String>, String> RelationMap = new HashMap<HashMap<String,String>,String>();// map to store the relation names and relation
	
	String parent1 = null;
	String parent2 = null;
	String name;
	String photo;
	String Status;
	String Gender;
	String age_t;
	int Age;
	String State;
	public void ReadDetailsFile() throws SQLException {
		Del_Index.add(10000);
		try (BufferedReader reader = new BufferedReader(new FileReader("people.txt"))) {
			if(reader.readLine()==null)//if file is empty then goes to database
				if(StoreDatabase.getConnection()==null){ // if database is empty prints out error
					System.out.println("Sorry could not load the data .Please try again");
					System.exit(0);
				}else
				StoreDatabase.LoadData();// loads initial data from the database
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try (BufferedReader reader = new BufferedReader(new FileReader("people.txt"))) {
			String line;
			while((line=reader.readLine())!=null){ // reads data from text file
				 ArrayList<String> Data= new ArrayList<String>();
				boolean child_young=true;
				int parentcount=0;
				StringTokenizer token = new StringTokenizer(line);// using tokenizer data is split
				name = token.nextToken(",");
				photo = token.nextToken(",");
				photo = photo.substring(1, photo.length()-1); // triming quotes "awesome" to awesome
				Status = token.nextToken(",");
				Status = Status.substring(1, Status.length()-1);
				Gender = token.nextToken(",");
				
				age_t = token.nextToken(",");
				Age = Integer.parseInt(age_t);
				State = token.nextToken(",");
				Data.add(name);// adds every details into a string arraylist
				Data.add(photo);
				Data.add(age_t);
				Data.add(Status);
				Data.add(State);
				Data.add(Gender);

				if(Age>16)
					StoreDatabase.insertPersonTable(name, photo, Age, Status, State, Gender);//inserts the data into the person table
				
				if(Age>3 && Age<16)
					Parents_Check(parentcount,child_young,Data);//child cant exist without parents so adding parents child_young
																// is a flag just to know whether its a child or youngchild
				if(Age<3&&Age>0){
					child_young=false;	//false mean youngchild
					Parents_Check(parentcount,child_young,Data);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void Parents_Check(int parentcount, boolean child_young, ArrayList<String> data) {
		for (Entry<HashMap<String, String>, String> entry : RelationMap.entrySet()){
			if(entry.getValue().equals("parent")){
				String s1=entry.getKey().toString();		//read from relation map and checking for parent relation
				StringTokenizer toke = new StringTokenizer(s1);
				String name1=toke.nextToken("{=");
				String name2=toke.nextToken("=}");
				if(parentcount==0){
					if(name.equals(name1)){		// from parent relation takes the name which is not equal to the child or ychild
						parent1=name2;			//and that name is set as parent1
						parentcount++;
					}
					if(name.equals(name2)){
						parent1=name1;
						parentcount++;
					}
				}
				else if(parentcount==1){
					if(name.equals(name1)){		//and that name is set as parent1
						parent2=name2;
						parentcount++;
					}
					if(name.equals(name2)){
						parent2=name1;
						parentcount++;
					}
				}
			}
		}
		if(parentcount==2){
			if(child_young){
				StoreDatabase.insertPersonTable(name, photo, Age,Status,State,Gender); // inserts into person table	
				StoreDatabase.insertParentTable(name, "CHILD", parent1, parent2,data); // inserts into parent table
			}
			else{
				StoreDatabase.insertPersonTable(name, photo, Age,Status,State,Gender);	//insert into the person table
				StoreDatabase.insertParentTable(name, "YOUNGCHILD", parent1, parent2,data);//inserts into parent table
			}
		}
	}
	public void readRelationsFile(){
		try (BufferedReader reader = new BufferedReader(new FileReader("relations.txt"))) {
			String line;
			while((line=reader.readLine())!=null){		//reads relation text file and stores everything into the relation map
				StringTokenizer token = new StringTokenizer(line);
				String name1 =token.nextToken(",");
				String name2 =token.nextToken(",");
				String Relation =token.nextToken(",");
				Map<String,String> name = new HashMap<String,String>();
				name.put(name1,name2);
				RelationMap.put((HashMap<String, String>) name,Relation);
				StoreDatabase.insertRelationTable(name1, name2,Relation);//inserts relations into the table
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Person> List() { // this will have the an arraylist of objects which are stored in the database
		ArrayList<Person> det = new ArrayList<Person>();
		
		for(int i=0;i<PeopleC;i++)
				det.add(List[i]);
		return det;
	}
	public static ArrayList<String> ListUser() { // gives out the names of the user who are present in the system
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i=0;i<PeopleC;i++){
			int flag=0;
			for(int j=0;j<Del_Index.size();j++)
				if(Del_Index.get(j)==i){
					flag=1;
					break;
				}
			if(flag==0)
				names.add(List[i].getName());
		}
		return names;
	}
	public static ArrayList<Integer> Deleted_List(){// this stores index of objects which were deleted from system 
		return Del_Index;
	}
	public static void Delete_object(String name){ // when a user is deleted from the system , its corresponding object is made to null
		int i,flag=0;
		for( i=0;i<PeopleC;i++)
			if(List[i].getName().equals(name)){
				flag=1;
				break;
			}
			if(flag==1){
			Del_Index.add(i);
			List[i] = null;
		}
			
	}
	
	public static boolean User_Add_Check(String combo, ArrayList<String> text) throws NoSuchAgeException {
		
		int age=0;
		try{
			age=Integer.parseInt(text.get(2));
		}catch(Exception NumberFormatException){//exception if the number entered is not in number for 
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("An exception occurred!");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please Enter Number for Age")));
			alert.showAndWait();
			return false;
		}
		
		if(age>0&&age<=150) // age should be from 1 - 150 otherwise exceptiom
			
			switch(combo){
				case "ADULT":if(age>16){
								return true;
							 }
				case "CHILD":if(age>3&&age<=16){
								return true;
				 			 }
				case "YOUNGCHILD":if(age>0&&age<=3){
								return true;
							 }
			}
		else   throw new NoSuchAgeException("not valid"); //exception for age not in range
		return false;
	}
	public static Boolean User_Null_Check(ArrayList<String> inputs) {//if name and ageis empty
		if(inputs.get(0).equals("")||inputs.get(2)==null){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("An exception occurred-Name and Age cannot be empty!");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Name cannot be Empty")));
			alert.showAndWait();
			return false;
		
		}
		return true;
		
	}
	public static void addAdult(String name,String photo , int Age , String Status, String State ,String Gender){
		List[PeopleC] = new Adult(name,photo,Age,Status,State,Gender);// add into the objects
		PeopleC++;
	}
	public static void addChild(String name,String photo , int Age , String Status, String State ,String Gender,String Parent1,String Parent2){
		List[PeopleC] = new Child(name,photo,Age,Status,State,Gender,Parent1,Parent2);
		PeopleC++;
	}
	public static void addYchild(String name,String photo , int Age , String Status, String State ,String Gender,String Parent1,String Parent2){
		List[PeopleC] = new YoungChild(name,photo,Age,Status,State,Gender,Parent1,Parent2);
		PeopleC++;
	}
	public static int getPeopleC(){
		return PeopleC; //returns how many people are registered in the system
	}
	public static void setPeopleC(int value){
		PeopleC=value;
	}
	public static ArrayList<String> Parent_Child(String name) {//gives the children and parents list for corresponding parents and child
		int Person_Age = StoreDatabase.getAge(name);
		if(Person_Age<=16)
			return StoreDatabase.getParents(name);
		else
			return StoreDatabase.getChildren(name);
		
	}
	public static void Delete(String name) throws NoParentException {
		if(StoreDatabase.getAge(name)<=16){ // if user is not an adult
			StoreDatabase.del_Parent(name); // have to delete from parent table
			StoreDatabase.del_Relation(name);// have to delete from relation table any relation is there
			StoreDatabase.del_Person(name);	//then delete from person table
		}else{
			if(StoreDatabase.check_child(name)==0){
				StoreDatabase.del_Relation(name);
				StoreDatabase.del_Person(name);
			}else throw new NoParentException("Person is having child so cannot Delete");// if there is a child cannot delete 
		}
	}
	public static void RelationCheck(String name, String name_rel, String rel_type) throws TooYoungException, NotToBeFriendsException, NoAvailableException, NotToBeCoupledException, NotToBeClassmateException, NotToBeColleagueException {
		//Relation Check is used to 2 names and its relation
		if(!StoreDatabase.name_exists(name_rel)){ // check whether name exist in database
			Alert alert = new Alert(Alert.AlertType.ERROR); // shows error since name doesnt exist
			alert.setHeaderText("Person doesnt exists!");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Name cannot be Empty")));
			alert.showAndWait();
		}else{
			int age = StoreDatabase.getAge(name);//gets age ofthe user
			if(age>16){// adult
				if(rel_type.equals("parent")){ // here we are not considering adult to have parents thats why this error
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Adult cannot add Parent Relation!"); 
					alert.showAndWait();
					return;
				}
				if(StoreDatabase.getAge(name_rel)<=16){// checks the type of user we entered - this is child and young
					if(rel_type.equals("couple")){
						throw new NotToBeCoupledException("Cannot make couple relation with Child"); // child or young child cant make relation couple
					}
					else{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Adult - Child/Young - Only in Parent Relation!"); // already creating relation with parents
						alert.showAndWait();
					}
				}else{
					String status= StoreDatabase.CheckRelationExists(name,name_rel);//this will return nill if there exist no relation 
					if(status.equals("Nil")){
						if(rel_type.equals("couple")){ //adding couple
							if(StoreDatabase.AlreadyCouple(name, name_rel)==0){
								StoreDatabase.insertRelationTable(name, name_rel, rel_type);
								Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
								alert.setHeaderText("Successfully Added !"); 
								alert.showAndWait();
							}
							else
								throw new NoAvailableException("Either one is a couple so cannot add as couple !");// already a couple cannot be a couple
							
						}else{
						StoreDatabase.insertRelationTable(name, name_rel, rel_type);
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setHeaderText("Successfully Added !"); 
						alert.showAndWait();
						}
						
					}
					else{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Relation already exists - "+status.toUpperCase()); // already creating relation with parents
						alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Name cannot be Empty")));
						alert.showAndWait();
					}
				}
			}
			else if(age>3&&age<=16){//child case

				if(rel_type.equals("couple")){ //checking whether the entered relation is couple or colleague
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Child cannot have : Couple  Relation"); 
					alert.showAndWait();
					return;
				}
				if(rel_type.equals("colleague"))// child cannot have colleague 
					throw new NotToBeColleagueException("Child cannot make colleague relation");
				if(StoreDatabase.getAge(name_rel)>16){
					throw new NotToBeFriendsException("Cannot make adult and child friends");
				}else{
					String status= StoreDatabase.CheckRelationExists(name,name_rel); //checks whether already a relation exist
					if(status.equals("Nil")){//IF NO
						int age_upperlimit,age_lowerlimit,proceedflag=0;//proceedflag = 1 then can add relation age is within limit
						int name_rel_age = StoreDatabase.getAge(name_rel);
						age_upperlimit=age+3;//check age limit for children that means less than or greater than 3 
						age_lowerlimit=age-3;
						if(age_upperlimit>16)
							age_upperlimit=16;
						if(age_lowerlimit<3)
							age_lowerlimit=3;
						if(name_rel_age<=age_upperlimit&&name_rel_age>=(age_lowerlimit))
								proceedflag++;
						if(proceedflag==1 && (rel_type.equals("classmate")||rel_type.equals("friends"))){
							StoreDatabase.insertRelationTable(name, name_rel, rel_type);
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // relation is added into database
							alert.setHeaderText("Successfully Added !"); 
							alert.showAndWait();
						}
						if(proceedflag==0&&(!rel_type.equals("sibling"))) //adding relation weith age gap greater than 3 or less than 3
							throw new NotToBeFriendsException("Age gap larger than 3");
						check_sibling(name,name_rel,rel_type);
					}
					else{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Relation already exists !"); 
						alert.showAndWait();
					}
					
				}
				
			}else {
				if(rel_type.equals("couple"))
					throw new NotToBeCoupledException("YoungChild cannot have couple relation");
				if(rel_type.equals("friends"))
					throw new TooYoungException("Cannot make friend");
				if(rel_type.equals("classmate"))
					throw new NotToBeClassmateException("Cannot make classmate");
				if(rel_type.equals("colleague"))
					throw new NotToBeColleagueException("Cannot make colleague");
				check_sibling(name,name_rel,rel_type);
			}
		}
	}
	private static void check_sibling(String name, String name_rel, String rel_type) {
		if(rel_type.equals("sibling")){ // checking sibling condition
			ArrayList<String> Parent_Set1 = new ArrayList<String>();
			ArrayList<String> Parent_Set2 = new ArrayList<String>();
			Parent_Set1 = StoreDatabase.getParents(name);
			Parent_Set2 = StoreDatabase.getParents(name_rel);
			System.out.println(Parent_Set1);System.out.println(Parent_Set2);
			if(Parent_Set1.get(1).equals(Parent_Set2.get(1))){ //if parents are same - relation added
				if(Parent_Set1.get(2).equals(Parent_Set2.get(2))){
					StoreDatabase.insertRelationTable(name, name_rel, rel_type);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText("Successfully Added !"); 
					alert.showAndWait();
				}
				else{
					Alert alert = new Alert(Alert.AlertType.ERROR); //different parents
					alert.setHeaderText("Different Parents Cannot be siblings!"); 
					alert.showAndWait();
					return;
				}
			}else if(Parent_Set1.get(1).equals(Parent_Set2.get(2))){
				if(Parent_Set1.get(2).equals(Parent_Set2.get(1))){
					StoreDatabase.insertRelationTable(name, name_rel, rel_type);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText("Successfully Added !"); 
					alert.showAndWait();
				}
				else{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Different Parents Cannot be siblings!"); 
					alert.showAndWait();
					return;
				}
			}else if(Parent_Set1.get(2).equals(Parent_Set2.get(2))){
				if(Parent_Set1.get(1).equals(Parent_Set2.get(1))){
					StoreDatabase.insertRelationTable(name, name_rel, rel_type);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText("Successfully Added !"); 
					alert.showAndWait();
				}
				else{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Different Parents Cannot be siblings!"); 
					alert.showAndWait();
					return;
				}
			}else if(Parent_Set1.get(2).equals(Parent_Set2.get(1))){
				if(Parent_Set1.get(1).equals(Parent_Set2.get(2))){
					StoreDatabase.insertRelationTable(name, name_rel, rel_type);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText("Successfully Added !"); 
					alert.showAndWait();
				}
				else{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Different Parents Cannot be siblings!"); 
					alert.showAndWait();
					return;
				}
			}
		}
		
	}
	
	
	
}