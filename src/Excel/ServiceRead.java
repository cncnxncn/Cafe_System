package Excel;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import FileController.FileVO;

public class ServiceRead {
	
	private FileInputStream fis;
	private TimeZone timezon;
	private Date date;
	private String datemonthday = "MM월 dd";
	private DateFormat df = new SimpleDateFormat(datemonthday);
	
	FileVO vo = new FileVO();
	public Map<String, Object> ReadXlsx(Map<String, Object> map){
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowIndex = 0;
		while(sheet.getRow(rowIndex).getCell(0).getStringCellValue().equals("월/일")) {
			rowIndex++;
		}
		int StatisticsRowIndex = 28 ;
		while(sheet.getRow(StatisticsRowIndex).getCell(0).getStringCellValue().equals("총합")) {
			StatisticsRowIndex ++;
		}
		StatisticsRowIndex ++;
		
		XSSFRow productRow = sheet.getRow(rowIndex);
		String [] productData = {"품명","입고량","사용량","망실량","재고","최근 수정일"};
		int productsCount = productRow.getPhysicalNumberOfCells() / 4;
		String [][] product = new String[productsCount][productData.length];
		int cellIndex = 1;
		for(int index = 0 ; index < productsCount; index++) {
			
			product[index][0] = productRow.getCell(cellIndex).getStringCellValue();
			cellIndex += 2;
			product[index][5] = productRow.getCell(cellIndex).getStringCellValue();
			//next row
			cellIndex -= 2;
			for(int i = 1; i < 5; i++) {
				product[index][i] = productRow.getCell(cellIndex).getStringCellValue();
				cellIndex++;
			}
		}
		
		
		return null;
	}
	
}
