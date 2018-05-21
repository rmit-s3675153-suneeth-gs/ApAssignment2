package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.interfaces.Classmate;
import model.interfaces.Colleague;

public class Adult extends Person implements Classmate,Colleague {
	private  ArrayList<String>classmate = new ArrayList<String>();
	private  ArrayList<String>colleague = new ArrayList<String>();
	public Adult(String Name,String Photo,int Age,String Status,String State,String Gender) {
		super(Name,Photo, Age,Status,State,Gender);//giving data to super class
	}
	@Override
	public void setClassmate(String name) {
		classmate.add(name);
	}
	@Override
	public ArrayList<String> getClassmate() {
		return classmate;
	}
	@Override
	public void setColleague(String name) {
		colleague.add(name);	
	}
	@Override
	public ArrayList<String> getColleague() {
		return colleague;
	}
	
	
}
