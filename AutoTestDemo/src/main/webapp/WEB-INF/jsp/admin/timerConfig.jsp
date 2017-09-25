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
<style type="text/css">
.expectto,.expectcc{
	display: none;
	position: absolute;
}
.expectto ul, .expectcc ul{
	padding-left:0px;
	list-style: none;
	width: 300px;
}
.expectto ul li , .expectcc ul li{
	width: 200px;
	height: 25px;
	padding:1px 5px;
	background: #E2E2E2;
	color: black;
	font-weight: bold;
	font-size: 14px;
	line-height: 25px;
	cursor: pointer;
	white-space: nowrap;
	overflow:hidden;
	text-overflow: ellipsis;
	text-align: left;
}
.expectto ul li:hover , .expectcc ul li:hover{
	background: #ABABAB;
	color: white;
}
input[id="to"],input[id="cc"],input[name="tomails"],input[name="ccmails"],input[id="remark"]{
	width:200px;
    font-size:14px;
	line-height:25px;
    margin:0px; 
	padding:2px 8px 2px 6px; 
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
			<input id="userAddress" type="text" value="${userAddress}" class="none"/>
			<input id="localAddress" type="text" value="${localAddress}" class="none"/>
			<input id="msg" type="text" value="${msg }" class="none"/>
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<div class="clearfix">
						<div class="fl"><h3 class="page-title">定期执行用例</h3></div>
						<div class="fl" style="margin-left:20px;">
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
						<div class="fr"><input id="getall" type="button" value="查看所有" class="btn btn-default"></div>
						<div class="fr"><input type="button" id="addConfig" value="添加" class="btn btn-default"></div>
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
													<div class="fl w100 tr mr10 h30">备注:</div>
													<div class="fl">
														<input type="text" id="remark" class="form-control">
													</div>
												</div>
												<div class="mt10">
													<div class="fl w100 tr mr10 h30">执行时间:</div>
													<div class="fl">
														<select id="week" class="form-control input-sm">
															<option value="2">周一</option>
															<option value="3">周二</option>
															<option value="4">周三</option>
															<option value="5">周四</option>
															<option value="6" selected="selected">周五</option>
															<option value="7">周六</option>
															<option value="1">周日</option>
														</select>
													</div>
													<div class="fl">
														<select id="time" class="form-control input-sm">
															<option value="null">请选择时间</option>
															<c:forEach items="${ list}" var="weektime">
																<option value="${ weektime.time}" <c:if test="${ weektime.engross}">disabled="disabled"</c:if>>${ weektime.time}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="clearfix"></div>
												
												<div class="mt10">
													<div class="fl w100 tr mr10 h30">指定收件人:</div>
													<div class="fl">
														<input id="to" type="text" class="form-control">
														<div class="expectto">
															<ul id="expectto"></ul>
														</div>
														<div id="tomails"></div>
													</div>
												</div>
												<div class="mt10">
													<div class="fl w100 tr mr10 h30">指定抄送人:</div>
													<div class="fl">
														<input id="cc" type="text" class="form-control">
														<div class="expectcc">
															<ul id="expectcc"></ul>
														</div>
														<div id="ccmails"></div>
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
<script id="expectTemplate" type="text/x-jquery-tmpl">
	<li onclick="expectItemClick(this)">{{= mail}}</li>
</script>
<script id="weekChangeTemplate" type="text/x-jquery-tmpl">
{{each(i,weektime) list}}
	<option value="{{= weektime.time}}" {{if weektime.engross}}disabled="disabled"{{/if}}>{{= weektime.time}}</option>
{{/each}}
</script>
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>
<script type="text/javascript" src="js/nav.js"></script>

<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/timerConfig.js"></script>

</body>
</html>