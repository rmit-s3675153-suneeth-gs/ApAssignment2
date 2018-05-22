package model;

import java.util.ArrayList;

import model.interfaces.Classmate;

public class Child extends Person implements Classmate {

	private ArrayList<String> parent = new ArrayList<String>(2);
	private ArrayList<String> classmate = new ArrayList<String>();

	public Child(String Name,String Photo,int Age,String Status,String State,String Gender,String parent1,String parent2) {
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
	@Override
	public void setClassmate(String name) { //setter for classmate
		classmate.add(name);
	}
	@Override
	public ArrayList<String> getClassmate() { //getter for classmate
		return classmate;
	}
}
