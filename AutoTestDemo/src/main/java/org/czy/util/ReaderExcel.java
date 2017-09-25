package org.czy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.czy.entity.Description;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author dengpeng
 *
 */
public class ReaderExcel {
	private final static Logger LOG = Logger.getLogger(ReaderExcel.class);
	
	private static List<List<List<String>>> readXls(String path){
		List<List<List<String>>> result = new ArrayList<List<List<String>>>();
		try {
			InputStream is = new FileInputStream(path);
			HSSFWorkbook workBook = new HSSFWorkbook(is);
			//遍历Excel中sheet数
			for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet sheet = workBook.getSheetAt(sheetIndex);
				if(null==sheet){
					continue;
				}
				List<List<String>> tableList = new ArrayList<List<String>>();
				//遍历sheet中所有行
				for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					HSSFRow row = sheet.getRow(rowIndex);
					int colMinIndex = row.getFirstCellNum();
					int colMaxIndex = row.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					//遍历行中所有列
					for (int colIndex = colMinIndex; colIndex < colMaxIndex; colIndex++) {
						HSSFCell col = row.getCell(colIndex);
						if(null==col||col.equals("")){
							continue;
						}
						rowList.add(getStringVal(col));
					}
					tableList.add(rowList);
				}
				result.add(tableList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			return null;
		}
		return result;
	}
	
	
	private static List<List<List<String>>> readXlsx(String path){
		List<List<List<String>>> result = new ArrayList<List<List<String>>>();
		try {
			InputStream is = new FileInputStream(path);
			XSSFWorkbook workBook = new XSSFWorkbook(is);
			//遍历Excel中sheet数
			for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) {
				XSSFSheet sheet = workBook.getSheetAt(sheetIndex);
				if(null==sheet){
					continue;
				}
				List<List<String>> tableList = new ArrayList<List<String>>();
				//遍历sheet中所有行
				for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					XSSFRow row = sheet.getRow(rowIndex);
					int colMinIndex = row.getFirstCellNum();
					int colMaxIndex = row.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					//遍历行中所有列
					for (int colIndex = colMinIndex; colIndex < colMaxIndex; colIndex++) {
						XSSFCell col = row.getCell(colIndex);
						if(null==col||col.equals("")){
							continue;
						}
						rowList.add(getStringVal(col));
					}
					tableList.add(rowList);
				}
				result.add(tableList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			return null;
		}
		return result;
	}
	
	
	public static List<List<List<String>>> readXls(InputStream is){
		List<List<List<String>>> result = new ArrayList<List<List<String>>>();
		try {
			HSSFWorkbook workBook = new HSSFWorkbook(is);
			//遍历Excel中sheet数
			for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet sheet = workBook.getSheetAt(sheetIndex);
				if(null==sheet){
					continue;
				}
				List<List<String>> tableList = new ArrayList<List<String>>();
				//遍历sheet中所有行
				for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					HSSFRow row = sheet.getRow(rowIndex);
					int colMinIndex = row.getFirstCellNum();
					int colMaxIndex = row.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					//遍历行中所有列
					for (int colIndex = colMinIndex; colIndex < colMaxIndex; colIndex++) {
						HSSFCell col = row.getCell(colIndex);
						if(null==col||col.equals("")){
							rowList.add("");
							continue;
						}
						rowList.add(getStringVal(col));
					}
					tableList.add(rowList);
				}
				result.add(tableList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			return null;
		}
		return result;
	}
	
	
	public static List<List<List<String>>> readXlsx(InputStream is){
		List<List<List<String>>> result = new ArrayList<List<List<String>>>();
		try {
			XSSFWorkbook workBook = new XSSFWorkbook(is);
			//遍历Excel中sheet数
			for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) {
				XSSFSheet sheet = workBook.getSheetAt(sheetIndex);
				if(null==sheet){
					continue;
				}
				List<List<String>> tableList = new ArrayList<List<String>>();
				//遍历sheet中所有行
				for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					XSSFRow row = sheet.getRow(rowIndex);
					int colMinIndex = row.getFirstCellNum();
					int colMaxIndex = row.getLastCellNum();
					List<String> rowList = new ArrayList<String>();
					//遍历行中所有列
					for (int colIndex = colMinIndex; colIndex < colMaxIndex; colIndex++) {
						XSSFCell col = row.getCell(colIndex);
						if(null==col||col.equals("")){
							rowList.add("");
							continue;
						}
						rowList.add(getStringVal(col));
					}
					tableList.add(rowList);
				}
				result.add(tableList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			return null;
		}
		return result;
	}
	
	
	
