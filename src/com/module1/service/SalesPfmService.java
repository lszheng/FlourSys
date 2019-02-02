package com.module1.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface SalesPfmService {
	
	/**
	 * 获取excle文件模板
	 * @param path
	 * @return
	 */
	public XSSFWorkbook getDirExcel(String path);
	
	/**
	 * 更新excle文件类容
	 * @param wbook
	 * @return
	 */
	public XSSFWorkbook updateExcel(XSSFWorkbook wbook);
	
	

}
