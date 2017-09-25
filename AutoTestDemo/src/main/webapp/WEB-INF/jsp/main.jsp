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
			<div class="main-content">
				<div class="container-fluid">
					<!-- OVERVIEW -->
					<div class="panel panel-headline">
						<div class="panel-heading">
							<h3 class="panel-title">工作情况</h3>
							<p class="panel-subtitle">统计时间: <fmt:formatDate value="${ info.beginTime}" pattern="yyyy-MM-dd"/> - <fmt:formatDate value="${ info.endTime}" pattern="yyyy-MM-dd"/></p>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-file-text"></i></span>
										<p>
											<span class="number">${ info.weekSuccess}</span>
											<span class="title">完成用例</span>
										</p>
									</div>
								</div>
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-file"></i></span>
										<p>
											<span class="number">${ info.weekFail}</span>
											<span class="title">未完成用例</span>
										</p>
									</div>
								</div>
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-check-square-o"></i></span>
										<p>
											<span class="number">${ info.rate}%</span>
											<span class="title">正确率</span>
										</p>
									</div>
								</div>
								<div class="col-md-3">
									<div class="metric">
										<span class="icon"><i class="fa fa-plus-square-o"></i></span>
										<p>
											<span class="number">${ info.weekNewTest}</span>
											<span class="title">新增用例</span>
										</p>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-9">
									<div id="headline-chart" class="ct-chart"></div>
								</div>
							</div>
						</div>
					</div>
					<!-- END OVERVIEW -->
					<div class="row">
						<div class="col-md-6">
							<!-- RECENT PURCHASES -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">历史用例</h3>
									<div class="right">
										<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
										<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
									</div>
								</div>
								<div class="panel-body no-padding">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>QC_ID</th>
												<th>用例名</th>
												<th>执行次数</th>
												<th>创建时间</th>
												<th>执行状态</th>
											</tr>
										</thead>
										<tbody>
										<c:if test="${null == testlist || '' == testlist[0].testname || undefined == testlist[0].testname}">
											<tr>
												<td colspan="6">
													<p style="font-size: 30px;text-align: center;">您在此项目没有添加过用例</p>
												</td>
											</tr>
										</c:if>
										<c:if test="${null != testlist && '' != testlist[0].testname && undefined != testlist[0].testname}">
											<c:forEach items="${testlist}" varStatus="status" var="test">
												<tr>
													<td><a href="javascript:;">${ test.tid}</a></td>
													<td><div class="ellipsis">${ test.testname}</div></td>
													<td>${ test.success + test.fail}</td>
													<td><fmt:formatDate value="${ test.createtime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
													<td>
														<c:if test="${ test.flag == 0}"><span class="label label-warning">UNKNOWN</span></c:if>
														<c:if test="${ test.flag == 1}"><span class="label label-danger">FAILED</span></c:if>
														<c:if test="${ test.flag == 2}"><span class="label label-success">SUCCESS</span></c:if>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										</tbody>
									</table>
								</div>
								<div class="panel-footer">
									<div class="row">
										<div class="col-md-6"><span class="panel-note"><i class="fa fa-clock-o"></i> 显示最新五条用例</span></div>
										<div class="col-md-6 text-right"><a href="javascript:getall('<c:if test="${null == testlist || '' == testlist[0].testname || undefined == testlist[0].testname}">true</c:if><c:if test="${null != testlist && '' != testlist[0].testname && undefined != testlist[0].testname}">false</c:if>');" class="btn btn-primary">查看全部</a></div>
									</div>
								</div>
							</div>
							<!-- END RECENT PURCHASES -->
						</div>
						<div class="col-md-6">
								<!-- TASKS -->
								<div class="panel">
									<div class="panel-heading">
										<h3 class="panel-title">我的工作ALL</h3>
										<div class="right">
											<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
											<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
										</div>
									</div>
									<div class="panel-body">
										<ul class="list-unstyled task-list">
											<li>
												<p>用例Success率 <span class="label-percent">${ info.allSuccessRate}%</span></p>
												<div class="progress progress-xs">
													<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="${ info.allSuccessRate}" aria-valuemin="0" aria-valuemax="100" style="width: ${ info.allSuccessRate}%">
														<span class="sr-only">${ info.allSuccessRate}% Complete</span>
													</div>
												</div>
											</li>
											<li>
												<p>用例Fail率 <span class="label-percent">${ info.allFailRate}%</span></p>
												<div class="progress progress-xs">
													<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${ info.allFailRate}" aria-valuemin="0" aria-valuemax="100" style="width:${ info.allFailRate}%">
														<span class="sr-only">${ info.allFailRate}% Complete</span>
													</div>
												</div>
											</li>
											<li>
												<p>用例未完成率 <span class="label-percent">${ info.allUnknownRate}%</span></p>
												<div class="progress progress-xs">
													<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="${ info.allUnknownRate}" aria-valuemin="0" aria-valuemax="100" style="width: ${ info.allUnknownRate}%">
														<span class="sr-only">${ info.allUnknownRate}% Complete</span>
													</div>
												</div>
											</li>
											<li>
												<p>个人用例占比 <span class="label-percent">${ info.allPersonalRate}%</span></p>
												<div class="progress progress-xs">
													<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${ info.allPersonalRate}" aria-valuemin="0" aria-valuemax="100" style="width: ${ info.allPersonalRate}%">
														<span class="sr-only">${ info.allPersonalRate}% Complete</span>
													</div>
												</div>
											</li>
										</ul>
									</div>
								</div>
								<!-- END TASKS -->
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
<script>
function getall(flag){
	if(flag=="true"){
		layer.msg("您在此项目没有添加过用例");
	}else{
		window.location.href='CMS/allUserTest.htm';
	}
}
</script>
</body>
</html>