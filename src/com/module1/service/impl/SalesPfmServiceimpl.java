package com.module1.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.module1.dao.impl.BaseDaoImpl;
import com.module1.service.SalesPfmService;
import com.module1.util.ExcelUtils;

@Service
public class SalesPfmServiceimpl implements SalesPfmService {
	public static final String dbconfig = "com.module1.service.impl.ProdServiceImpl";
	@Autowired
	private BaseDaoImpl prodDao;

	@Override
	public XSSFWorkbook getDirExcel(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		File[] fs = file.listFiles();
		String filepathname = null;
		for (File f : fs) {
			if (f.isFile()) {
				filepathname = f.toString();
				break;
			}
		}

		// 创建输入流
		InputStream is = null;
		try {
			is = new FileInputStream(filepathname);
			return new XSSFWorkbook(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public XSSFWorkbook updateExcel(XSSFWorkbook wbook) {
		// 获取Sheet个数，根据sheet下标获取sheet。
		int n = wbook.getNumberOfSheets();
		System.out.println("Sheet 个数:" + n);

		List nexists = new ArrayList();
		
		for (int i = 0; i < n; i++) {
			Sheet sheet = wbook.getSheetAt(i);
			
			//设置强制使用公司 数字数据类型时才会生效
			sheet.setForceFormulaRecalculation(true);
			
			// 获取sheet name， sheet name 为销售员名称
			String sheetName = sheet.getSheetName();
			System.out.println("sheetName:" + sheetName);
			// 查询
			Map param = new HashMap();
			param.put("salesname", sheetName);
			List proList = prodDao.queryForList(dbconfig + ".queryMaterielTotalBySalesName", param, HashMap.class);
			// List proList =
			// prodDao.queryForList(dbconfig+".queryMaterielTotalBySalesName",
			// param);

			if (null == proList) {
				System.out.println("该sheet名称无对应销售业绩");
				continue;
			}
			// 保存EXCEL中不存在的产品，显示在最后面
			StringBuffer sb = new StringBuffer();
			
			for (int j = 0; j < proList.size(); j++) {
				Map mateMap = (HashMap) proList.get(j);
				String materielname = mateMap.get("materielname").toString();
				
				String tontotal = "0";
				try {
					tontotal = mateMap.get("tontotal").toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				String avgprice = "0";
				try {
					avgprice = mateMap.get("avgprice").toString();
				} catch (Exception e) {
					
				}
				
				int lastrn = sheet.getLastRowNum();
				System.out.println("sheet 总行数:" + lastrn);
				// sheet.getc
				boolean exist = false;

				for (int k = 0; k < lastrn; k++) {
					Row row = sheet.getRow(k);
					Cell namecell = row.getCell(6);
					if (namecell == null) {
						continue;
					}

					String materielnameExcel = namecell.getStringCellValue();

					if (materielname.equals(materielnameExcel)) {
						Cell toncell = row.getCell(9);
						// 保留两位小数，四舍五入
						BigDecimal bg = new BigDecimal(numfmt(tontotal, "#0.00")).setScale(2, RoundingMode.UP);
						
						toncell.setCellValue(bg.doubleValue());
						
						//个人实际售价
						System.out.println("个人实际售价:"+avgprice);
						Cell avgPriceCell = row.getCell(12);
						;
						
						BigDecimal avgBg = new BigDecimal(numfmt(avgprice, "#")).setScale(0, RoundingMode.UP);
						avgPriceCell.setCellValue(avgBg.intValue());
						
						exist = true;
						
						
						
						
					}
				}

				if (!exist) {
					sb.append(materielname);
					sb.append(":");
					sb.append(tontotal);
					sb.append("|");
					
					String tmp[] = {sheetName, materielname, numfmt(tontotal, "#0.00"),numfmt(avgprice, "#"), "未能匹配"};
					nexists.add(tmp);
				}

			}

			// 增加一行
			Row newrow = sheet.createRow(sheet.getLastRowNum() + 1);
			Cell newcell = newrow.createCell(6);
			newcell.setCellValue("表中不存在的产品:" + sb.toString());

			
		}

		// 不匹配列表

		String sheetName = "绩效表中不存在的产品统计表";
		String title[] = {"业务员","物料名称","顿数","个人实际售价","标识"};
		
		
		wbook = ExcelUtils.createSheet(wbook, sheetName, title, nexists);
		
		return wbook;
	}
	
	public static String numfmt(Object obj, String format) {
		try {
			DecimalFormat df = new DecimalFormat(format);

			return df.format(Double.parseDouble(obj.toString()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	

}
