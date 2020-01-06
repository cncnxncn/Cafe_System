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
	
	FileVO vo = new FileVO();
	
	private ServiceWrite serviceWrite;
	private ServiceRead serviceRead;
	
	public Map<String , Object> getXlsx() {
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		ServiceWrite servicewrite = null;
<<<<<<< HEAD
		FileInputStream fis = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			fis = vo.getXlsx();
=======
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			FileInputStream fis = vo.getXlsx();
>>>>>>> master
			workbook = new XSSFWorkbook(fis);
			sheet = null;
			servicewrite = new ServiceWrite();
			
		} catch (Exception e) {
			resultMap.put("result", "파일이 없습니다.");
			return resultMap;
		}
		
		try {
			sheet = workbook.getSheetAt(0);
			if(sheet.getPhysicalNumberOfRows() < 30) {
				servicewrite.XlsxSetting(workbook,true);
				resultMap.put("result", "가져올 데이터가 없습니다");
				return resultMap;
			}
		}catch(Exception e){
			e.printStackTrace();
			servicewrite.XlsxSetting(workbook,false);
			resultMap.put("result", "가져올 데이터가 없습니다");
			return resultMap;
		}
		
		ServiceRead serviceRead = new ServiceRead();
		resultMap.put("workbook", workbook);
<<<<<<< HEAD
		resultMap.put("product",serviceRead.ReadXlsx(resultMap));
		resultMap.put("result", "성공");
		
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
=======
		resultMap = serviceRead.ReadXlsx(resultMap);
		resultMap.put("result", "성공");
>>>>>>> master
		return resultMap;
	}
	
	
	public Object addProduct(XlsxVO vo) {
		serviceWrite = new ServiceWrite();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("product", vo);
		serviceWrite.addProduct(map);
		
		return null;
	}
}
