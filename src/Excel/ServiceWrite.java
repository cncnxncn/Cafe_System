package Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import FileController.FileController;
import FileController.FileVO;

public class ServiceWrite {
	
	private Calendar cal;
	private File file;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	private FileVO vo = new FileVO();
	
	private TimeZone timezon;
	private Date date;
	private DateFormat df;
	private String datefull = "MM-dd/HH:mm";
	private String datemonthday = "MM월 dd";
	
	private XlsxVO product;
	
	private String Header[] = {"입고량","사용량","망실량","재고"};
	
	public void XlsxSetting(XSSFWorkbook workbook,Boolean result) {

		try {
			fos = vo.setXlsx();
			XSSFSheet sheet = null;
			
			if(result)
				sheet = workbook.getSheetAt(0);
			else
				sheet = workbook.createSheet();
			
				int rowindex = 0 ;
				
				CellStyle style = workbook.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
				style.setBorderRight(XSSFCellStyle.BORDER_THIN);
				style.setBorderTop(XSSFCellStyle.BORDER_THIN);
				style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				
				Font font = workbook.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				style.setFont(font);
				
				int firstrowindex = rowindex;
				XSSFCell cell = sheet.createRow(firstrowindex).createCell(0);
				cell.setCellValue("월/일");
				cell.setCellStyle(style);
				rowindex++;
				int lastrowindex = rowindex;
				XSSFCell cell1 = sheet.createRow(lastrowindex).createCell(0);
				cell1.setCellStyle(style);
				sheet.addMergedRegion(new CellRangeAddress(firstrowindex,lastrowindex,0,0));
				
				rowindex++;
				XSSFCell cell2 = sheet.createRow(rowindex).createCell(0);
				cell2.setCellValue("전월 재고");
				cell2.setCellStyle(style);
				
				
				//date cell style
				CellStyle celldatestyle = workbook.createCellStyle();
				celldatestyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
				celldatestyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				celldatestyle.setFont(font);
				
				CellStyle cellDateStyleTen = workbook.createCellStyle();
				cellDateStyleTen.setBorderRight(XSSFCellStyle.BORDER_THIN);
				cellDateStyleTen.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				cellDateStyleTen.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cellDateStyleTen.setFont(font);
				
				//statistics cell style
				CellStyle cellStatisticsStyle = workbook.createCellStyle();
				cellStatisticsStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				cellStatisticsStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
				cellStatisticsStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
				cellStatisticsStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
				cellStatisticsStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cellStatisticsStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				cellStatisticsStyle.setFont(font);
				
				
				rowindex++;
				//date setting
				
				FileController filecon = new FileController();
				String filePath = filecon.getFilePath();
				String fileName = filePath.substring(filePath.lastIndexOf("\\"));
				int month = 1;
				while (fileName.indexOf(String.valueOf(month)) < 0) {
					month ++;
					if(month > 12)
						break;
				}
				date = new Date();
				GregorianCalendar cal = new GregorianCalendar();
					cal.set(date.getYear(), month - 1, 1);
					int Maximumday = cal.getActualMaximum(cal.DAY_OF_MONTH);
					for(int day = 0; day < Maximumday; day ++) {
						int setday = day + 1;
						String monthday = month + "월 " + setday +"일";
						XSSFCell []celldate = new XSSFCell[Maximumday];
						celldate[day] = sheet.createRow(rowindex).createCell(0);
						
						if(setday % 10 == 0) 
						{
							celldate[day].setCellStyle(cellDateStyleTen);
						}
						else
							celldate[day].setCellStyle(celldatestyle);
						
						celldate[day].setCellValue(monthday);
						rowindex++;
					}
					sheet.addMergedRegion(new CellRangeAddress(rowindex,rowindex+1,0,0));
					XSSFCell cellStatistics = sheet.createRow(rowindex).createCell(0);
					cellStatistics.setCellStyle(cellStatisticsStyle);
					cellStatistics.setCellValue("총합");
					rowindex++;
					XSSFCell cellBlank = sheet.createRow(rowindex).createCell(0);
					cellBlank.setCellStyle(cellStatisticsStyle);
					rowindex+=3;
					
					sheet.addMergedRegion(new CellRangeAddress(rowindex,rowindex+1,0,0));
					XSSFCell cellAllStatistics = sheet.createRow(rowindex).createCell(0);
					cellAllStatistics.setCellStyle(cellStatisticsStyle);
					cellAllStatistics.setCellValue(month + " 월 통계");
					
					XSSFCell cellAllStatistics1 = sheet.createRow(rowindex+1).createCell(0);
					cellAllStatistics1.setCellStyle(cellStatisticsStyle);
					
					String [] cellAllStatisticsHeaderContent = {"입고금액", "사용금액", "망실금액", "총 금액"};
					XSSFCell []cellAllStatisticsHeader = new XSSFCell[cellAllStatisticsHeaderContent.length];
					XSSFCell []cellAllStatisticsContent = new XSSFCell[cellAllStatisticsHeaderContent.length];
					
					XSSFCellStyle headerStyle = workbook.createCellStyle();
					headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
					headerStyle.setFont(font);
					XSSFCellStyle contentStyle = workbook.createCellStyle();
					contentStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					contentStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					
					
					for(int cellIndex = 0 ; cellIndex < cellAllStatisticsHeaderContent.length; cellIndex ++) {
						if(cellIndex + 1 == cellAllStatisticsHeaderContent.length) {
							headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
							contentStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
						}
						
						cellAllStatisticsHeader[cellIndex] = sheet.getRow(rowindex).createCell(cellIndex + 1);
						cellAllStatisticsHeader[cellIndex].setCellValue(cellAllStatisticsHeaderContent[cellIndex]);
						
						cellAllStatisticsContent[cellIndex] = sheet.getRow(rowindex + 1).createCell(cellIndex + 1);
						
						cellAllStatisticsHeader[cellIndex].setCellStyle(headerStyle);
						cellAllStatisticsContent[cellIndex].setCellStyle(contentStyle);
					}
					
					
					
					
					workbook.write(fos);
					fos.flush();
					fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public void addProduct(Map<String ,Object> map) {
		
		product = (XlsxVO)map.get("product");
		fis = vo.getXlsx();
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowIndex = 0 ;
			
			while(sheet.getPhysicalNumberOfRows() > rowIndex) {
			if(sheet.getRow(rowIndex).getCell(0).getStringCellValue().equals("월/일")) 
				break;
			
			rowIndex++;
			}
			
			int cellIndex = 1;
			int headerCellIndex = 1;
			while(sheet.getRow(rowIndex).getCell(cellIndex) != null) {
				cellIndex+=4;
			}
			headerCellIndex = cellIndex;
			XSSFCellStyle AllLastStyle = workbook.createCellStyle();
			AllLastStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			AllLastStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			XSSFCellStyle AllStyle = workbook.createCellStyle();
			AllStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,cellIndex,cellIndex + 1));
			
			XSSFCell productName = sheet.getRow(rowIndex).createCell(cellIndex);
			productName.setCellValue(product.getProductName());
			productName.setCellStyle(AllStyle);
			
			cellIndex += 2;
			XSSFCell productLastUpdateDate = sheet.getRow(rowIndex).createCell(cellIndex);
			
			date = new Date();
			timezon = TimeZone.getTimeZone("Asia/Seoul");
			df = new SimpleDateFormat(datefull);
			df.setTimeZone(timezon);
			String fullDate = df.format(date);
			productLastUpdateDate.setCellStyle(AllStyle);
			productLastUpdateDate.setCellValue(fullDate);
			cellIndex ++;
			
			XSSFCell productUnitPrice = sheet.getRow(rowIndex).createCell(cellIndex);
			productUnitPrice.setCellStyle(AllLastStyle);
			productUnitPrice.setCellValue(product.getProductUnitPrice());
			
			rowIndex ++;
			XSSFCell [] productHeader = new XSSFCell[4];
			for(int index = 0; index < 4; index++) {
				productHeader[index] = sheet.getRow(rowIndex).createCell(headerCellIndex);
				
				if(index + 1 == 4)
					productHeader[index].setCellStyle(AllLastStyle);
				else
					productHeader[index].setCellStyle(AllStyle);
				
				productHeader[index].setCellValue(Header[index]);
				headerCellIndex++;
			}
			
			rowIndex++;
			double lastMonthStock = Double.parseDouble(product.getProductStock());
			XSSFCell lastMonthStockCell = sheet.getRow(rowIndex).createCell(cellIndex - 3);
			sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,cellIndex - 3,cellIndex));
			XSSFCellStyle LastMonthStockCellStyle = workbook.createCellStyle();
			LastMonthStockCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			LastMonthStockCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			LastMonthStockCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			XSSFCellStyle LastMonthStockLastCellStyle = workbook.createCellStyle();
			LastMonthStockLastCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			LastMonthStockLastCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			LastMonthStockLastCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			LastMonthStockLastCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			lastMonthStockCell.setCellStyle(LastMonthStockCellStyle);
			lastMonthStockCell.setCellValue(lastMonthStock);
			sheet.getRow(rowIndex).createCell(cellIndex - 2).setCellStyle(LastMonthStockCellStyle);
			sheet.getRow(rowIndex).createCell(cellIndex - 1).setCellStyle(LastMonthStockCellStyle);
			sheet.getRow(rowIndex).createCell(cellIndex).setCellStyle(LastMonthStockLastCellStyle);
			
			rowIndex ++;
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("workbook", workbook);
			map1.put("sheet", sheet);
			map1.put("cellIndex",cellIndex - 3);
			map1.put("lastMonthStock", lastMonthStock);
			
			Map<String , Object> resultMap = new HashMap<String,Object>();
			resultMap = statisticsSetting(map1);
			sheet = (XSSFSheet) resultMap.get("sheet");
			int LastRowIndex = (int) resultMap.get("rowIndex");
			
			XSSFCellStyle rightBorderStyle = workbook.createCellStyle();
			rightBorderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			rightBorderStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			
			XSSFCellStyle TenBorderStyle = workbook.createCellStyle();
			TenBorderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			TenBorderStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			XSSFCellStyle TenLastBorderStyle = workbook.createCellStyle();
			TenLastBorderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			TenLastBorderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			TenLastBorderStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			
			int countIndex = 1 ;
			int startCellIndex = cellIndex - 3;
			int lastCellIndex = cellIndex + 1;
			for(int index = rowIndex ; index < LastRowIndex; index++) {
				if(countIndex % 10 == 0)
				{
					for(int i = startCellIndex; i < lastCellIndex ; i ++) {
						if(i + 1 ==  lastCellIndex)
							sheet.getRow(index).createCell(i).setCellStyle(TenLastBorderStyle);
						else
							sheet.getRow(index).createCell(i).setCellStyle(TenBorderStyle);
						
						sheet.getRow(index).getCell(i).setCellValue("");
					}
				}
				else
					sheet.getRow(index).createCell(cellIndex).setCellStyle(rightBorderStyle);
				
				countIndex ++;
			}
			
			
			fos = vo.setXlsx();
			workbook.write(fos);
			fos.flush();
			fos.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<String , Object> statisticsSetting(Map<String, Object> map) {

		
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = (XSSFSheet) map.get("sheet");
		int cellIndex = (int) map.get("cellIndex");
		double lastMonthStock = (double) map.get("lastMonthStock");
		int rowIndex = 30 ;
		int MaximumRowIndex = sheet.getPhysicalNumberOfRows();
		while(rowIndex < MaximumRowIndex) {
			if(sheet.getRow(rowIndex).getCell(0).getStringCellValue().equals("총합"))
				break;
			
			rowIndex++;
		}
		
		String []StatisticsHeader = {"총 입고량","총 사용량","총 망실량","재고"};
		
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		XSSFCellStyle contentStyle = workbook.createCellStyle();
		XSSFCellStyle headerLastStyle = workbook.createCellStyle();
		XSSFCellStyle contentLastStyle = workbook.createCellStyle();
		
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		headerStyle.setFont(font);
		
		
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		
		headerLastStyle = headerStyle;
		headerLastStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		contentStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		contentStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		
		contentLastStyle = contentStyle;
		contentLastStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		XSSFCell []CellHeader = new XSSFCell[StatisticsHeader.length];
		XSSFCell []CellContent = new XSSFCell[StatisticsHeader.length];
		for(int index = 0; index < StatisticsHeader.length; index++) {
			CellHeader[index] = sheet.getRow(rowIndex).createCell(cellIndex);
			CellHeader[index].setCellValue(StatisticsHeader[index]);
			CellHeader[index].setCellStyle(headerStyle);
			
			CellContent[index] = sheet.getRow(rowIndex + 1).createCell(cellIndex);
			CellContent[index].setCellValue(0);
			CellContent[index].setCellStyle(contentStyle);
			
			if(index + 1 == StatisticsHeader.length) {
				CellHeader[index].setCellStyle(headerLastStyle);
				CellContent[index].setCellStyle(contentLastStyle);
				CellContent[index].setCellValue(lastMonthStock);
			}
			cellIndex++;
		}
		Map<String ,Object > resultMap = new HashMap<String , Object>();
		resultMap.put("sheet", sheet);
		resultMap.put("rowIndex",rowIndex);
		
		
		return resultMap;
	}
	
	public void saveProductDetail(Map<String,Object> map) {

		List<String[]> contents = (List<String[]>) map.get("content");
		
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		ServiceRead read = new ServiceRead();
		
		int startCellIndex = (int) map.get("startCellIndex");
		int rowIndexs [] = read.getRowIndex(sheet);
		int startRowIndex = rowIndexs[0];
		int contentRowIndex= rowIndexs[0] + 3;
		int startStatisticsRowIndex = rowIndexs[1];
		
		date = new Date();
		timezon = TimeZone.getTimeZone("Asia/Seoul");
		df = new SimpleDateFormat(datefull);
		df.setTimeZone(timezon);
		String fullDate = df.format(date);
		
		//lastUpdate Cell
		sheet.getRow(startRowIndex).getCell(startCellIndex + 2).setCellValue(fullDate);
		
		XSSFCellStyle dualStyle = workbook.createCellStyle();
		dualStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		
		int listIndex = 0;
		for(int rowIndex = contentRowIndex; rowIndex < startStatisticsRowIndex - 1; rowIndex++) {
			int contentIndex = 0;
			String []content = contents.get(listIndex);
			for(int cellIndex = startCellIndex; cellIndex < startCellIndex + 4; cellIndex++) {
				XSSFCell cell = null;
				if(sheet.getRow(rowIndex).getCell(cellIndex) != null)
				{
					cell = sheet.getRow(rowIndex).getCell(cellIndex);
				}
				else
				{
					cell = sheet.getRow(rowIndex).createCell(cellIndex);
					cell.setCellStyle(dualStyle);
				}
				if(!content[contentIndex].equals(""))
					cell.setCellValue(Double.parseDouble(content[contentIndex]));
				
				contentIndex++;
			}
			listIndex++;
		}
		
		String []statisticsContent = contents.get(listIndex);
		int statisticsContentIndex = 0 ;
		for(int i = startCellIndex; i < startCellIndex + 4; i ++) {
			sheet.getRow(startStatisticsRowIndex).getCell(i).
			setCellValue(Double.parseDouble(statisticsContent[statisticsContentIndex]));
			statisticsContentIndex++;
		}
		FileOutputStream fos = vo.setXlsx();
		try {
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void TodayUpdate(Map<String,Object> map) {
		XSSFWorkbook workbook = (XSSFWorkbook) map.get("workbook");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		ServiceRead read = new ServiceRead();
		
		int [] rowindexs = read.getRowIndex(sheet); 
		int headerRowIndex = rowindexs[0];
		int statisticsRowIndex = rowindexs[1];
		int todayRowIndex = (int) map.get("rowIndex");
		
		date = new Date();
		timezon = TimeZone.getTimeZone("Asia/Seoul");
		df = new SimpleDateFormat(datefull);
		df.setTimeZone(timezon);
		String fullDate = df.format(date);
		
		String content[][] = (String[][]) map.get("content");
		String statisticsContent[][] = (String[][]) map.get("statisticsContent");
		
		XSSFRow headerRow = sheet.getRow(headerRowIndex);
		XSSFRow todayRow = sheet.getRow(todayRowIndex);
		XSSFRow statisticsRow = sheet.getRow(statisticsRowIndex);
		
		int cellIndex = 1;
		for(int index = 0; index < content.length; index++) {
			headerRow.getCell(cellIndex + 2).setCellValue(fullDate);
			int contentIndex = 0 ;
			for(int i = cellIndex; i < cellIndex + 4; i ++) {
				double Value = Double.parseDouble(content[index][contentIndex]); 
			if(todayRow.getCell(i) != null)
			{
				todayRow.getCell(i).setCellValue(Value);
			}
			else
			{
				todayRow.createCell(i).setCellValue(Value);
			}
			double statisticsValue = Double.parseDouble(statisticsContent[index][contentIndex]);
			if(contentIndex == 3)
				statisticsRow.getCell(i).setCellValue(Value);
			else
			{	
				statisticsValue += statisticsRow.getCell(i).getNumericCellValue();
				statisticsRow.getCell(i).setCellValue(statisticsValue);
			}
			contentIndex++;
			}
			cellIndex += 4;
		}
		
		FileOutputStream fos = vo.setXlsx();
		
		try {
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
