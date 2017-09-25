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
.table thead tr th{
	text-align: center;
	padding:10px 20px;
}
.table tbody tr td{
	text-align: center;
	padding:10px 20px;
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
					<h3 class="page-title">环境配置一览</h3>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading">
<!-- 										<h3 class="panel-title">环境配置</h3> -->
										<input id="msg" type="text" value="${msg }" class="none"/>
										<div class="fr">
											<input id="addAmbient" type="button" value="添加环境" class="btn btn-default"/>
										</div>
										<div class="clearfix"></div>
									</div>
								<!-- 文件夹配置列表main -->
								<div class="panel-body">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th>行号</th> 
												<th>描述信息</th> 
												<th>环境URL</th>
												<th>操作</th> 
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${ amlist}" var="ambient" varStatus="status">
												<tr>
													<td>${status.index+1 }</td>
													<td>${ ambient.amdesc}</td>
													<td>${ ambient.amurl}</td>
													<td style="text-align: left;padding-left: 100px;">
														<input class="btn btn-default" type="button" value="查看" onclick="selAm(${ambient.id})">
														<input class="btn btn-default" type="button" value="删除" style="width:75px;" onclick="delAm(${ambient.id})">
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- 文件夹配置列表main -->
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

<script type="text/javascript">
function selAm(id){
	window.location.href="switchambient/getam.htm?id="+id;
}
function delAm(id){
	window.location.href="switchambient/remove.htm?id="+id;
}
$(function(){
	var msg = $("#msg").val();
	if(""!=msg&&undefined!=msg){
		if(msg=="true"){
			 layer.alert("环境删除成功",{icon:1});
		}else{
			 layer.alert("环境删除失败",{icon:2});
		}
	}
	$("#addAmbient").click(function(){
		window.location.href="switchambient/addAmbient.htm";
	});
});A
</script>
</body>
</html>