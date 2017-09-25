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
<link rel="stylesheet" href="js/themes/default/easyui.css"/>
<link rel="stylesheet" href="js/themes/icon.css"/>
<!-- MAIN CSS -->
<link rel="stylesheet" href="assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="assets/css/demo.css">
<style type="text/css">
.table tbody tr td{
	height: 40px;
	line-height: 40px;
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
						<div class="fl">
							<h3 class="page-title"><c:if test="${viewname == 'userconfig'}">您的</c:if>定期执行计划列表</h3>
						</div>
						<input type="text" class="none" value="${viewname }" id="viewname">
						<div class="fr">
							<c:if test="${viewname != 'userconfig'}">
								<input type="button" value="返回" id="back" class="btn btn-default">
							</c:if>
						</div>
						<c:if test="${uid == 3 || uid == 4 }">
							<div class="fr"><input id="serverTimer" type="button" value="项目重新部署或重启后使用(暂用)" class="btn btn-default"></div>
						</c:if>
					</div>
					<div class="row">
							<div class="col-md-12">
								<div class="panel" style="padding:20px 10px 30px 10px;">
									<!-- Copy文件夹main -->
									<div class="mainContentDiv">
										<div class="contentDiv">
											<div class="row">
												<div class="col-md-12">
													<c:if test="${list== null || fn:length(list) == 0}">
														<div class="tc"><h3>当前QC库还没有添加定期执行计划</h3></div>
													</c:if>
													<c:if test="${list!= null && fn:length(list) > 0}">
													<!-- TABLE STRIPED -->
														<div class="panel-body" style="border:0px;">
															<table class="table table-striped">
																<thead>
																	<tr>
																		<th class="tc">ID</th>
																		<th class="tc">所属服务器IP</th>
																		<th class="tc">备注</th>
																		<th class="tc">新建时间</th>
																		<th class="tc">执行时间</th>
																		<th class="tc">操作</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${list }" varStatus="status" var="timer">
																		<tr>
																			<td>${ timer.id}</td>
																			<td>${ timer.ip}</td>
																			<td>${ timer.remark}</td>
																			<td><fmt:formatDate value="${ timer.newdate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
																			<td>
																				<c:set value="${fn:split(timer.execdate,'@') }" var="dates" />
																				<c:forEach items="${ dates }" var="date" varStatus="status">
																					<c:if test="${status.index==0 }">
																						<c:choose>
																							<c:when test="${date == 1}">
																						      	周日
																						    </c:when>
																							<c:when test="${date == 2}">
																						      	周一
																						    </c:when>
																							<c:when test="${date == 3}">
																						      	周二
																						    </c:when>
																							<c:when test="${date == 4}">
																						      	周三
																						    </c:when>
																							<c:when test="${date == 5}">
																						      	周四
																						    </c:when>
																							<c:when test="${date == 6}">
																						      	周五
																						    </c:when>
																							<c:when test="${date == 7}">
																						      	周六
																						    </c:when>
																						    <c:otherwise>
																						       	<span class="fontred">非法数据</span>
																						    </c:otherwise>
																						</c:choose>
																					</c:if>
																					<c:if test="${status.index==1 }">
																						${ date}
																					</c:if>
																				</c:forEach>
																			</td>
																			<td>
																				<input type="button" value="查看" onclick="selTimer(${timer.id})" class="btn btn-default">
																				<input class="btn btn-default" type="button" value='<c:if test="${ timer.startflag }">停止</c:if><c:if test="${ !timer.startflag }">启动</c:if>' onclick="onTimerTest(${timer.id},this)">
																			</td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													<!-- END TABLE STRIPED -->
													</c:if>
												</div>
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

<script type="text/javascript" src="js/timerConfigList.js"></script>

</body>
</html>