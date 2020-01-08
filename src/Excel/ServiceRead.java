package Excel;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.ss.formula.functions.Replace;
import org.apache.poi.ss.formula.functions.Value;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import FileController.FileController;
import FileController.FileVO;

public class ServiceRead {

	
	private FileInputStream fis;
	private TimeZone timezon;
	private Date date;
	private String datemonthday = "MM월dd일";
	private DateFormat df = new SimpleDateFormat(datemonthday);
	private Calendar cal;
	
	FileVO vo = new FileVO();
	
	private int[] getRowIndex(XSSFSheet sheet) {
		int []rowIndexs = new int[2];
		while(!sheet.getRow(rowIndexs[0]).getCell(0).getStringCellValue().equals("월/일")) {
			rowIndexs[0] ++;
		}
		rowIndexs[1] = 28;
		while(!sheet.getRow(rowIndexs[1]).getCell(0).getStringCellValue().contentEquals("총합")) {
			rowIndexs[1] ++;
		}
		//this row = header , next row = content
		rowIndexs[1] ++;
		return rowIndexs;
	}
	
	public Map<String, Object> ReadXlsx(Map<String, Object> map){
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		
		int indexs[] = getRowIndex(sheet);
		
		int rowIndex = indexs[0];
		int StatisticsRowIndex = indexs[1];
		
		XSSFRow productStatisticsRow = sheet.getRow(StatisticsRowIndex);
		XSSFRow productRow = sheet.getRow(rowIndex);
		String [] productData = {"품명","입고량","사용량","망실량","재고","최근 수정일"};
		int productsCount = productRow.getPhysicalNumberOfCells() / 3;
		String [][] product = new String[productsCount][productData.length];
		int cellIndex = 1;
		for(int index = 0 ; index < productsCount; index++) {
			product[index][0] = productRow.getCell(cellIndex).getStringCellValue();
			cellIndex += 2;
			product[index][5] = productRow.getCell(cellIndex).getStringCellValue();
			//next row
			cellIndex -= 2;
			for(int i = 1; i < 5; i++) {
				XSSFCell statisticsCell = productStatisticsRow.getCell(cellIndex);
				switch(statisticsCell.getCellType()) {
				case XSSFCell.CELL_TYPE_NUMERIC	: 
					product[index][i] = statisticsCell.getNumericCellValue() + "";
					break;
				case XSSFCell.CELL_TYPE_STRING	:
					product[index][i] = statisticsCell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_ERROR	:
					product[index][i] = String.valueOf(statisticsCell.getErrorCellValue());
					break;
				}
				cellIndex++;
			}
		}
		Map<String , Object> productMap = new HashMap<String, Object>();
		productMap.put("result","성공");
		productMap.put("product", product);
		
		return productMap;
	}
	
	public Map<String, Object> ReadProductDetailXlsx(Map<String, Object> map){
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowindexs[] = getRowIndex(sheet);
		int startCellIndex = (int) map.get("cellIndex");
		
		int startRowIndex = rowindexs[0];
		int statisticsRowIndex = rowindexs[1];
		
		String productName = sheet.getRow(startRowIndex).getCell(startCellIndex).getStringCellValue();
		String lastUpdate = sheet.getRow(startRowIndex).getCell(startCellIndex + 2).getStringCellValue();
		double LastMonthStock = sheet.getRow(startRowIndex + 2).getCell(startCellIndex).getNumericCellValue();
		int rowIndex = startRowIndex + 3;
		
		cal = Calendar.getInstance();
		date = new Date();
		FileController filecon = new FileController();
		String filePath = filecon.getFilePath();
		String fileName = filePath.substring(filePath.lastIndexOf("\\"));
		int month = 1;
		while (fileName.indexOf(String.valueOf(month)) < 0) {
			month ++;
		}
		cal.set(2020, month - 1, 1);
		int MaximumDay = cal.getActualMaximum(cal.DAY_OF_MONTH);
		System.out.println("MaximumDay : " + MaximumDay);
		df = new SimpleDateFormat(datemonthday);
		String [][] content = new String[MaximumDay][5];
		for(int day = 1 ; day < MaximumDay + 1; day++) {
			int cellIndex =startCellIndex;
			cal.set(cal.get(Calendar.YEAR), month -1, day);
			String month_day = df.format(cal.getTime());
			month_day.replace("0", "");
			
			content[day-1][0] = month_day;
			
			for(int i = 1 ; i < 5; i ++) {
				
			if(sheet.getRow(rowIndex).getCell(cellIndex) == null) 
			{
				content[day-1][i] = "";
			}
			else 
			{
				content[day-1][i] = sheet.getRow(rowIndex).getCell(cellIndex).getNumericCellValue() + "";
			}
			cellIndex++;
			}
			
			rowIndex++;
		}
		
		map.put("content",content);
		map.put("productName", productName);
		map.put("lastUpdate", lastUpdate);
		map.put("lastMonthStock", LastMonthStock);
		
		return map;
	}
	
}
