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
.loadingParent{ width:150px; margin:0 auto;}
.loadingParent canvas{ display:block; margin:0 auto;}
.table thead tr th{
	padding: 5px 0px 5px 0px !important;
	text-align: center;
	line-height: 30px;
}
.table tbody tr td{
	padding: 5px 0px 5px 0px !important;
	text-align: center;
	line-height: 150px;
	overflow:hidden;
	white-space:nowrap;
	text-overflow: ellipsis;
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
			<div class="main-content clearfix" style="padding-bottom:0px;">
				<div class="container-fluid">
					<div class="fl"><h3 class="page-title">${name }&nbsp;用户统计</h3></div>
					<div class="fr"><input type="button" value="返回" class="btn btn-default" id="back"></div>	
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading tr">
										
									</div>
								<!-- 用例统计main -->
								<div class="panel-body">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th class="w50">行号</th> 
												<th>用例名</th> 
												<th style="width: 200px;">创建时间</th> 
												<th style="width: 75px;">执行次数</th>
												<th style="width: 75px;">总步骤数</th>
												<th style="width: 150px;padding:0;">Fail比例</th> 
											</tr>
										</thead>
										<tbody>
											<c:if test="${null == list}">
												<tr>
													<td scope="col" colspan="6"><h3>此用户没有创建用例</h3></td> 
												</tr>
											</c:if>
											<c:forEach items="${list }" varStatus="status" var="desc">
												<tr>
													<td>${status.index+1 }</td> 
													<td>${ desc.testName}</td> 
													<td>${ desc.date}</td> 
													<td style="font-size:18px;">${ desc.execCount}</td> 
													<td style="font-size:18px;">${ desc.stepCount}</td> 
													<td>
														<div class="loadingParent">
															<input type="text" id="${ desc.id}" value="${desc.percent}" class="none">
															<canvas id="canvasIndex${ desc.id}" width="150" height="150" amount="10000" vote="10000" tip="50">${desc.percent}%</canvas>
														</div>
													</td> 
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- 用例统计main -->
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

<script type="text/javascript" src="js/canvas/cycle.js"></script>
<script type="text/javascript" src="js/canvas/hidpi-canvas.min.js"></script>
<script type="text/javascript" src="js/canvas/modernizr-2.6.2.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#back").click(function(){
		window.location.href="count/main.htm";
	});
	$(".loadingParent").each(function(){
		var per = $(this).children(".none").val();
		var id = $(this).children(".none").attr("id");
		var width=$(".loadingParent").width();
		var option={
			   percent:per,   //百分比数值
			   w:width,          //宽度
			   oneCircle:"ture"  //是否是整个圆  默认半圆
			 }
		 $("#canvasIndex"+id).audios2(option);
	});
});
</script>
</body>
</html>