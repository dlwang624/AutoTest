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
	width:350px;
}
</style>
</head>
<body class="${ layoutfull}">
	<!-- WRAPPER -->
	<div id="wrapper">
		<!--NAVBAR -->
		<%@include file="../nav/nav.jsp" %>
		<!-- LEFT SIDEBAR -->
		<%@include file="../nav/sidebar.jsp" %>
		
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<h3 class="page-title">上传工具</h3>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading tr">
										<input id="msg" type="text" value="${msg }" class="none">
									</div>
									<!-- 添加文件夹main -->
									<form id="qcdbForm" action="servertools/upload.htm" method="post" enctype="multipart/form-data">
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:50%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">工具名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input name="name" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">范围</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<select name="type" class="form-control input-sm">
														<option value="工具所属范围">工具所属范围</option>
														<option value="0">常用工具</option>
														<option value="1">性能测试</option>
														<option value="2">安全测试</option>
													</select>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">种类</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<select name="toolclass" class="form-control input-sm">
														<option value="工具所属种类">工具所属种类</option>
														<option value="0">文件</option>
														<option value="1">链接</option>
													</select>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">帮助文档</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input style="cursor: pointer;" type="file" name="helps" multiple="true" accept=".xls,.xlsx">
												</div>
											</div>
											<div class="clearfix"></div>
											<div id="checkClass"></div>
											<div class="mt10 tr">
												<button id="addTool" class="btn btn-default" style="width:120px;">上传</button>
											</div>
											<div class="clearfix"></div>
											
										</div>
									</div>
									</form>
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
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>
<script type="text/javascript" src="js/nav.js"></script>

<script type="text/javascript" src="js/addTools.js"></script>
</body>
</html>