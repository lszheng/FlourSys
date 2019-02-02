package com.module1.bean;

/**
 * 销售明细实体
 * 
 * @author Administrator
 *
 */
public class SalesDtlBean {

	private String id;

	private String customer;// 客户名称
	private String salesname;// 业务员
	private String materielName;// 物料名称
	private String dateTime;// 日期
	private String simplePrice;// 单价
	private String ton;// 吨
	private String freight;// 运费
	private String totalFreight;// 总运费
	private String totalPrice;// 开单金额
	private String averagePrice;// 均价

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSalesname() {
		return salesname;
	}

	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getSimplePrice() {
		return simplePrice;
	}

	public void setSimplePrice(String simplePrice) {
		this.simplePrice = simplePrice;
	}

	public String getTon() {
		return ton;
	}

	public void setTon(String ton) {
		this.ton = ton;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(String totalFreight) {
		this.totalFreight = totalFreight;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}

	@Override
	public String toString() {
		return "ProdBean [id=" + id + ", customer=" + customer + ", salesname=" + salesname + ", materielName="
				+ materielName + ", dateTime=" + dateTime + ", simplePrice=" + simplePrice + ", ton=" + ton
				+ ", freight=" + freight + ", totalFreight=" + totalFreight + ", totalPrice=" + totalPrice
				+ ", averagePrice=" + averagePrice + "]";
	}

}
