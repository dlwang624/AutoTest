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
<link rel="stylesheet" href="js/themes/default/easyui.css"/>
<link rel="stylesheet" href="js/themes/icon.css"/>
<!-- MAIN CSS -->
<link rel="stylesheet" href="assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="assets/css/demo.css">
</head>
<body class="${ layoutfull}">
<div id="execwait">
	<div style='left:50%;background: transparent;height:150px;width:300px;position: relative;top: 50%;margin-left:-150px;margin-top:-75px;'>
		<div role='alert' class='alert alert-warning alert-dismissible' style='margin: 0;padding:0;height:100%;color:black;text-align: center;'>
			<p style='color:red;font-weight:bold;font-size:16px;'>正在执行用例</p>
			<p style='color:red;font-weight:bold;font-size:16px;'><span id="testname"></span></p>
			<div style='width:300px;height:100px;text-align: center;line-height:100px;'>
				<image src='image/loading.gif' style='border:0px;'/>
			</div>
		</div>
	</div>
</div>
<div id="divLocker"></div>
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
				<input id="userAddress" type="text" value="${userAddress}" class="none"/>
				<input id="localAddress" type="text" value="${localAddress}" class="none"/>
				<input id="rurl" type="text" class="none">
				<div class="container-fluid">
					<div class="clearfix">
						<div class="fl"><h3 class="page-title">批量执行用例</h3></div>
						<div class="fr"><button class="btn btn-default" id="back">返回</button></div>
					</div>
					<div class="row">
							<div class="col-md-12">
								<div class="panel" style="padding:20px 10px 30px 10px;">
									<!-- Copy文件夹main -->
									<div class="mainContentDiv">
										<div class="contentDiv">
											<div class="mt10 fl">
												<div class="fl w50 tr mr10 vt">用例表:</div>
												<div class="fl tl">
													<ul id="testtree"></ul>
												</div>
											</div>
											<div class="fr" style="width:50%;">
												<div class="mt10">
													<div class="fl w150 tr mr10 h30">
														<label class="fancy-checkbox">
															<input id="emailflag" type="checkbox">
															<span>群体邮件告知</span>
														</label>
													</div>
													<div class="fl w150 tc h30">
														<label class="fancy-checkbox">
															<input type="checkbox" id="browserflag">
															<span>是否启用浏览器</span>
														</label>
													</div>
													<div class="fl">
														<select id="switchAmbient" class="form-control input-sm">
															<c:if test="${ null == swlist}">
																<option value="-1">此项目还没有配置环境</option>
															</c:if>
															<c:if test="${ null != swlist}">
																<option value="-1">请选择执行环境</option>
															</c:if>
															<c:forEach items="${ swlist}" var="sw">
																<option value="${sw.id }">${sw.amdesc }</option>
															</c:forEach>
														</select>
													</div>
													<div class="fl ml20">
														<button id="startTest" class="btn btn-default">开始执行</button>
													</div>
													<div class="fl ml5">
														<button id="report" class="btn btn-default none">报告</button>
													</div>
												</div>
												<div class="clearfix"></div>
											</div>
										</div>
									</div>
								<!-- Copy文件夹main -->
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

<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/batchtest.js"></script>
<script type="text/javascript" src="js/batchsocket.js"></script>

</body>
</html>