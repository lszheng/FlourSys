package com.module1.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.module1.bean.SalesInitBean;
import com.module1.dao.impl.BaseDaoImpl;
import com.module1.service.SalesInitService;
import com.module1.util.Utils;
import com.module1.util.XLSXCovertCSVReader;
@Service
public class SalesInitServiceImpl implements SalesInitService {

	@Autowired//自动装配
	private BaseDaoImpl dao;

	@Override
	public boolean uploadFile(File file) {

		List<SalesInitBean> sibList = new ArrayList<SalesInitBean>();

		// 删除表中的明细数据
		dao.delete("SalesInitEntity.delete", null);
		try {
			// 读取文件
			List<String[]> list = XLSXCovertCSVReader.readerExcel(file.getPath(), "初始数据源", 17);
			int i=0;
			for (String[] record : list) {
				//第一行数据是表头，不要
				if(i==0){
					i++;
					continue;
				}
				i++;
				System.out.println(i);
				System.out.println(record.toString());
				
				SalesInitBean sib = new SalesInitBean();
				sib.setCust_no(record[0]);
				sib.setCust_name(record[1]);
				sib.setSales_name(record[2]);
				sib.setMateriel_no(record[3]);
				sib.setMeteriel_name(record[4]);
				sib.setMeteriel_unit(record[5]);
				
				sib.setSale_date(record[6]);
				/*
				sib.setSale_simp_price(Utils.formatDouble(record[7]));
				sib.setSales_volums(Utils.formatDouble(record[8]));
				sib.setBack_volums(Utils.formatDouble(record[9]));
				sib.setBack_give_volums(Utils.formatDouble(record[10]));
				sib.setTotal_amt(Utils.formatDouble(record[11]));
				*/
				sib.setSale_simp_price(record[7]);
				sib.setSales_volums(record[8]);
				sib.setBack_volums(record[9]);
				sib.setBack_give_volums(record[10]);
				sib.setTotal_amt(record[11]);
				sibList.add(sib);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SalesInitList", sibList);
			int count1 = dao.insert("SalesInitEntity.insert", map);

			System.out.println("入库条数：" + count1);
		} catch (Exception e) {
			System.out.println("异常！！！:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public List queryInitData(Map param) {
		// TODO Auto-generated method stub
		
		return dao.queryForList("SalesInitEntity.querySalesInitList", param);
	}

}
