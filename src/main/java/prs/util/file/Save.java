package prs.util.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Save {
	PrintWriter writer;
	public Save(String content, String name) {
		try {
			writer = new PrintWriter("src/main/resources/session/"+name+".txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(content);
		writer.close();
	}
}
