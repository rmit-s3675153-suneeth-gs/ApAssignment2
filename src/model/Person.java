package model;
/*
 * Abstract class Person - Vishal
 */
import java.util.HashMap;
import java.util.Map;

public abstract class Person {
	private String Name;
	private int Age;
	private String Photo;
	private String Status;
	private String State;
	private String Gender;

	private Map<String,String>Relation=new HashMap<String,String>();
	public Person(String Name,String Photo,int Age,String Status,String State,String Gender){//constructor fetching data
		this.Name=Name;
		this.Age=Age;
		this.Photo=Photo;
		this.Status=Status;
		this.State=State;
		this.Gender=Gender;
	}
	public void setName(String s){//setters to fetch data
		Name=s;
	}
	public void setAge(int a){
		Age=a;
	}
	public void setPhoto(String p){
		Photo=p;
	}
	public void setStatus(String s){
		Status=s;
	}
	public void setGender(String s){
		Gender=s;
	}
	public String getName(){//getters returning data
		return Name;
	}
	public String getState(){
		return State;
	}
	public String getPhoto(){
		return Photo;
	}
	public int getAge(){
		return Age;
	}
	public String getStatus(){
		return Status;
	}
	public String getGender(){
		return Gender;
	}
	public void setRelation(String name,String relation){
		Relation.put(name, relation);	
	}
	public Map<String,String> getRelation(String name,String relation){
		return Relation;	
	}

}
