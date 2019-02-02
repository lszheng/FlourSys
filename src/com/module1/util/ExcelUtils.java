package com.module1.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {

	/**
	 * 读取excel
	 * 
	 * @param filepath
	 *            文件路径
	 * @param startrow
	 *            读取的开始行
	 * @Result 返回一个二维数组（第一维放的是行，第二维放的是列表）
	 * @throws Exception
	 */
	public static String[][] readexcell(String filepath, int startrow) throws Exception {
		// 判断文件是否存在
		File file = new File(filepath);
		if (!file.exists()) {
			throw new IOException("文件" + filepath + "W不存在！");
		}
		// 获取sheet
		Sheet sheet = getSheet(filepath, 0);
		String[][] content = getData(startrow, sheet);
		return content;
	}

	/**
	 * 根据表名获取第一个sheet
	 * 
	 * @param path
	 *            d:\\1213.xml
	 * @return 2003-HSSFWorkbook  2007-XSSFWorkbook
	 * @throws Exception 
	 */
	public static Sheet getSheet(String file, int sheetno) throws Exception {
		// 文件后缀
		String extension = file.lastIndexOf(".") == -1 ? "" : file.substring(file.lastIndexOf("."));
		// 创建输入流
		InputStream is = new FileInputStream(file);
		if (".xls".equals(extension)) {// 2003
			// 获取工作薄
			POIFSFileSystem fs = new POIFSFileSystem(is);
			return new HSSFWorkbook(fs).getSheetAt(sheetno);
		} else if (".xlsx".equals(extension) || ".xlsm".equals(extension)) {
			return new XSSFWorkbook(is).getSheetAt(sheetno);
		} else {
			throw new IOException("文件（" + file + "）,无法识别！");
		}
	}

	/**
	 * 获取一个EXCEL对象
	 * 
	 * @param filepath
	 * @return
	 */
	public static Object getExcel(String filepath) throws Exception{
		// 文件后缀
		String extension = filepath.lastIndexOf(".") == -1 ? "" : filepath.substring(filepath.lastIndexOf("."));
		// 创建输入流
		InputStream is = new FileInputStream(filepath);
		if (".xls".equals(extension)) {// 2003
			// 获取工作薄
			POIFSFileSystem fs = new POIFSFileSystem(is);
			return new HSSFWorkbook(fs);
		} else if (".xlsx".equals(extension) || ".xlsm".equals(extension)) {
			return new XSSFWorkbook(is);
		} else {
			throw new IOException("文件（" + filepath + "）,无法识别！");
		}
	}

	/**
	 * 获取表单数据 wangyue
	 * 
	 * @param startrow
	 * @param sheet
	 * @return 2018年4月26日下午2:25:43
	 */
	private static String[][] getData(int startrow, Sheet sheet) {
		// 得到总行数
		int rowNum = sheet.getLastRowNum() + 1;
		// 根据第一行获取列数
		Row row = (Row) sheet.getRow(0);
		// 获取总列数
		int colNum = row.getPhysicalNumberOfCells();
		// 根据行列创建二维数组
		String[][] content = new String[rowNum - startrow][colNum];
		String[] cols = null;
		// 通过循环，给二维数组赋值
		for (int i = startrow; i < rowNum; i++) {
			row = (Row) sheet.getRow(i);
			cols = new String[colNum];
			for (int j = 0; j < colNum; j++) {
				// 获取每个单元格的值
				cols[j] = getCellValue(row.getCell(j));
				// 把单元格的值存入二维数组
				content[i - startrow][j] = cols[j];
			}
		}
		return content;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {
		Object result = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				// 在excel里,日期也是数字,在此要进行判断
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = cell.getDateCellValue();
					result = sdf.format(date);
				} else {
					// DecimalFormat df=new DecimalFormat("#");
					// result=df.format(cell.getNumericCellValue());
					result = cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}

		return result.toString();
	}
	
	public static XSSFWorkbook createSheet(XSSFWorkbook wbook, String sheetName, String[] title, List data) {
		Sheet sheet = wbook.createSheet(sheetName);
		Row titleRow = sheet.createRow(sheet.getFirstRowNum());
		for (int i = 0; i < title.length; i++) {
			Cell cell = titleRow.createCell(i);
			cell.setCellValue(title[i]);
		}

		for (int i = 0; i < data.size(); i++) {
			String[] datas = (String[]) data.get(i);
			Row contenRow = sheet.createRow(sheet.getLastRowNum() + 1);

			for (int k = 0; k < datas.length; k++) {
				Cell cell = contenRow.createCell(k);
				cell.setCellValue(datas[k]);
			}
		}

		return wbook;
	}

}
