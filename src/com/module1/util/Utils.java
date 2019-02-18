package com.module1.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	/**
     * 随即序列获取序列
     * */
    public static String getSerialData()
    {
		try {
			StringBuffer buffer = new StringBuffer();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			//获取当前时间
			String data = format.format(new Date());
			
			//产生一个6位数的随机数
	        int randomInt = (int)(Math.random()*900000)+100000;
			
			//流水构成： 当前时间+随机数
			return buffer.append(data).append(randomInt).toString();
		} catch (RuntimeException e) {
			//异常默认流水
			return "999000000000000123";
		}
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
    /**
     * String转Date类型
     * @param date
     * @param pattern
     * @return
     */
    public static Date formatDate(String date, String pattern){
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
    public static Double formatDouble(String number){
    	try {
			return Double.parseDouble(number);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
