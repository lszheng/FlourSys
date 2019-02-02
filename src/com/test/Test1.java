package com.test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test1 {
	
	public static void main(String[] args) {
		
		String avgprice = "5080.6600224";
		
		BigDecimal avgBg = new BigDecimal(avgprice).setScale(2, RoundingMode.UP);
		System.out.println(avgBg.intValue());
		System.out.println(avgBg.doubleValue());
		
		Calendar date = Calendar.getInstance();
		
		//Calendar c = new Calendar.getInstance();
		
		/*File file = new File("E:/springUpload");
		System.out.println("iiiii");*/
		
		
		/*String ss = System.getProperty("user.dir");
		
		System.out.println("lll:"+ss);
		
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		System.out.println(fileName);*/
		
		/*System.out.println("c%L5Z2pli#zF".getBytes());
		System.out.println("++++");*/
		
		
		//String b = "45.666";
		Double b = 45.6987 ,a = 34.987 ,c = 0.0;
		int t = 0;
		t = (int) (a+b);
		c = 5080.5000224;
		System.out.println(c);
		System.out.println(t);
		String str = "12";
		DecimalFormat df=new DecimalFormat("#000");
		
		System.out.println(Double.parseDouble(df.format(c)));
		System.out.println(df.format(Double.parseDouble(str)));
	}

}
