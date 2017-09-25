<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/common.css"/>
<link rel="stylesheet" href="css/table.css"/>
<title>自动化测试用例执行</title>
</head>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<body>
<div class="main14">
	<div><input type="button" value="返回" class="backBtn" id="back"></div>
	<div class="content">
		<form action="CMS/addTest.htm" method="post" id="addtest"> 
		<input type="text" name="value" value="0" class="none"/>
		<table class="formdata" id="steptable"> 
			<caption>添加测试用例</caption> 
			<tr>
				<th scope="col" colspan="3">用例名:</th> 
				<td colspan="3"><input type="text" name="testname" style="width:560px" onfocus="checkFontColor(this)"/></td> 
				<td colspan="2">
					<input type="button" value="删除空步骤" style="width: 100px;height: 30px" id="delNullStep"/>
					<input type="button" value="提交用例" style="width: 80px;height: 30px" id="testsubmit"/>
					<select id="stepcount" style="width: 40px;height: 30px;margin-left: 10px;">
						<option value="1">1</option>
						<option value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="50">50</option>
					</select>
					<input type="button" value="添加步骤" id="addstep" style="width: 80px;height: 30px;"/>
				</td>
			</tr>
			<tr> 
			<th scope="col">行号</th> 
			<th scope="col">定位符类型</th> 
			<th scope="col">定位符</th> 
			<th scope="col">定位符描述</th> 
			<th scope="col">预期定位符描述</th> 
			<th scope="col">预期定位符类型</th> 
			<th scope="col">预期定位符</th>
			<th scope="col">操作</th>
			</tr> 
			<tr id="row1"> 
			<th scope="row" class="outbg">1</th>   
			    <td>
					<select name="descList[0].type" class="sync">
						<option value="xpath" selected="selected">xpath</option>
						<option value="id">id</option>
						<option value="linktext">linktext</option>
						<option value="name">name</option>
						<option value="partialLinkText">partialLinkText</option>
						<option value="classname">classname</option>
						<option value="css">css</option>
					</select>
				</td> 
			    <td><input type="text" name="descList[0].name" onfocus="checkFontColor(this)" class="sync"/></td> 
			    <td><input type="text" name="descList[0].description" style="width:200px" onfocus="checkFontColor(this)" class="sync"/></td> 
			    <td><input type="text" name="descList[0].resultdescription" style="width:200px" onfocus="checkFontColor(this)" class="sync"/></td> 
				<td>
					<select name="descList[0].resulttype" class="sync">
						<option value="" selected="selected"></option>
						<option value="xpath">xpath</option>
						<option value="id">id</option>
						<option value="linktext">linktext</option>
						<option value="name">name</option>
						<option value="partialLinkText">partialLinkText</option>
						<option value="classname">classname</option>
						<option value="css">css</option>
					</select>
				</td> 
			    <td><input type="text" name="descList[0].resultname" onfocus="checkFontColor(this)" class="sync"/></td> 
			    <td>
			    	<input name="delstep" type="button" style="width:80px;height:30px" value="删除步骤"/>
			    	<input type="button" style="width:80px;height:30px" value="插入步骤" onclick="insertStep(this)"/>
			    </td> 
			</tr> 
		</table>
		</form>
	</div>
</div>
<script id="myTemplate" type="text/x-jquery-tmpl">
<tr id="row{{= num + 1}}"> 
<th scope="row" class="outbg">{{= num + 1}}</th>
    <td>
		<select name="descList[{{= num}}].type" class="sync">
			<option value="xpath" selected="selected">xpath</option>
			<option value="id">id</option>
			<option value="linktext">linktext</option>
			<option value="name">name</option>
			<option value="partialLinkText">partialLinkText</option>
			<option value="classname">classname</option>
			<option value="css">css</option>
		</select>
	</td> 
    <td><input type="text" name="descList[{{= num}}].name" onfocus="checkFontColor(this)" class="sync"/></td> 
    <td><input type="text" name="descList[{{= num}}].description" style="width:200px" onfocus="checkFontColor(this)" class="sync"/></td> 
    <td><input type="text" name="descList[{{= num}}].resultdescription" style="width:200px" onfocus="checkFontColor(this)" class="sync"/></td> 
	<td>
		<select name="descList[{{= num}}].resulttype" class="sync">
			<option value="" selected="selected"></option>
			<option value="xpath">xpath</option>
			<option value="id">id</option>
			<option value="linktext">linktext</option>
			<option value="name">name</option>
			<option value="partialLinkText">partialLinkText</option>
			<option value="classname">classname</option>
			<option value="css">css</option>
		</select>
	</td> 
    <td><input type="text" name="descList[{{= num}}].resultname" onfocus="checkFontColor(this)" class="sync"/></td> 
	<td>
		<input type="button" style="width:80px;height:30px" value="删除步骤" onclick="delNull(this)">
		<input type="button" style="width:80px;height:30px" value="插入步骤" onclick="insertStep(this)"/>
	</td> 
</tr>
</script>
<script type="text/javascript" src="js/testPage.js"></script>
</body>
</html>