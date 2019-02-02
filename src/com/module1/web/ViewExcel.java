package com.module1.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ViewExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook book, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		 String filename = "业务员明细汇总表.xls";//设置下载时客户端Excel的名称         
		 //filename = encodeFilename(filename, request);//处理中文文件名      
         response.setContentType("application/vnd.ms-excel");         
         response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "UTF-8"));         
         OutputStream ouputStream = response.getOutputStream();         
         book.write(ouputStream);         
         ouputStream.flush();         
         ouputStream.close();   

	}

}
