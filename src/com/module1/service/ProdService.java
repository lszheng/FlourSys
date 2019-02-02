package com.module1.service;

import java.io.File;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface ProdService {
	
	public String importProd(File file);
	
	public void export(ServletOutputStream out);
	
	public HSSFWorkbook exportExcel();
	
	public void delete();

}
