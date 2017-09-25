<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
					<div class="fl"><h3 class="page-title">培训一览</h3></div>
					<div class="fr"><input id="addTrain" type="button" value="添加培训" class="btn btn-default"></div>
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
												<th class="w300">培训课题</th> 
												<th class="w150">培训讲师</th> 
												<th class="w150">发布时间</th> 
												<th class="w150">课件</th>
												<th class="w150">作业</th>
												<th class="w120">详情</th> 
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list }" varStatus="status" var="train">
												<tr>
													<td>${status.index+1 }</td> 
													<td>${ train.title}</td> 
													<td class="w150 tc">${train.author }</td> 
													<td><fmt:formatDate value="${ train.time}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
													<td style="<c:if test="${train.fileurl != null && train.fileurl != '' }">text-align:left;</c:if>">
														<c:if test="${ train.fileurl == null || train.fileurl == '' }">
															无
														</c:if>
														<c:if test="${train.fileurl != null && train.fileurl != '' }">
															<c:forEach var="word" items="${fn:split(train.fileurl,',')}">
													            <a class="fa fa-file cursor ellipsis clearastyle" target="_Top" style="width:130px;" href="javascript:window.location.href='${httpBase }${train.id}/word/${fn:trim(word)}'" title="${fn:trim(word)}">${fn:trim(word)}</a>
													    	</c:forEach>
														</c:if>
													</td>
													<td style="<c:if test="${train.temp != null && train.temp != '' }">text-align:left;</c:if>">
														<c:if test="${ train.temp == null || train.temp == ''}">
															无
														</c:if>
														<c:if test="${ train.temp != null && train.temp != ''}">
															<c:forEach var="ppt" items="${fn:split(train.temp,',')}">
													            <a class="fa fa-file cursor ellipsis clearastyle" target="_Top" style="width:130px;" href="javascript:window.location.href='${httpBase }${train.id}/ppt/${fn:trim(ppt)}'" title="${fn:trim(ppt)}">${fn:trim(ppt)}</a>
													    	</c:forEach>
														</c:if>
													</td>
													<td>
														<span class="fa fa-pencil-square-o cursor" onclick="selTrain(${train.id})"></span>
														<span class="fa fa-remove cursor ml10" onclick="delTrain('${ train.title}',${train.id})"></span>
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

<script type="text/javascript" src="js/trainlist.js"></script>
</body>
</html>