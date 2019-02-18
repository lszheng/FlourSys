package com.module1.bean;

import java.util.Date;

/**
 * 销售数据初始化
 * 
 * @author Administrator
 *
 */
public class SalesInitBean {

	private String cust_no;// 客户编号
	private String cust_name;// 客户名称
	private String sales_name;// 业务员
	private String materiel_no;// 物料编号
	private String meteriel_name;// 物料名称
	private String meteriel_unit;// 单位
	private Date sale_date;// 日期
	private double sale_simp_price;// 单价
	private double sales_volums;// 数量 (总和)
	private double give_volums;// 赠送数量 (总和)
	private double back_volums;// 退货数量 (总和)
	private double back_give_volums;// 退货赠送数量 (总和)
	private double total_amt;// 开单金额 (总和)
	private String remark1;// 预留1
	private String remark2;// 预留2
	private String remark3;// 预留3
	private String remark4;// 预留4

	public String getCust_no() {
		return cust_no;
	}

	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getSales_name() {
		return sales_name;
	}

	public void setSales_name(String sales_name) {
		this.sales_name = sales_name;
	}

	public String getMateriel_no() {
		return materiel_no;
	}

	public void setMateriel_no(String materiel_no) {
		this.materiel_no = materiel_no;
	}

	public String getMeteriel_name() {
		return meteriel_name;
	}

	public void setMeteriel_name(String meteriel_name) {
		this.meteriel_name = meteriel_name;
	}

	public String getMeteriel_unit() {
		return meteriel_unit;
	}

	public void setMeteriel_unit(String meteriel_unit) {
		this.meteriel_unit = meteriel_unit;
	}

	public Date getSale_date() {
		return sale_date;
	}

	public void setSale_date(Date sale_date) {
		this.sale_date = sale_date;
	}

	public double getSale_simp_price() {
		return sale_simp_price;
	}

	public void setSale_simp_price(double sale_simp_price) {
		this.sale_simp_price = sale_simp_price;
	}

	public double getSales_volums() {
		return sales_volums;
	}

	public void setSales_volums(double sales_volums) {
		this.sales_volums = sales_volums;
	}

	public double getGive_volums() {
		return give_volums;
	}

	public void setGive_volums(double give_volums) {
		this.give_volums = give_volums;
	}

	public double getBack_volums() {
		return back_volums;
	}

	public void setBack_volums(double back_volums) {
		this.back_volums = back_volums;
	}

	public double getBack_give_volums() {
		return back_give_volums;
	}

	public void setBack_give_volums(double back_give_volums) {
		this.back_give_volums = back_give_volums;
	}

	public double getTotal_amt() {
		return total_amt;
	}

	public void setTotal_amt(double total_amt) {
		this.total_amt = total_amt;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	

}
