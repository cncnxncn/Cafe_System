package FileController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class FileController {
	private File file;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public String getFilePath() {
		file = new File("FilePath.txt");
		String path = "";
		try {
			path = file.getCanonicalPath();
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				path = scan.nextLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public void setFilePath(String filePath) {
		file = new File("FilePath.txt");
		String path = "";
		try {
			path = file.getCanonicalPath();
			file = new File(path);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			System.out.println(filePath);
			writer.write(filePath);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
