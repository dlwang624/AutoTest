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
					<div class="fl"><h3 class="page-title">添加培训</h3></div>
					<div class="fr"><c:if test="${ backflag}"><div><input type="button" value="返回" class="btn btn-default" id="back"></div></c:if></div>
					<div class="clearfix"></div>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading tr">
										<input id="msg" type="text" value="${msg }" class="none">
										
									</div>
									<!-- 添加文件夹main -->
									<form id="trainForm" action="maindata/savetrain.htm" method="post" enctype="multipart/form-data">
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:50%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">培训课题</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input name="title" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">培训讲师</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input name="author" type="text" class="form-control" >
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">培训课件</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input style="cursor: pointer;" type="file" name="pptfiles" multiple="true" accept=".pptx,.ppt">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">培训作业</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input style="cursor: pointer;" type="file" name="files" multiple="true" accept=".docx,.doc,.xls,.xlsx">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10 tr">
												<button id="addtrain" class="btn btn-default" style="width:120px;">添加</button>
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

<script type="text/javascript" src="js/addTrain.js"></script>
</body>
</html>