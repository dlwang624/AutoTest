<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>自动化测试平台</title>
<base href="${basePath}"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" CONTENT="0"> 
<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="Pragma" CONTENT="no-cache"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<!-- VENDOR CSS -->
<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
<link rel="stylesheet" href="assets/vendor/chartist/css/chartist-custom.css">
<!-- MAIN CSS -->
<link rel="stylesheet" href="assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="assets/css/demo.css">
<style type="text/css">
.form-control{
	width:250px;
}
</style>
</head>
<body class="${ layoutfull}">
<div id="execwait">
	<div style='left:50%;background: transparent;height:150px;width:300px;position: relative;top: 50%;margin-left:-150px;margin-top:-75px;'>
		<div role='alert' class='alert alert-warning alert-dismissible' style='margin: 0;padding:0;height:100%;color:black;text-align: center;'>
			<p style='color:red;font-weight:bold;font-size:16px;'>测试总结报告当前统计到</p>
			<p style='color:red;font-weight:bold;font-size:16px;'><span id="testname">请等待...</span></p>
			<div style='width:300px;height:100px;text-align: center;line-height:100px;'>
				<image src='image/loading.gif' style='border:0px;'/>
			</div>
		</div>
	</div>
</div>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!--NAVBAR -->
		<%@include file="../nav/nav.jsp" %>
		<!-- LEFT SIDEBAR -->
		<%@include file="../nav/sidebar.jsp" %>
		
		<!-- MAIN -->
		<div class="main">
			<input id="userAddress" type="text" value="${userAddress}" class="none"/>
			<input id="localAddress" type="text" value="${localAddress}" class="none"/>
			<input id="msg" type="text" value="${msg }" class="none"/>
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<div class="fl"><h3 class="page-title">生成测试总结报告</h3></div>
					<div class="fr">
						<button id="correctTestStatus" class="btn btn-default w120">批改</button>&nbsp;&nbsp;
						<button id="sumReport" class="btn btn-default w120">生成</button>&nbsp;&nbsp;
						<button id="canvasReport" class="btn btn-default w150">Bug收敛趋势图</button></div>
					<div class="clearfix"></div>
					<div class="row">
							<div class="col-md-12">
								<div id="sumReportPanel" class="panel" style="padding-bottom:50px;">
									<div class="panel-heading">
<!-- 										<h3 class="panel-title">用例</h3> -->
									</div>
									<!-- 添加文件夹main -->
									<form action="count/downreport.htm" method="post" id="trsum">
										<input type="text" name="filename" id="filename" class="none">
									</form>
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:45%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">QC库名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<select id="qid" name="qid" class="form-control">
														<option value="-1" selected="selected">请选择QC库名</option>
														<c:forEach items="${list }" varStatus="status" var="qcdb">
																<option value="${qcdb.id }">
																		${ qcdb.dbname}
																</option>
															</c:forEach>
													</select>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">文件夹名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input id="folderID" name="folderID" type="text" class="none">
													<input id="foldername" name="foldername" type="text" class="form-control" placeholder="请先选择QC库名">
													<div class="expect">
														<ul id="expect"></ul>
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">需求ID</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input id="redmineID" type="text" class="form-control" placeholder="请输入需求ID">
												</div>
											</div>
											<div class="clearfix"></div>
										</div>
										<div style="margin-top:30px;" id="bugview"></div>
									</div>
								<!-- 添加文件夹main -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
	</div>
<script id="expectTemplate" type="text/x-jquery-tmpl">
	<li style="width:250px;" onclick="expectItemClick(this)" value="{{= projectid}}">{{= name}}-{{= projectid}}</li>
</script>
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>
<script type="text/javascript" src="js/nav.js"></script>
<script type="text/javascript" src="js/echarts.js"></script>

<script type="text/javascript" src="js/sumreport.js"></script>
<script type="text/javascript" src="js/batchsocket.js"></script>
</body>
</html>