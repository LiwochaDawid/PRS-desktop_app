package prs.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Open {
	public static String openFile() throws IOException {
		FileReader fileReader = new FileReader("src/main/resources/session/token.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String textLine = bufferedReader.readLine();
		bufferedReader.close();
		return textLine;
	}
}