	private static String getStringVal(HSSFCell col){
		switch (col.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return col.getBooleanCellValue()?"True":"False";
			case Cell.CELL_TYPE_STRING:
				return col.getStringCellValue();
			case Cell.CELL_TYPE_FORMULA:
				return col.getCellFormula();
			case Cell.CELL_TYPE_NUMERIC:
				col.setCellType(Cell.CELL_TYPE_STRING);
				return col.getStringCellValue();
			default:
				LOG.warn("没有添加此类型数据的判断,类型ID:"+col.getCellType());
				return col.getStringCellValue();
		}
	}
	
	private static String getStringVal(XSSFCell col){
		switch (col.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return col.getBooleanCellValue()?"True":"False";
			case Cell.CELL_TYPE_STRING:
				return col.getStringCellValue();
			case Cell.CELL_TYPE_FORMULA:
				return col.getCellFormula();
			case Cell.CELL_TYPE_NUMERIC:
				col.setCellType(Cell.CELL_TYPE_STRING);
				return col.getStringCellValue();
			default:
				LOG.warn("没有添加此类型数据的判断,类型ID:"+col.getCellType());
				return col.getStringCellValue();
		}
	}
	
	public static List<Map<Integer, List<String>>> formatList(String path){
		List<Map<Integer, List<String>>> list = new ArrayList<Map<Integer,List<String>>>();
		List<List<List<String>>> excel = new ArrayList<List<List<String>>>();
		if(path.substring(path.lastIndexOf(".")+1).equals("xlsx")){
			excel = readXlsx(path);
		}else{
			excel = readXls(path);
		}
		for (List<List<String>> table : excel) {
			Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
			for (List<String> row : table) {
				map.put(Integer.valueOf(row.get(0)), row);
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 格式化数据为键值对格式
	 * @author dengpeng
	 * @param excel
	 * @return
	 */
	public static List<Map<String, List<String>>> formatData(List<List<List<String>>> excel){
		List<Map<String, List<String>>> list = new ArrayList<Map<String,List<String>>>();
		for (List<List<String>> table : excel) {
			Map<String,List<String>> map = new HashMap<String,List<String>>();
			for (List<String> row : table) {
				map.put(row.get(0), row);
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 展现指定用例的数据文件
	 * @author dengpeng
	 * @param excel
	 * @return
	 */
	public static List<List<Map<String, String>>> formatShowData(List<List<List<String>>> excel){
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		for (List<List<String>> table : excel) {
			List<Map<String,String>> rl = new ArrayList<Map<String,String>>();
			for (List<String> row : table) {
				Map<String,String> map = new HashMap<String,String>();
				int i = 0;
				String resultDesc = "";
				for (String col : row) {
					if(i==0){
						map.put("step", col);
					}else if(i==1){
						map.put("desc", col);
					}else{
						resultDesc += ","+col;
					}
					++i;
				}
				map.put("resultDesc", !resultDesc.equals("")?resultDesc.substring(1):resultDesc);
				rl.add(map);
			}
			list.add(rl);
		}
		return list;
	}
	
	/**
	 * 公共用例格式化数据
	 * @author dengpeng
	 * @param list
	 * @return
	 */
	public static Map<String, List<String>> getPubTestData(Map<String, List<String>> baseMap,String step){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String regex = "^"+step+"[0-9]*$";
		for (String key : baseMap.keySet()) {
			if(Pattern.matches(regex, key)){
				String k = key.substring(step.length());
				map.put(k, baseMap.get(key));
			}
		}
		return map;
	}
	
	
	public static void exportExcel(String filepath,String testName,List<List<String>> list,List<String> headDesc){
		if(null==list||list.size()==0){
			LOG.info("数据库没有["+testName+"]用例的数据");
			return;
		}
		String subffix = filepath.substring(filepath.lastIndexOf("."));
		Workbook workbook = null;
		try {
			if(subffix.equals(Final.XLSX)){
				 workbook = new XSSFWorkbook();
			}else{
				 workbook = new HSSFWorkbook();
			}
		}catch(Exception e) {
			LOG.info("It cause Error on CREATING excel workbook: "+e);
		}
		if(workbook != null) {
		    Sheet sheet = workbook.createSheet(testName);
		    Row row0 = sheet.createRow(0);
		    for(int i = 0; i < headDesc.size(); i++) {
		         Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
		         CellStyle style = getStyle(workbook);
		         cell_1.setCellStyle(style);
		         cell_1.setCellValue(headDesc.get(i));
		         sheet.autoSizeColumn(i);
		    }
		    for (int rowNum = 1; rowNum <= list.size(); rowNum++) {
		         Row row = sheet.createRow(rowNum);
		         List<String> col = list.get(rowNum-1);
		         for(int i = 0; i < col.size(); i++) {
		             Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
		             cell.setCellValue(col.get(i));
		         }
		    }
		    try {
		    	 String path = filepath.substring(0,filepath.lastIndexOf(File.separator));
		    	 File file = new File(path);
		    	 if(!file.exists()){
		    		 file.mkdirs();
		    	 }
		         FileOutputStream outputStream = new FileOutputStream(filepath);
		         workbook.write(outputStream);
		         outputStream.flush();
		         outputStream.close();
		    } catch (Exception e) {
		         LOG.error("It cause Error on WRITTING excel workbook: "+e);
		    }
		}
	}
	
	
	 private static CellStyle getStyle(Workbook workbook){
	     CellStyle style = workbook.createCellStyle();
	     style.setAlignment(CellStyle.ALIGN_CENTER); 
	     style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	     // 设置单元格字体
	     Font headerFont = workbook.createFont(); // 字体
//	     headerFont.setFontHeightInPoints((short)12);
	     headerFont.setColor(HSSFColor.BLACK.index);
	     headerFont.setFontName("宋体");
	     headerFont.setBoldweight((short)12);
	     style.setFont(headerFont);
	     style.setWrapText(true);
	     
	     // 设置单元格边框及颜色
	     style.setBorderBottom((short)1);
	     style.setBorderLeft((short)1);
	     style.setBorderRight((short)1);
	     style.setBorderTop((short)1);
	     style.setWrapText(true);
	     return style;
	}    
	
	public static void main(String[] args) {
		String path = "C:\\Users\\Administrator\\Desktop\\1.xls";
//		List<List<List<String>>> excel = new ArrayList<List<List<String>>>();
//		if(path.substring(path.lastIndexOf(".")+1).equals("xlsx")){
//			excel = readXlsx(path);
//		}else{
//			excel = readXls(path);
//		}
//		Map<String, List<String>> m = getPubTestData(formatData(excel).get(0),"8");
//		for (String key : m.keySet()) {
//			System.out.println(m.get(key));
//		}
		
		exportExcel(path,"测试",null,null);
//		for (List<List<String>> table : excel) {
//			for (List<String> row : table) {
//				for (String col : row) {
//					System.out.print(col+"  ");
//				}
//				System.out.println("");
//			}
//		}
		
	}
}
