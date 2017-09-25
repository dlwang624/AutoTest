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
					<h3 class="page-title">${tabtitle }</h3>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading">
										<input type="text" id="msg" value="${msg }" class="none">
										<c:if test="${toolsList == null || toolsList[0] == null}">
											<h3 class="panel-title">还未添加任何工具</h3>
										</c:if>
									</div>
								<!-- 文件夹配置列表main -->
								<div class="panel-body">
									<c:if test="${toolsList != null && toolsList[0] != null}">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th style="width:30px;padding:5px;">行号</th> 
												<th>工具名</th> 
												<th style="width:110px;">工具种类</th> 
												<th style="width:200px;">更新时间</th>
												<th>帮助文档</th> 
												<th style="width:30px;padding:5px;">操作</th> 
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${toolsList }" varStatus="status" var="tool">
												<tr>
													<td>${status.index+1 }</td> 
													<td class="ellipsis">
														<c:if test="${tool.uploaduid == uid }">
															<a href="javascript:show(${ tool.id});">${ tool.name}</a>
														</c:if>
														<c:if test="${tool.uploaduid != uid }">
															${ tool.name}
														</c:if>
													</td> 
													<td>
														<c:choose>
															<c:when test="${ tool.toolclass==0}">
																文件类型
															</c:when>
															<c:when test="${ tool.toolclass==1}">
																外部URL
															</c:when>
															<c:otherwise>
															</c:otherwise>
														</c:choose>
													</td> 
													<td><fmt:formatDate value="${ tool.uploadtime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
													<td style="text-align:left;">
														<c:if test="${tool.studyurl == '' }">
															无
														</c:if>
														<c:if test="${tool.studyurl != '' }">
															<c:forEach var="study" items="${fn:split(tool.studyurl,',')}">
													            <a class="fa fa-file cursor ellipsis clearastyle" target="_Top" style="width:130px;" href="javascript:window.location.href='${httpBase }studyword/${tool.id}/${fn:trim(study) }'" title="${study}">${study}</a>
													    	</c:forEach>
														</c:if>
													</td> 
													<td <c:if test="${tool.uploaduid == uid }">style="width: 100px;"</c:if>>
														<c:choose>
															<c:when test="${ tool.toolclass==0}">
																<c:if test="${tool.url == '' }">
																	无
																</c:if>
																<c:if test="${tool.url != '' }">
																	<a title="下载工具" target="_Top" class="fa fa-download cursor clearastyle" href="javascript:window.location.href='${httpBase }toolfile/${tool.id}/${fn:trim(tool.url) }'"></a>
																</c:if>
															</c:when>
															<c:when test="${ tool.toolclass==1}">
																<c:if test="${tool.url == '' }">
																	无
																</c:if>
																<c:if test="${tool.url != '' }">
																	<span class="fa fa-share cursor" onclick="useOpen(${tool.id})" title="外部工具链接"></span>
																</c:if>
																
															</c:when>
															<c:otherwise>
															</c:otherwise>
														</c:choose>
														<c:if test="${tool.uploaduid == uid }">
															<span class="fa fa-remove cursor ml10" onclick="removetool('${tool.name}',${tool.id},${tool.type})" title="删除工具"></span>
														</c:if>
													</td> 
												</tr>
											</c:forEach>
										</tbody>
									</table>
									</c:if>
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

<script type="text/javascript" src="js/toolsList.js"></script>
</body>
</html>