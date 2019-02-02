<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.8.1.js"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type: "post",
			url: "pro/find.do",
			success: function(data){
				alert(data);
			}
		});
	});
</script>
</head>
<body>
	<ul id="ul">
	</ul>
</body>
</html>