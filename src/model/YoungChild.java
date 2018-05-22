package model;

import java.util.ArrayList;
/*
 * YoungCHild from Abstract class Person inheritance
 */
public class YoungChild extends Person {
	private ArrayList<String> parent = new ArrayList<String>(2);

	public YoungChild(String Name,String Photo,int Age,String Status,String State,String Gender,String parent1,String parent2) {
		super(Name,Photo, Age,Status,State,Gender);// Super class constructor storing data
		parent.add(parent1);
		parent.add(parent2);//adding data into arraylist from constructor
	}
	public void setParent(String p1,String p2) {
		parent.add(p1);
		parent.add(p2);//adding data into arraylist from execution
	}
	public String getParent1(){
		return parent.get(0);//returning parent name
	}
	public String getParent2(){
		return parent.get(1);
	}
}
