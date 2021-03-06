<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/common.css"/>
<link rel="stylesheet" href="css/ExecTest.css"/>
<link rel="stylesheet" href="css/table.css"/>
<title>自动化测试用例执行</title>
<style type="text/css">
select{
    font-size:14px;
	line-height:25px;
	padding:5px 0px; 
}
#iframeName{
	width: 300px;
    font-size: 12px;
    line-height: 25px;
    margin: 0px 0px 3px 0px;
}
#execNum{
	width: 30px;
    font-size: 12px;
    line-height: 25px;
    margin: 0px 0px 3px 0px;
    text-align: center;
}
</style>
</head>
<body>
<div id="divLocker"></div>
<div class="clearfix">
<div class="fl">
	<div class="main8">
		<p>客户的ID: ${uid}</p>
		<p>客户的IP: ${userAddress}</p>
		<p>服务器IP: ${localAddress}</p>
		<div class="fr">
			<input type="button" value="批量执行" class="batchBtn" id="batch" style="padding: 2px 8px;">
			<input type="button" value="返回" class="backBtn" id="back">
		</div>
		<input id="userAddress" type="text" value="${userAddress}" class="none"/>
		<input id="localAddress" type="text" value="${localAddress}" class="none"/>
		<div class="clearfix">
				<div class="fl w100 tr mr5 h30">用例执行次数:</div>
				<div class="fl">
					<input type="text" id="execNum" value="1"/>
				</div>
			</div>
		<div class="clearfix">
			<div class="clearfix">
				<div class="fl w100 tr mr5 h30">文件夹名:</div>
				<div class="fl">
					<select id="projectid" style="font-size:13px;padding:2px;">
						<option value="请选择文件夹名" selected="selected">请选择文件夹名</option>
						<c:forEach items="${prolist}" varStatus="status" var="pro">
							<option value="${pro.id }">
							${ pro.name}
							</option>
						</c:forEach>
					</select>
				</div>
				<div id="fileshowmsg" class="fl none"><p style="color:red;">请选择文件夹名!</p></div>
			</div>
<!-- 			<div class="clearfix"> -->
<!-- 				<div class="fl w100 tr mr5 h30">iframeName:</div> -->
<!-- 				<div class="fl"> -->
<!-- 					<input type="text" id="iframeName"/> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="fl clearfix">
				<div class="fl w100 tr mr5 h30">实施用例名:</div>
				<div class="fl relative">
					<input type="text" id="testName"/>
					<div class="expect">
						<ul id="expect"></ul>
					</div>
				</div>
			</div>
			<div class="clearfix">
				<div class="fl w100 tr mr5 h30">数据文件名:</div>
				<div class="fl">
					<select name="dataFilename" id="dataFilename" style="font-size:13px;padding:2px;">
						<option value="null">可选-先选择用例名</option>
					</select>
				</div>
				<div class="fl">
					<div class="fl">
						是否启用浏览器:<input type="checkbox" id="browserflag" checked="checked"><input type="button" id="startTest" value="开始执行">
					</div>
				</div>
				<input id="testID" class="none">
				<div class="fl" id="reportLink" style="display: none;">
					<input type="button" onclick="showReport()" value="报告">
				</div>
			</div>
			<div class="clearfix">
				<div class="fl w100 tr mr5">实施日志:</div>
				<div class="textarea fl" id="testlog" contenteditable="false"></div>
			</div>
		</div>
	</div>
</div>
<div class="fl w500 ml50">
	<div><h1>用例数据显示区域</h1></div>
	<div id="showtestdata"><p class="f16 bold">请选择用例数据文件</p></div>
</div>
</div>
<script id="excelTemplate" type="text/x-jquery-tmpl">
{{each(i,table) sheet}}
	<table class="formdata" id="steptable">
		<caption>第{{= i+1}}套用例数据</caption> 
		<tr>
			<th scope="col">步骤</th> 
			<th scope="col">描述变量值</th> 
			<th scope="col">预期描述变量值</th>
		</tr>
		{{each(j,row) table}}
			<tr>
				<td scope="col">{{= row.step}}</td> 
				<td scope="col">{{= row.desc}}</td> 
				<td scope="col">{{= row.resultDesc}}</td> 
			</tr>
		{{/each}}
	</table>
{{/each}}
</script>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/socket.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script id="expectTemplate" type="text/x-jquery-tmpl">
	<li onclick="expectItemClick(this)" value="{{= id}}">{{= testname}}</li>
</script>
<script type="text/javascript" src="js/exectest.js"></script>
</body>
</body>
</html>