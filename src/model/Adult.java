package model;

import java.util.ArrayList;

import model.interfaces.Classmate;
import model.interfaces.Colleague;
/*
 * CLass Adult inheriting from Person - Vishal
 */
public class Adult extends Person implements Classmate,Colleague {
	private  ArrayList<String>classmate = new ArrayList<String>();
	private  ArrayList<String>colleague = new ArrayList<String>();
	public Adult(String Name,String Photo,int Age,String Status,String State,String Gender) {
		super(Name,Photo, Age,Status,State,Gender);//giving data to super class
	}
	@Override
	public void setClassmate(String name) { //setting classmate data
		classmate.add(name);
	}
	@Override
	public ArrayList<String> getClassmate() {// getters for classmate
		return classmate;
	}
	@Override
	public void setColleague(String name) { //setter for colleague 
		colleague.add(name);	
	}
	@Override
	public ArrayList<String> getColleague() { //getter for colleague
		return colleague;
	}
	
	
}
