<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery-1.8.1.js"></script>
<script type="text/javascript">
	$(function(){
		alert("上传成功！！");
		$("#sub").click(function(){
			alert("开始下载");
			$.ajax({
				type: "post",
				url: "/out/pro/export.do",
				
				success: function(data){
					alert(data);
					alert("下载成功!!!");
				}
			});
		});
	});
</script>
</head>
<body>
<h1>下载业务员统计文件</h1>
<input id="sub" type="submit" value="download"/>

<a href="/out/pro/export.do" data-descript="导出">导出业务员明细汇总表</a>  

</body>
</html>