package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;

import database.StoreDatabase;
import exception.NoParentException;
import exception.NoSuchAgeException;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import model.Adult;
import model.Child;
import model.Person;
import model.YoungChild;
import view.AddParent;

public class Driver {
	
	private static int PeopleC=0;
	static Person[] List = new Person[1000];
	static ArrayList<String> Data= new ArrayList<String>();
	static ArrayList<Integer> Del_Index= new ArrayList<Integer>();
	private Map<HashMap<String, String>, String> RelationMap = new HashMap<HashMap<String,String>,String>();
	
	String parent1 = null;
	String parent2 = null;
	String name;
	String photo;
	String Status;
	String Gender;
	String age_t;
	int Age;
	String State;
	public void ReadDetailsFile() {
		Del_Index.add(10000);
		try (BufferedReader reader = new BufferedReader(new FileReader("people.txt"))) {
			String line;
			while((line=reader.readLine())!=null){
				boolean child_young=true;
				int parentcount=0;
				StringTokenizer token = new StringTokenizer(line);
				name =token.nextToken(",");
				photo =token.nextToken(",");
				Status =token.nextToken(",");
				Gender=token.nextToken(",");
				age_t=token.nextToken(",");
				Age=Integer.parseInt(age_t);
				State =token.nextToken(",");
				Data.add(name);
				Data.add(photo);
				Data.add(age_t);
				Data.add(Status);
				Data.add(State);
				Data.add(Gender);

				if(Age>16){
					List[PeopleC] = new Adult(name,photo,Age,Status,State,Gender);
					StoreDatabase.insertPersonTable(name, photo, Age, Status, State, Gender);
				}
				if(Age>3 && Age<16){
					Parents_Check(parentcount,child_young,Data);
				}
				if(Age<3&&Age>0){
					child_young=false;
					Parents_Check(parentcount,child_young,Data);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StoreDatabase.selectPerson();
//		for(int i=0;i<PeopleC;i++)
//			System.out.println(List[i].getName()+" "+List[i].getAge()+" "+List[i].getPhoto()+" "+List[i].getStatus());
//		System.out.println(((YoungChild) List[3]).getParent1());System.out.println(((YoungChild) List[3]).getParent2());
//		((YoungChild) List[3]).getParent1();
	}
	private void Parents_Check(int parentcount, boolean child_young, ArrayList<String> data2) {
		for (Entry<HashMap<String, String>, String> entry : RelationMap.entrySet()){
			if(entry.getValue().equals("parent")){
				String s1=entry.getKey().toString();
				StringTokenizer toke = new StringTokenizer(s1);
				String name1=toke.nextToken("{=");
				String name2=toke.nextToken("=}");
				if(parentcount==0){
					if(name.equals(name1)){
						parent1=name2;
						parentcount++;
					}
					if(name.equals(name2)){
						parent1=name1;
						parentcount++;
					}
				}
				else if(parentcount==1){
					if(name.equals(name1)){
						parent2=name2;
						parentcount++;
					}
					if(name.equals(name2)){
						parent2=name1;
						parentcount++;
					}
				}
			}
			//System.out.println();
		}
		if(parentcount==2){
			if(child_young){
				List[PeopleC] = new Child(name,photo,Age,Status,State,Gender,parent1,parent2);
				StoreDatabase.insertPersonTable(name, photo, Age,Status,State,Gender);
				StoreDatabase.insertParentTable(name, "CHILD", parent1, parent2,Data);
			}
			else{
				List[PeopleC] = new YoungChild(name,photo,Age,Status,State,Gender,parent1,parent2);
				StoreDatabase.insertPersonTable(name, photo, Age,Status,State,Gender);
				StoreDatabase.insertParentTable(name, "YOUNGCHILD", parent1, parent2,Data);
			}
		}
	}
	public void readRelationsFile(){
		try (BufferedReader reader = new BufferedReader(new FileReader("relations.txt"))) {
			String line;
			while((line=reader.readLine())!=null){
				StringTokenizer token = new StringTokenizer(line);
				String name1 =token.nextToken(",");
				String name2 =token.nextToken(",");
				String Relation =token.nextToken(",");
				Map<String,String> name = new HashMap<String,String>();
				name.put(name1,name2);
				RelationMap.put((HashMap<String, String>) name,Relation);
				StoreDatabase.insertRelationTable(name1, name2,Relation);
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> ListUser() {
		ArrayList<String> names = new ArrayList<String>();
		System.out.println(PeopleC);
		
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
	public static void Delete_object(String name){
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
		}catch(Exception NumberFormatException){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("An exception occurred!");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please Enter Number for Age")));
			alert.showAndWait();
			return false;
		}
		
		if(age>0&&age<=130)
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
		else   throw new NoSuchAgeException("not valid");
		return false;
	}
	public static Boolean User_Null_Check(ArrayList<String> inputs) {
		if(inputs.get(0).equals("")||inputs.get(2)==null){
			System.out.println("qwe"+inputs.get(0));
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("An exception occurred-Name cannot be empty!");
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Name cannot be Empty")));
			alert.showAndWait();
			return false;
		
		}
		return true;
		
	}
	public static void addAdult(String name,String photo , int Age , String Status, String State ,String Gender){
		List[PeopleC] = new Adult(name,photo,Age,Status,State,Gender);
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
		return PeopleC;
	}
	public static void setPeopleC(int value){
		PeopleC=value;
	}
	public static ArrayList<String> Parent_Child(String name) {
		int Person_Age = StoreDatabase.getAge(name);
		if(Person_Age<=16)
			 return StoreDatabase.getParents(name);
		else
			return StoreDatabase.getChildren(name);
		
	}
	public static void Delete(String name) throws NoParentException {
		if(StoreDatabase.getAge(name)<=16){
			StoreDatabase.del_Parent(name);
			StoreDatabase.del_Relation(name);
			StoreDatabase.del_Person(name);
		}else{
			if(StoreDatabase.check_child(name)==0){
				StoreDatabase.del_Relation(name);
				StoreDatabase.del_Person(name);
			}else throw new NoParentException("Person is having child so cannot Delete");
		}
	}
	
	
	
}