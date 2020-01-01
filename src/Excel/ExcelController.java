package Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelController {
	
	TimeZone time;
	Date date;
	DateFormat df = new SimpleDateFormat(
			"yy-MM-dd HH:mm:ss");
	
	public Object getXlsx(int index) {
		date = new Date();
		time = TimeZone.getTimeZone("Asia/Seoul");
		df.setTimeZone(time);
		String dt = df.format(date)+"";
		
		try {
			File file = new File("C:\\Users\\사용자\\Downloads\\통합 문서.xlsx");
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("rows : " + rows);
			
			String Sday = dt.substring(dt.lastIndexOf("-")+1,dt.lastIndexOf("-")+3);
			int Iday = Integer.parseInt(Sday);
			System.out.println(Iday);
			
			XSSFRow row = sheet.getRow(Iday + 2);
			int cells = row.getPhysicalNumberOfCells();
			String [][]content = new String[cells/4][6];
			System.out.println(content.length);
			for(int contentIndex = 0; contentIndex < cells/4; contentIndex ++) {
				int contentIndex2 = 0;
				for(int cellIndex = contentIndex * 4 + 1; cellIndex <= cells; cellIndex++) {
					XSSFCell cell = row.getCell(cellIndex);
					
					String result = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						result = cell.getNumericCellValue() + "";
						break;
					case Cell.CELL_TYPE_STRING:
						result = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_ERROR:
						result= cell.getErrorCellValue() + "";
						break;
					}
					content[contentIndex][contentIndex2] = result;
					contentIndex2++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Object writeXlsx() {
		try {
			File file = new File("C:\\Users\\사용자\\Downloads\\통합 문서.xlsx");
			FileOutputStream fos = new FileOutputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object addProduct() {
		
		
		return null;
	}
}
