package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adult extends Person {
	private  ArrayList<String>child = new ArrayList<String>();
	public Adult(String Name,String Photo,int Age,String Status,String State,String Gender) {
		super(Name,Photo, Age,Status,State,Gender);//giving data to super class
	}
	
	
}
