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
					<div class="clearfix">
						<div class="fl"><h3 class="page-title">更新工具</h3></div>
						<div class="fr"><input type="button" value="返回" id="back" class="btn btn-default"></div>
					</div>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading tr">
										<input id="msg" type="text" value="${msg }" class="none">
									</div>
									<!-- 添加文件夹main -->
									<form id="qcdbForm" action="servertools/update.htm" method="post" enctype="multipart/form-data">
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:50%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">工具名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input name="name" type="text" value="${tool.name }" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">范围</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<select name="type" class="form-control input-sm">
														<option value="工具所属范围">工具所属范围</option>
														<option value="0" <c:if test="${tool.type == 0 }">selected="selected"</c:if>>常用工具</option>
														<option value="1" <c:if test="${tool.type == 1 }">selected="selected"</c:if>>性能测试</option>
														<option value="2" <c:if test="${tool.type == 2 }">selected="selected"</c:if>>安全测试</option>
													</select>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">种类</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<select name="toolclass" class="form-control input-sm">
														<option value="工具所属种类">工具所属种类</option>
														<option  <c:if test="${tool.toolclass == 0 }">selected="selected"</c:if> value="0">文件</option>
														<option  <c:if test="${tool.toolclass == 1 }">selected="selected"</c:if> value="1">链接</option>
													</select>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">帮助文档</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input type="text" id="tooltype" value="${ tool.type}" class="none">
													<input type="text" name="studyurl" value="${ tool.studyurl}" class="none">
													<input type="text" name="url" value="${ tool.url}" class="none">
													<input type="text" name="id" value="${ tool.id}" class="none">
													<input type="text" name="usecount" value="${ tool.usecount}" class="none">
													<input type="text" name="downloadcount" value="${ tool.downloadcount}" class="none">
													<input type="text" name="uploadtime" value="<fmt:formatDate value="${ tool.uploadtime}" pattern="yyyy-MM-dd hh:mm:ss"/>" class="none">
													<input type="text" name="uploaduid" value="${ tool.uploaduid}" class="none">
													<div>
														<c:forEach items="${fn:split(tool.studyurl, ',')}" var="help" varStatus="status">
															<c:if test="${help != '' && null != help }">
																<div id="help${status.index }" class="tl"><a class="fa fa-remove" href="javascript:removehelp('${help }','#help${status.index }')">${help }</a></div>
															</c:if>
														</c:forEach>
													</div>
													<div>
														<input style="cursor: pointer;" type="file" name="helps" multiple="true" accept=".xls,.xlsx">
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div id="checkClass">
												<c:if test="${ tool.toolclass == 0}">
													<div class="mt10">
													<div class="fl w150 tr mr10 h30">工具</div>
													<div class="fl mr10 h30">:</div>
													<div class="fl">
														<div><input style="cursor: pointer;" type="file" name="tl"></div>
													</div>
													</div>
													<div class="clearfix"></div>
												</c:if>
												<c:if test="${ tool.toolclass == 1}">
													<input style="display:none;" type="file" name="tl">
													<div class="mt10">
														<div class="fl w150 tr mr10 h30">链接</div>
														<div class="fl mr10 h30">:</div>
														<div class="fl"><input id="url" onblur="urlchange()" type="text" value="${tool.url }" class="form-control" placeholder="请输入工具链接"></div>
													</div>
													<div class="clearfix"></div>
												</c:if>
											</div>
											<div class="mt10 tr">
												<button id="updateTool" style="width:120px;" class="btn btn-default">更新</button>
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

<script type="text/javascript" src="js/updateTools.js"></script>
</body>
</html>