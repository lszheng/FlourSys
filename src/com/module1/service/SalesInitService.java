package com.module1.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface SalesInitService {
	
	public boolean uploadFile(File file);
	
	/*public void export(ServletOutputStream out);
	
	public HSSFWorkbook exportExcel();
	
	public void delete();
*/
	public List queryInitData(Map param);
}
