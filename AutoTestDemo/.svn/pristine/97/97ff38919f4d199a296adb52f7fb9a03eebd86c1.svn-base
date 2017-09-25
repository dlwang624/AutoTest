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
					<div class="fl"><h3 class="page-title">公告一览</h3></div>
					<div class="fr"><input id="addworksynamic" type="button" value="添加公告" class="btn btn-default"></div>
					<div class="clearfix"></div>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading">
<!-- 										<h3 class="panel-title">用例</h3> -->
									</div>
								<!-- 文件夹配置列表main -->
								<div class="panel-body">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th class="w120">行号</th> 
												<th class="w300">公告名称</th> 
												<th class="w150">公告内容</th> 
												<th class="w150">发布时间</th> 
												<th class="w150">发布人</th>
												<th class="w120">详情</th> 
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list }" varStatus="status" var="worksynamic">
												<tr>
													<td>${status.index+1 }</td> 
													<td>${ worksynamic.title}</td> 
													<td class="w150 tc">
														<c:if test="${ worksynamic.temp == '' || null == worksynamic.temp}">
															无
														</c:if>
														<c:if test="${ worksynamic.temp != ''}">
															<div style="margin: 0 auto" class="ellipsis w180" title="${ worksynamic.temp}">${ worksynamic.temp}</div>
														</c:if>
													</td> 
													<td><fmt:formatDate value="${ worksynamic.time}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
													<td>${ worksynamic.cid}</td>
													<td>
														<span class="fa fa-pencil-square-o cursor" onclick="selSynamic(${worksynamic.id})"></span>
														
														<span class="fa fa-remove cursor ml10" onclick="delSynamic('${ worksynamic.title}',${worksynamic.id})"></span>
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
function selSynamic(id){
	window.location.href="maindata/showsynamic.htm?id="+id;
}
function delSynamic(title,id){
	layer.confirm("是否删除[<span class='red'>"+title+"</span>]公告", {
		  btn: ['确定', '取消'] //可以无限个按钮
		}, function(){
			window.location.href="maindata/removesync.htm?id="+id;
			layer.closeAll('dialog');
		}, function(){
			layer.closeAll('dialog');
		});
}
$(function(){
	$("#addworksynamic").click(function(){
		window.location.href="maindata/addwork.htm";
	});
});
</script>
</body>
</html>