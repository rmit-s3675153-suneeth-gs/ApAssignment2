package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StoreFile {
	public static void StorePeople(String Name,String Photo,int Age,String Status,String State,String Gender) throws IOException{
			File file = new File("people.txt");
			FileWriter fw = new FileWriter(file,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println();
			pw.print(Name+","+Photo+","+Status+","+Gender+","+Age+","+State);
			pw.close();
	}
	
}
