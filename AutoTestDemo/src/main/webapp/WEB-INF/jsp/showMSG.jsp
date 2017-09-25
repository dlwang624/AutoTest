<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/common.css"/>
<title>自动化测试用例执行</title>
</head>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<body>
<div class="main8">
	<div><p>${msg }</p></div>
	<div><input type="button" value="返回" class="backBtn" id="back"></div>
</div>
<script type="text/javascript">
$(function(){
	$("#back").click(function(){
		window.location.href="CMS/allTest.htm";
	});
});
</script>
</body>
</html>