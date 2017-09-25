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
		<%@include file="nav/nav.jsp" %>
		<!-- LEFT SIDEBAR -->
		<%@include file="nav/sidebar.jsp" %>
		
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<h3 class="page-title">QC文件夹配置一览</h3>
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
												<th>行号</th> 
												<th>数据库名</th> 
												<th>ip+端口</th> 
												<th>用户名</th>
												<th>密码</th> 
												<th>操作</th> 
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list }" varStatus="status" var="qcdb">
												<tr>
													<td>${status.index+1 }</td> 
													<td>${ qcdb.dbname}</td> 
													<td>${ qcdb.ip}</td> 
													<td>${ qcdb.username}</td>
													<td>${ qcdb.password}</td> 
													<td><input type="button" value="查看" onclick="selQcdb(${qcdb.id})" class="btn btn-default"></td> 
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
function selQcdb(id){
	window.location.href="project/selQcdb.htm?id="+id;
}
</script>
</body>
</html>