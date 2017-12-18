package prs.util.file;

import java.io.File;

public class Delete {
	static File file;
	public static void deleteFile(String name) {
		System.out.println(Delete.class.getResource("/session/token.txt"));
		file = new File("src/main/resources/session/"+name);
		System.out.println(file.getAbsolutePath());
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
	}
}
