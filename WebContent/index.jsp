<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售业绩统计系统</title>
<link rel="stylesheet" href="./css/floursys.css">
<script type="text/javascript" src="js/jquery-1.8.1.js"></script>

</head>
<body>

	<form name="Form" action="/FlourSys/salesInit/uploadFile.do"
		method="post" enctype="multipart/form-data">
		<h1>销售初始数据维护>>></h1>


		<br />
		<div>
			<input type="file" name="file"><input type="submit"
				value="上传初始数据" /> <span style="font-size: 10px">注意：需要将销售初始数据放在名称为“初始数据源”
				的Sheet中</span>
			<div class="divcss5" style="color: #ff8040">${retMsg}</div>
		</div>
	</form>
	<form action=""></form>
	<br>
	<a onclick="">1、 客户名称like%金永利% and业务员=XX部门 将XX部门更改为余圣彬 </a>
	<br>
	
	<form name="Form1" action="/FlourSys/salesInit/query.do" method="post">
		<table cellspacing="0" width="100%">
			<tr>
				<td align="right">业务员</td>
				<td><input type="text" id="sales_name" name="sales_name" /></td>
			</tr>

			<tr>
				<td align="right"><input type="submit" value="查询"></td>
			</tr>
		</table>
	</form>
	<br />
	<table class="table1" border="1" cellspacing="0" width="100%">
		<tr>
			<th>客户号</th>
			<th>客户名称</th>
			<th>业务员</th>
			<th>物料编码</th>
			<th>物料名称</th>
			<th>单位</th>
			<th>日期</th>
			<th>单价</th>
			<th>数量 (总和)</th>
			<th>赠送数量 (总和)</th>
			<th>退货数量 (总和)</th>
			<th>退货赠送数量 (总和)</th>
			<th>开单金额 (总和)</th>

		</tr>

		<c:forEach items="${retList}" var="Init">
			<tr>
				<td>${Init.cust_no }</td>
				<td>${Init.cust_name }</td>
				<td>${Init.sales_name }</td>
				<td>${Init.materiel_no }</td>
				<td>${Init.meteriel_name }</td>
				<td>${Init.meteriel_unit }</td>
				<td>${Init.sale_date }</td>
				<td>${Init.sale_simp_price }</td>
				<td>${Init.sales_volums }</td>
				<td>${Init.give_volums }</td>
				<td>${Init.back_volums }</td>
				<td>${Init.back_give_volums }</td>
				<td>${Init.total_amt }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>