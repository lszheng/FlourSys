package com.module1.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.module1.bean.SalesDtlBean;
import com.module1.dao.impl.BaseDaoImpl;
import com.module1.service.ProdService;
import com.module1.util.ExcelUtils;
import com.module1.util.XLSXCovertCSVReader;

@Service
@Scope("prototype")
public class ProdServiceImpl implements ProdService {

	@Autowired
	private BaseDaoImpl prodDao;

	public static final String BusinessConfig = "com.module1.service.impl.ProdServiceImpl";

	@Override
	public String importProd(File file) {
		List<SalesDtlBean> listProd = new ArrayList<SalesDtlBean>();
		
		// 删除表中的明细数据
		prodDao.delete(BusinessConfig + ".deleteTotal", null);
		try {
			// 读取文件
			// String[][] mm = ExcelUtils.readexcell(file.getPath(), 1);

			List<String[]> list = XLSXCovertCSVReader.readerExcel(file.getPath(), "Sheet1", 17);
			int i = 0;
			for (String[] record : list) {
				if (i == 0) {
					i++;
					continue;
				}
				SalesDtlBean sdb = new SalesDtlBean();
				sdb.setId(Integer.toString(i));
				sdb.setCustomer(record[0]);
				sdb.setSalesname(record[1]);
				sdb.setMaterielName(record[2]);
				System.out.println(record[3]);
				sdb.setDateTime(record[3]);
				sdb.setSimplePrice(record[4]);
				sdb.setTon(record[5]);
				sdb.setFreight(record[6]);
				sdb.setTotalFreight(record[7]);
				sdb.setTotalPrice(record[8]);
				listProd.add(sdb);
				i++;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("listProd", listProd);
			int count1 = prodDao.insert(BusinessConfig + ".insertProduct", map);
		
			System.out.println("入库条数：" + count1);
		} catch (Exception e) {
			System.out.println("异常！！！:" + e.getMessage());
		}

		return "导入成功";

	}

	public void exportSales(ServletOutputStream out) {
		String[] titles = { "客户名称", "业务员", "物料名称", "日期", "单价", "吨", "运费", "总运费", "开单金额 (总和)", "均价", "明细、汇总" };
		Map<String, Object> manMap = null;

		List<String> listMaterielname = null;// 当前业务员的所有物料
		// 根据Excel文件获取Excel对象
		String filepath = "E:\\springUpload\\2019010216493311月销售明细表.xlsx";

		try {
			XSSFWorkbook xsswb = (XSSFWorkbook) ExcelUtils.getExcel(filepath);

			// 获取业务员
			List<String> listMan = getManNameList();// 所有业务员
			for (int i = 0; i < listMan.size(); i++) {

				String saleName = listMan.get(i);
				// 创建一个以业务员为名称的sheet
				XSSFSheet xssfx = xsswb.createSheet(saleName);

				// xssfx.createRow(arg0)

			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void export(ServletOutputStream out) {
		String[] titles = { "客户名称", "业务员", "物料名称", "日期", "单价", "吨", "运费", "总运费", "开单金额 (总和)", "均价", "明细、汇总" };
		Map<String, Object> manMap = null;

		List<String> listMaterielname = null;// 当前业务员的所有物料
		List<String> listMan = getManNameList();// 所有业务员

		// 第一步，创建一个workbook，对应一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();

		for (int k = 0; k < listMan.size(); k++) {
			try {
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet hssfSheet = workbook.createSheet(listMan.get(k));
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow hssfRow = hssfSheet.createRow(0);
				// 第四步，创建单元格，并设置值表头 设置表头居中

				// 样式一
				HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
				// 居中样式
				hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				// 设置字体
				HSSFFont font = workbook.createFont();
				font.setFontName("宋体");
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
				// font2.setFontHeightInPoints((short) 12);
				hssfCellStyle.setFont(font);// 选择需要用到的字体格式

				// 样式二
				HSSFCellStyle hssfCellStyle1 = workbook.createCellStyle();
				// 设置字体
				HSSFFont font2 = workbook.createFont();
				font2.setFontName("宋体");
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
				hssfCellStyle1.setFont(font2);// 选择需要用到的字体格式

				HSSFCell hssfCell = null;
				for (int i = 0; i < titles.length; i++) {
					hssfCell = hssfRow.createCell(i);// 列索引从0开始
					hssfCell.setCellValue(titles[i]);// 列名1
					hssfCell.setCellStyle(hssfCellStyle);// 列居中显示
				}

				manMap = new HashMap<String, Object>();
				manMap.put("salesman", listMan.get(k));

				listMaterielname = new ArrayList<String>();
				listMaterielname = getMaterielListBySales(manMap);

				Double tonTotalY = 0.0;
				Double freTotalY = 0.0;
				Double freightTotalY = 0.0;
				Double priceTotalY = 0.0;

				// 第五步，写入实体数据
				List<SalesDtlBean> listProd = null;
				// 循环获取数据设置到excel表格中
				for (int i = 0; i < listMaterielname.size(); i++) {
					manMap.put("materiel", listMaterielname.get(i));
					listProd = new ArrayList<SalesDtlBean>();
					listProd = getProdNameList(manMap);
					/*
					 * for(ProdBean str : listProd){ System.out.println(str); }
					 */
					// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					Double tonTotal = 0.0;
					Double freTotal = 0.0;
					Double freightTotal = 0.0;
					Double priceTotal = 0.0;
					Double averagePrice = 0.0;

					int temp = hssfRow.getRowNum();
					if (listProd != null && !listProd.isEmpty()) {
						for (int j = 0; j < listProd.size(); j++) {
							hssfRow = hssfSheet.createRow(j + temp + 1);
							SalesDtlBean sdb = listProd.get(j);
							// System.out.println("1:"+prod.getSalesMan()+"-"+prod.getMaterielName());
							// 第六步，创建单元格，并设置值
							hssfRow.createCell(0).setCellValue(sdb.getCustomer());
							hssfRow.createCell(1).setCellValue(sdb.getSalesname());
							hssfRow.createCell(2).setCellValue(sdb.getMaterielName());
							hssfRow.createCell(3).setCellValue(sdb.getDateTime());
							hssfRow.createCell(4).setCellValue(Double.parseDouble(sdb.getSimplePrice()));
							hssfRow.createCell(5).setCellValue(Double.parseDouble(sdb.getTon()));
							if (sdb.getFreight() != null && !"".equals(sdb.getFreight())) {
								hssfRow.createCell(6).setCellValue(Double.parseDouble(sdb.getFreight()));
								freTotal = freTotal + Double.parseDouble(sdb.getFreight());
							}
							hssfRow.createCell(7).setCellValue(Double.parseDouble(sdb.getTotalFreight()));
							hssfRow.createCell(8).setCellValue(Double.parseDouble(sdb.getTotalPrice()));
							hssfRow.createCell(10).setCellValue("明细");

							// 计算汇总行
							tonTotal = tonTotal + Double.parseDouble(sdb.getTon());
							// freTotal = freTotal +
							// Double.parseDouble(prod.getFreight());
							freightTotal = freightTotal + Double.parseDouble(sdb.getTotalFreight());
							priceTotal = priceTotal + Double.parseDouble(sdb.getTotalPrice());
							averagePrice = (priceTotal - freightTotal) / tonTotal;
						}
					}
					// 计算单个物料的汇总
					int temp1 = hssfRow.getRowNum();
					// System.out.println("当前行:"+temp1);
					hssfRow = hssfSheet.createRow(temp1 + 1);
					// 第六步，创建单元格，并设置值
					HSSFCell hssfCell1 = null;
					hssfCell1 = hssfRow.createCell(2);
					hssfCell1.setCellValue(listProd.get(0).getMaterielName() + " 汇总");
					hssfCell1.setCellStyle(hssfCellStyle1);// 加粗
					hssfRow.createCell(5).setCellValue(tonTotal);
					hssfRow.createCell(6).setCellValue(freTotal);
					hssfRow.createCell(7).setCellValue(freightTotal);
					hssfRow.createCell(8).setCellValue(priceTotal);
					hssfRow.createCell(9).setCellValue("");
					hssfRow.createCell(10).setCellValue("汇总");

					// 计算汇总行
					tonTotalY = tonTotalY + tonTotal;
					freTotalY = freTotalY + freTotal;
					freightTotalY = freightTotalY + freightTotal;
					priceTotalY = priceTotalY + priceTotal;

				}

				// 计算汇总
				int temp1 = hssfRow.getRowNum();
				hssfRow = hssfSheet.createRow(temp1 + 1);
				// 第六步，创建单元格，并设置值
				HSSFCell hssfCell1 = null;
				hssfCell1 = hssfRow.createCell(2);
				hssfCell1.setCellValue("汇总");
				hssfCell1.setCellStyle(hssfCellStyle1);// 加粗
				hssfRow.createCell(5).setCellValue(tonTotalY);
				hssfRow.createCell(6).setCellValue(freTotalY);
				hssfRow.createCell(7).setCellValue(freightTotalY);
				hssfRow.createCell(8).setCellValue(priceTotalY);

			} catch (Exception e) {
				System.out.println("4444444444:" + e.getMessage());
			}
		}

		// 第七步，将文件输出到客户端浏览器
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("3333333333333:" + e.getMessage());
		}

	}

	@Override
	public HSSFWorkbook exportExcel() {
		String[] titles = { "客户名称", "业务员", "物料名称", "日期", "单价", "吨", "运费", "总运费", "开单金额 (总和)", "均价", "明细、汇总" };

		// 查询业务员
		List<String> salesList = getManNameList();

		// 第一步，创建一个workbook，对应一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();

		for (int k = 0; k < salesList.size(); k++) {
			try {
				// 定义两个样式
				// 样式一
				HSSFCellStyle style1 = workbook.createCellStyle();
				// 居中样式
				style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				// 设置边框
				style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
				// 设置字体
				HSSFFont font = workbook.createFont();
				font.setFontName("宋体");
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
				style1.setFont(font);// 选择需要用到的字体格式

				// 样式二
				HSSFCellStyle style2 = workbook.createCellStyle();
				// 居中样式
				style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				// 设置边框
				style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				//设置背景颜色
				style2.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				//style2.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				// 设置字体
				HSSFFont font2 = workbook.createFont();
				font2.setFontName("宋体");
				// font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
				style2.setFont(font2);// 选择需要用到的字体格式

				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet hssfSheet = workbook.createSheet(salesList.get(k));

				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow hssfRow = hssfSheet.createRow(0);

				// 第四步，创建单元格，并设置表头值 设置表头居中
				HSSFCell hssfCell = null;
				style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				for (int i = 0; i < titles.length; i++) {
					hssfCell = hssfRow.createCell(i);// 列索引从0开始
					hssfCell.setCellValue(titles[i]);// 列名1

					hssfCell.setCellStyle(style1);// 列居中显示
				}
				style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);

				// 当前业务员的所有物料
				Map<String, Object> manMap = new HashMap<String, Object>();
				manMap.put("salesname", salesList.get(k));

				List<String> materielList = new ArrayList<String>();
				materielList = getMaterielListBySales(manMap);

				Double tonTotalY = 0.0;
				Double freTotalY = 0.0;
				Double freightTotalY = 0.0;
				Double priceTotalY = 0.0;

				// 第五步，写入实体数据
				// sdbList = null;
				// 循环获取数据设置到excel表格中
				for (int i = 0; i < materielList.size(); i++) {
					manMap.put("materiel", materielList.get(i));

					List<SalesDtlBean> sdbList = new ArrayList<SalesDtlBean>();
					sdbList = getProdNameList(manMap);

					Double tonTotal = 0.0;
					Double freTotal = 0.0;
					Double freightTotal = 0.0;
					Double priceTotal = 0.0;
					int temp = hssfRow.getRowNum();
					System.out.println("明细当前行数：" + temp);
					if (sdbList != null && !sdbList.isEmpty()) {

						for (int j = 0; j < sdbList.size(); j++) {
							// 创建明细行
							hssfRow = hssfSheet.createRow(j + temp + 1);
							SalesDtlBean sdb = sdbList.get(j);
							createDtlRow(workbook, hssfRow, style2, sdb);
							// 计算汇总行
							if (sdb.getFreight() != null && !"".equals(sdb.getFreight())) {
								freTotal = freTotal + Double.parseDouble(sdb.getFreight());
							}
							tonTotal = tonTotal + Double.parseDouble(sdb.getTon());
							freightTotal = freightTotal + Double.parseDouble(sdb.getTotalFreight());
							priceTotal = priceTotal + Double.parseDouble(sdb.getTotalPrice());
						}
						// 计算单个物料的汇总
						int temp1 = hssfRow.getRowNum();
						System.out.println("物料的汇总当前行数：" + temp1);
						hssfRow = hssfSheet.createRow(temp1 + 1);
						Map<String, Object> totalMap = new HashMap<String, Object>();
						totalMap.put("totalName", sdbList.get(0).getMaterielName() + " 汇总");
						totalMap.put("tonTotal", tonTotal);
						totalMap.put("freTotal", freTotal);
						totalMap.put("freightTotal", freightTotal);
						totalMap.put("priceTotal", priceTotal);
						totalMap.put("avgPrice", (priceTotal - freightTotal) / tonTotal);
						createTotalRow(workbook, hssfRow, style1, totalMap);

						// 计算汇总行
						tonTotalY = tonTotalY + tonTotal;
						freTotalY = freTotalY + freTotal;
						freightTotalY = freightTotalY + freightTotal;
						priceTotalY = priceTotalY + priceTotal;
					}

				}

				// 计算总汇总
				int temp1 = hssfRow.getRowNum();
				System.out.println("计算总汇总当前行数：" + temp1);
				hssfRow = hssfSheet.createRow(temp1 + 1);
				Map<String, Object> totalMap = new HashMap<String, Object>();
				totalMap.put("totalName", "汇总");
				totalMap.put("tonTotal", tonTotalY);
				totalMap.put("freTotal", freTotalY);
				totalMap.put("freightTotal", freightTotalY);
				totalMap.put("priceTotal", priceTotalY);
				totalMap.put("avgPrice", "");
				createTotalRow(workbook, hssfRow, style1, totalMap);

				// 自适应列宽
				//hssfSheet.autoSizeColumn(1, true);
				
				for (int i = 0; i < titles.length; i++) {  
					hssfSheet.autoSizeColumn(i);  
				}  
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("4444444444:" + e.getMessage());
			}

		}

		return workbook;
	}

	/**
	 * 获取所有的业务员
	 * 
	 * @return
	 */
	public List<String> getManNameList() {
		List<String> list = new ArrayList<String>();

		try {
			list = prodDao.queryForList(BusinessConfig + ".queryManList", null, String.class);
		} catch (Exception e) {
			System.out.println("getManNameList-e:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取当前业务员卖出的物料（去重）
	 * 
	 * @return
	 */
	public List<String> getMaterielListBySales(Map<String, Object> map) {
		List<String> list = new ArrayList<String>();

		try {
			list = prodDao.queryForList(BusinessConfig + ".queryMaterieList", map, String.class);
		} catch (Exception e) {
			System.out.println("getMaterielNameList-e:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取单个业务员相同物料数据
	 * 
	 * @return
	 */
	public List<SalesDtlBean> getProdNameList(Map<String, Object> map) {
		List<SalesDtlBean> list = new ArrayList<SalesDtlBean>();

		try {
			list = prodDao.queryForList(BusinessConfig + ".queryProdList", map, SalesDtlBean.class);
		} catch (Exception e) {
			System.out.println("222222222:" + e.getMessage());
		}
		return list;
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

	public void delete() {
		try {
			prodDao.delete(BusinessConfig + ".deleteTotal", null);
		} catch (Exception e) {
			System.out.println("delete--e:" + e.getMessage());
		}

	}

	public void createDtlRow(HSSFWorkbook workbook, HSSFRow hssfRow, HSSFCellStyle hssfCellStyle1, SalesDtlBean sdb) {

		HSSFCell cello = hssfRow.createCell(0);

		cello.setCellStyle(hssfCellStyle1);
		cello.setCellValue(sdb.getCustomer());

		HSSFCell cell1 = hssfRow.createCell(1);
		cell1.setCellStyle(hssfCellStyle1);
		cell1.setCellValue(sdb.getSalesname());

		HSSFCell cello2 = hssfRow.createCell(2);

		cello2.setCellStyle(hssfCellStyle1);
		cello2.setCellValue(sdb.getMaterielName());

		HSSFCell cell3 = hssfRow.createCell(3);
		cell3.setCellStyle(hssfCellStyle1);
		cell3.setCellValue(sdb.getDateTime());

		HSSFCell cell4 = hssfRow.createCell(4);
		cell4.setCellStyle(hssfCellStyle1);
		cell4.setCellValue(Double.parseDouble(sdb.getSimplePrice()));

		HSSFCell cell5 = hssfRow.createCell(5);
		cell5.setCellStyle(hssfCellStyle1);
		cell5.setCellValue(Double.parseDouble(sdb.getTon()));

		HSSFCell cell6 = hssfRow.createCell(6);
		cell6.setCellStyle(hssfCellStyle1);

		if (sdb.getFreight() != null && !"".equals(sdb.getFreight())) {
			cell6.setCellValue(Double.parseDouble(sdb.getFreight()));
		}

		HSSFCell cell7 = hssfRow.createCell(7);
		cell7.setCellStyle(hssfCellStyle1);
		cell7.setCellValue(Double.parseDouble(sdb.getTotalFreight()));

		HSSFCell cell8 = hssfRow.createCell(8);
		cell8.setCellStyle(hssfCellStyle1);
		cell8.setCellValue(Double.parseDouble(sdb.getTotalPrice()));

		HSSFCell cell9 = hssfRow.createCell(9);
		cell9.setCellStyle(hssfCellStyle1);
		cell9.setCellValue("");

		HSSFCell cell10 = hssfRow.createCell(10);
		cell10.setCellStyle(hssfCellStyle1);
		cell10.setCellValue("明细");
	}

	public void createTotalRow(HSSFWorkbook workbook, HSSFRow hssfRow, HSSFCellStyle style,
			Map<String, Object> totalMap) {

		HSSFCell cello = hssfRow.createCell(0);
		cello.setCellStyle(style);

		HSSFCell cell1 = hssfRow.createCell(1);
		cell1.setCellStyle(style);

		HSSFCell cello2 = hssfRow.createCell(2);

		cello2.setCellStyle(style);
		cello2.setCellValue(totalMap.get("totalName").toString());

		HSSFCell cell3 = hssfRow.createCell(3);
		cell3.setCellStyle(style);

		HSSFCell cell4 = hssfRow.createCell(4);
		cell4.setCellStyle(style);

		HSSFCell cell5 = hssfRow.createCell(5);
		cell5.setCellStyle(style);

		cell5.setCellValue(numfmt(totalMap.get("tonTotal"), "#0.00000"));

		HSSFCell cell6 = hssfRow.createCell(6);
		cell6.setCellStyle(style);
		cell6.setCellValue(numfmt(totalMap.get("freTotal"), "#0.00"));

		HSSFCell cell7 = hssfRow.createCell(7);
		cell7.setCellStyle(style);
		cell7.setCellValue(numfmt(totalMap.get("freightTotal"), "#0.000"));

		HSSFCell cell8 = hssfRow.createCell(8);
		cell8.setCellStyle(style);
		cell8.setCellValue(numfmt(totalMap.get("priceTotal"), "#0.00"));

		HSSFCell cell9 = hssfRow.createCell(9);
		cell9.setCellStyle(style);
		cell9.setCellValue(numfmt(totalMap.get("avgPrice"), "#"));

		HSSFCell cell10 = hssfRow.createCell(10);
		cell10.setCellStyle(style);
		cell10.setCellValue("汇总");
	}

}
