package Excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import FileController.FileVO;

public class ExcelController {
	
	Calendar cal;
	TimeZone time;
	Date date;
	DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd/HH:mm:ss");
	
	FileVO vo = new FileVO();
	
	private ServiceWrite serviceWriter;
	private ServiceRead serviceRead;
	
	public Map<String , Object> getXlsx() {
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		ServiceWrite servicewrite = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			FileInputStream fis = vo.getXlsx();
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
				resultMap.put("result", "가져올 데이터가 없습니다.");
				return resultMap;
			}
		}catch(Exception e){
			e.printStackTrace();
			servicewrite.XlsxSetting(workbook,false);
			resultMap.put("result", "가져올 데이터가 없습니다.");
			return resultMap;
		}
		serviceRead = new ServiceRead();
		ServiceRead serviceRead = new ServiceRead();
		resultMap.put("workbook", workbook);
		resultMap = serviceRead.ReadXlsx(resultMap);
		return resultMap;
	}
	
	
	public void addProduct(XlsxVO vo) {
		serviceWriter = new ServiceWrite();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("product", vo);
		serviceWriter.addProduct(map);
	}
	
	public Map<String,Object> productDetailInfo(int index) {
		int startCellIndex = index * 4 + 1;
		Map<String , Object> map = new HashMap<String,Object>();

		FileInputStream fis = vo.getXlsx();
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map.put("cellIndex",startCellIndex);
		map.put("workbook",workbook);
		map.put("startCellIndex", startCellIndex);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		serviceRead = new ServiceRead();
		resultMap = serviceRead.ReadProductDetailXlsx(map);
		
		return resultMap;
	}
	
	public Object productDetailWriter(Map<String,Object> map) {
		
		FileInputStream fis = vo.getXlsx();
		XSSFWorkbook workbook = null;
		
		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("workbook", workbook);
		serviceWriter = new ServiceWrite();
		serviceWriter.saveProductDetail(map);
		return null;
	}
}
