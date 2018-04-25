package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import model.Adult;
import model.Child;
import model.Person;
import model.YoungChild;

public class Driver {
	private static int AdultC =0;
	private static int ChildC=0;
	private static int YchildC=0;
	private static int PeopleC=0;
	static Person[] List = new Person[1000];
	private Map<HashMap<String, String>, String> RelationMap = new HashMap<HashMap<String,String>,String>();
	
	public void ReadDetailsFile(){
		try (BufferedReader reader = new BufferedReader(new FileReader("people.txt"))) {
			String line;
			while((line=reader.readLine())!=null){
				StringTokenizer token = new StringTokenizer(line);
				String name =token.nextToken(",");
				String photo =token.nextToken(",");
				String Status =token.nextToken(",");
				String Gender=token.nextToken(",");
				int Age=Integer.parseInt(token.nextToken(","));
				String State =token.nextToken(",");
				if(Age>16){
					List[PeopleC] = new Adult(name,photo,Age,Status,State,Gender);
					AdultC++;
					PeopleC++;
				}
				if(Age>3 && Age<16){
					List[PeopleC] = new Child(name,photo,Age,Status,State,Gender);
					ChildC++;
					PeopleC++;
				}
				if(Age<3&&Age>0){
					List[PeopleC] = new YoungChild(name,photo,Age,Status,State,Gender);
					YchildC++;
					PeopleC++;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void Menu(){
//		int input=0;
//		do{	System.out.println("MiniNet Menu");//main menu
//		System.out.println("=================================");
//			System.out.println("1.List everyone");
//			System.out.println("2.Select a person");
//			System.out.println("3.Add an User");
//			System.out.println("4.Exit");
//			Scanner sc = new Scanner(System.in);
//			input=sc.nextInt();
//			switch(input){
//			case 1: ListUser();//lists user
//					break;
//			case 2: int id=SelectUser();//selects an user
//					break;
//			case 3:	int key=AddUser();
//					int in;
////					do{
////						System.out.println("1.Add friends");
////						System.out.println("2.exit");
////						in =sc.nextInt();
////						switch(in){
////						case 1:	if(Age.get(j)>16)
////									addFriend(key,Users.get(j));//adds friend
////								else if(Age.get(j)>4&&Age.get(j)<=16){
////									addOtherDependent(key,Users.get(j),Age.get(j));}//checks child and addsonly dependent
////								else
////									System.out.println("UnderAge :Sorry cant have friends, come when you growup" );
////								break;
////						}
////					}while(in<2);
//					break;
//			}
//			
//		}while(input!=4);
//	}
	public static ArrayList<String> ListUser() {
		// TODO Auto-generated method stub
		ArrayList<String> names = new ArrayList<String>();
		for(int i=0;i<PeopleC;i++){System.out.println(i+List[i].getName());
			names.add(List[i].getName());}
		return names;
		
		
	}
	private int SelectUser() {
		// TODO Auto-generated method stub
		return 0;
	}
	private int AddUser() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
