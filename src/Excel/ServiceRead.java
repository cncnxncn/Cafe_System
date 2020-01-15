package Excel;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
	private String datemonthday = "MM월 dd일";
	private DateFormat df = new SimpleDateFormat(datemonthday);
	private GregorianCalendar cal;
	
	FileVO vo = new FileVO();
	
	public int[] getRowIndex(XSSFSheet sheet) {
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
	
	public int getMonth() {
		FileController filecon = new FileController();
		String filePath = filecon.getFilePath();
		String fileName = filePath.substring(filePath.lastIndexOf("\\"));
		int month = 1;
		while (fileName.indexOf(String.valueOf(month)) < 0) {
			month ++;
			if(month > 12)
				break;
		}
		return month;
	}
	
	public Map<String,Object> ReadXlsx(Map<String, Object> map){
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		
		int indexs[] = getRowIndex(sheet);
		
		int rowIndex = indexs[0];
		int StatisticsRowIndex = indexs[1];
		
		XSSFRow productStatisticsRow = sheet.getRow(StatisticsRowIndex);
		XSSFRow productRow = sheet.getRow(rowIndex);
		String [] productData = {"품명","입고량","사용량","망실량","재고","최근 수정일"};
		int productsCount = productRow.getPhysicalNumberOfCells() / 3;
		if(productsCount == 0) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("result", "가져올 데이터가 없습니다.");
			return resultMap;
		}
		String [][] product = new String[productsCount][productData.length];
		int cellIndex = 1;
		for(int index = 0 ; index < productsCount; index++) {
			product[index][0] = productRow.getCell(cellIndex).getStringCellValue();
			product[index][5] = productRow.getCell(cellIndex + 2).getStringCellValue();
			//next row
			for(int i = 1; i < 5; i++) {
				XSSFCell statisticsCell = productStatisticsRow.getCell(cellIndex);
				if(i != 4)
				{
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
				}
				else
				{
					int lastStockRowIndex= StatisticsRowIndex - 2;
					for(int stockRowIndex = lastStockRowIndex; stockRowIndex > - 1; stockRowIndex --) {
						if(sheet.getRow(stockRowIndex).getCell(cellIndex).getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
						{
							product[index][i] = sheet.getRow(stockRowIndex).getCell(cellIndex).getNumericCellValue() + "";
							break;
						}
						if(stockRowIndex == 0)
						{
							product[index][i] = sheet.getRow(rowIndex + 2).getCell(cellIndex - 3).getNumericCellValue() + "";
						}
					}
				}
				
				cellIndex++;
			}
		}
		
		Map<String , Object> productMap = new HashMap<String, Object>();
		productMap.put("result","성공");
		productMap.put("product", product);
		
		return productMap;
	}
	
	public Map<String,Object> ReadProductDetailXlsx(Map<String, Object> map){
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
		
		int month = getMonth();
		
		date = new Date();
		GregorianCalendar cal = new GregorianCalendar(date.getYear(),month - 1, 1);
		int MaximumDay = cal.getActualMaximum(cal.DAY_OF_MONTH);
		String [][] content = new String[MaximumDay + 1][5];
		for(int day = 1 ; day < MaximumDay + 1; day++) {
			int cellIndex =startCellIndex;
			String month_day = month + "월 " + day +"일";
			
			content[day-1][0] = month_day;
			
			for(int i = 1 ; i < 5; i ++) {
				
			if(sheet.getRow(rowIndex).getCell(cellIndex) == null) 
			{
				content[day-1][i] = "";
			}
			else 
			{
				if(sheet.getRow(rowIndex).getCell(cellIndex).getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
					content[day-1][i] = sheet.getRow(rowIndex).getCell(cellIndex).getNumericCellValue() + "";
				else if(sheet.getRow(rowIndex).getCell(cellIndex).getCellType() == XSSFCell.CELL_TYPE_STRING)
					content[day-1][i] = sheet.getRow(rowIndex).getCell(cellIndex).getStringCellValue();
					
			}
			cellIndex++;
			}
			
			rowIndex++;
		}
		content[MaximumDay][0] = "총합";
		for(int i = 0 ; i < 4; i ++) {
			content[MaximumDay][i + 1] = sheet.getRow(statisticsRowIndex).getCell(startCellIndex + i).getNumericCellValue() +"";
		}
		
		map.put("content",content);
		map.put("productName", productName);
		map.put("lastUpdate", lastUpdate);
		map.put("lastMonthStock", LastMonthStock);
		
		return map;
	}
	
	public Map<String,Object> TodayProductXlsx(Map<String , Object> map){
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowIndexs[] = getRowIndex(sheet);
		int headerRowIndex = rowIndexs[0];
		int StatisticsRowIndex = rowIndexs[1];
		
		date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		String month_day = cal.get(cal.MONTH) +1 +"월 " +cal.get(cal.DAY_OF_MONTH)+"일";
		
		int todayRowIndex = 0 ;
		int maximumRowIndex = sheet.getPhysicalNumberOfRows();
		boolean row = false;
		while(todayRowIndex < maximumRowIndex - 1) {
			if(sheet.getRow(todayRowIndex).getCell(0) == null || sheet.getRow(todayRowIndex).getCell(0).getStringCellValue().equals("총합")) 
				break;
			else 
			{
				if(sheet.getRow(todayRowIndex).getCell(0).getStringCellValue().equals(month_day))
				{
					row = true;
					break;
				}
			}
			todayRowIndex++;
		}
		
		if(!row)
		{
			map.put("result", false);
			return map;
		}
		
		
		int productCount = sheet.getRow(headerRowIndex).getPhysicalNumberOfCells() / 3;
		String content [][] = new String [productCount][5];
		String contentModel[][] = new String [productCount][5];
		int cellIndex = 1;
		for(int index = 0; index < productCount; index ++) {
			content[index][0] = sheet.getRow(headerRowIndex).getCell(cellIndex).getStringCellValue();
			
			int contentIndex = 1;
			for(int i = cellIndex; i < cellIndex + 4; i++) {
				if(sheet.getRow(todayRowIndex).getCell(i) != null)
				{
					if(sheet.getRow(todayRowIndex).getCell(i).getNumericCellValue() != 0)
					{
						content[index][contentIndex] = sheet.getRow(todayRowIndex).getCell(i).getNumericCellValue() + "";
						contentModel[index][contentIndex] = sheet.getRow(todayRowIndex).getCell(i).getNumericCellValue() + "";
					}
					else
					{
						content[index][contentIndex] = "";
						contentModel[index][contentIndex] = "";
					}
				}
				
				contentIndex ++;
				}
			content[index][4] = sheet.getRow(StatisticsRowIndex).getCell(cellIndex + 3).getNumericCellValue() + "";
			contentModel[index][4] = sheet.getRow(StatisticsRowIndex).getCell(cellIndex + 3).getNumericCellValue() + "";
			cellIndex += 4;
			}
			
		
		
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("content", content);
		resultmap.put("result", true);
		resultmap.put("todayRowIndex", todayRowIndex);
		resultmap.put("contentModel", contentModel);
		return resultmap;
	}
}
