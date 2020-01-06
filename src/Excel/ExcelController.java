package Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellAlignment;

import FileController.FileController;
import FileController.FileVO;

public class ExcelController {
	
	Calendar cal;
	TimeZone time;
	Date date;
	DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd/HH:mm:ss");
	
	FileVO vo = new FileVO();
	
	private ServiceWrite serviceWrite;
	private ServiceRead serviceRead;
	
	public Object getXlsx() {
		try {
			FileInputStream fis = vo.getXlsx();
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = null;
			ServiceWrite servicewrite = new ServiceWrite();
			try {
				sheet = workbook.getSheetAt(0);
//				System.out.println(sheet.getPhysicalNumberOfRows());
				if(sheet.getPhysicalNumberOfRows() < 30) {
					servicewrite.XlsxSetting(workbook,true);
				}
			}catch(Exception e){
				e.printStackTrace();
				servicewrite.XlsxSetting(workbook,false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object writeXlsx() {
		
		return null;
	}
	
	public Object addProduct(XlsxVO vo) {
		serviceWrite = new ServiceWrite();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("product", vo);
		serviceWrite.addProduct(map);
		
		return null;
	}
}
