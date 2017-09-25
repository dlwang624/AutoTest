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
			<input id="userAddress" type="text" value="${userAddress}" class="none"/>
			<input id="localAddress" type="text" value="${localAddress}" class="none"/>
			<input id="msg" type="text" value="${msg }" class="none"/>
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<h3 class="page-title">上传下载用例数据</h3>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading">
<!-- 										<h3 class="panel-title">用例</h3> -->
									</div>
									<!-- 上传下载main -->
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:45%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">文件夹名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<select id="projectid" class="form-control input-sm">
														<option value="请选择文件夹名">请选择文件夹名</option>
														<c:forEach items="${prolist}" varStatus="status" var="pro">
															<option value="${pro.id }">
																${pro.name}
															</option>
														</c:forEach>
													</select>
												</div>
												<div id="fileshowmsg" class="fl none"><p style="color:red;">请选择文件夹名!</p></div>
											</div>
											<div class="clearfix"></div>
											<div class="fl mt10">
												<div class="fl w150 tr mr10 h30">用例名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl relative">
													<input class="form-control input-sm" type="text" id="testName"/>
													<div class="expect">
														<ul id="expect" style="z-index:999"></ul>
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<form action="file/filesUploadftp.htm" method="post" enctype="multipart/form-data" id="filedata">
													<div class="fl">
														<div class="fl w150 tr mr10 h30">选择文件</div>
														<div class="fl mr10 h30">:</div>
														<div class="fl">
															<input id="testID" name="testID" class="none">
															<div>
																<a href="javascript:;" class="file">
																	<input style="cursor: pointer;" type="file" name="files" id="filename" multiple="true" accept=".xls,.xlsx">
																	上传
																</a>
															</div>
														</div>
													</div>
												</form>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">用例已有数据文件</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl tc" id="excelDownload">
													<h3>请选择用例</h3>
												</div>
												<div class="clearfix"></div>
											</div>
											<div class="clearfix"></div>
										</div>
									</div>
								<!-- 上传下载main -->
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
	<li onclick="expectItemClick(this)" value="{{= id}}">{{= testname}}</li>
</script>
<script id="excelTemplate" type="text/x-jquery-tmpl">
<div class="clearfix" name="delete">
	<div class="fl w300 h30 tl">{{= filename}}</div>
	<div class="fl">
		<span title="下载" class="fa fa-download cursor" onclick="post('file/getFile.htm', {fileName :'{{= filename}}',id:{{= id}}})"></span>
		<span title="删除" class="fa fa-remove ml10 cursor" onclick="exceldel('{{= filename}}',this)"></span>
	</div>
</div>
</script>
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>

<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="js/testUpload.js"></script>
<script type="text/javascript" src="js/nav.js"></script>
</body>
</html>