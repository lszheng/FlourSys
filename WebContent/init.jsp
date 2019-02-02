<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.8.1.js"></script>
<script type="text/javascript">
	/* $(function(){
		$("#sub").click(function(){
			var $form1 = $('#improt');
	    	var params = $form1.serialize();
	    	alert(params);
			//$("#improt").submit();
		});
	}); */
</script>
</head>
<body>

	<form name="Form2" action="/out/pro/springUpload.do" method="post"
		enctype="multipart/form-data">
		<h1>上传销售明细表</h1>
		<h4>注意：需要将销售明细数据放在名称为“Sheet1” 的Sheet中</h4>

		<br />
		<div>
			<input type="file" name="file"><input type="submit"
				value="上传" /> ${retMsg1}
		</div>
	</form>
	
	<h4>
		<a href="/out/pro/export.do" data-descript="导出">导出业务员明细汇总表</a>
	</h4>

	<br />

	<h5>----------------------------------------------------------------------</h5>
	<form name="Form3" action="/out/salespfmc/uploadpfmtable.do" method="post"
		enctype="multipart/form-data">
		<h1>上传销售绩效表</h1>


		<div>
			<input type="file" name="file" size="300"><input
				type="submit" value="上传" /> ${retMsg}
		</div>
	</form>

	<h4>
		<a href="/out/salespfmc/export.do" data-descript="导出">导出销售绩效表</a>
	</h4>
</body>
</html>