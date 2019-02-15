<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售业绩统计系统</title>
 <link rel="stylesheet" href="css/floursys.css">
<script type="text/javascript" src="js/jquery-1.8.1.js"></script>

</head>
<body>

	<form name="Form" action="/FlourSys/salesInit/uploadFile.do" method="post"
		enctype="multipart/form-data">
		<h1>销售初始数据维护>>></h1>


		<br />
		<div>
			<input type="file" name="file"><input type="submit"
				value="上传初始数据" /> <span style="font-size: 10px">注意：需要将销售初始数据放在名称为“初始数据源”
				的Sheet中</span> ${retMsg1}
		</div>
	</form>
	<table class="table1">
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
				<th></th>
				<th></th>
			</tr>
			<tr>
			<td>测试数据</td>
			</tr>
	</table>
</body>
</html>