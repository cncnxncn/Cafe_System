package FileController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileVO {
	
	private FileController filecon;
	private File file;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	
	private File getFileConnect() {
		filecon = new FileController();
		String filepath = filecon.getFilePath();
		file = new File(filepath);
		return file;
	}
	 
	public FileInputStream getXlsx() {
		file = getFileConnect();
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fis;
	}
	
	public FileOutputStream setXlsx() {
		file = getFileConnect();
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fos;
	}
}
