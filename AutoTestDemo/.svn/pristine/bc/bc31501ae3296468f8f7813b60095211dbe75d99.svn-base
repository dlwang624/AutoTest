<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>CATM春之翼测试门户平台</title>
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
<body>
<!-- MAIN -->
<div class="main">
	<!-- MAIN CONTENT -->
	<div class="main-content" style="">
		<div class="container-fluid">
					<h2 class="page-title"><b>CATM&nbsp;春之翼测试门户平台</b></h2>
					<div class="row">
						<div class="col-md-8">
							<!-- BASIC TABLE -->
							<div class="panel" style="<c:if test="${ null!=uid && ''!=uid}">height: 200px;</c:if><c:if test="${ null==uid || ''==uid}">height: 250px;</c:if>">
								<div class="panel-body" style="padding-bottom:20px;padding-left:50px;">
									<div style="<c:if test="${ null==uid || ''==uid}">margin-top: 30px;</c:if>">
									<h4><b>
									今日
									<c:if test="${ null==gateway.ppCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.ppCount}">
										${ gateway.ppCount}
									</c:if>
									人使用了测试平台，累计使用人次
									<c:if test="${ null==gateway.totalPPCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.totalPPCount}">
										${ gateway.totalPPCount}
									</c:if>
									次
									</b></h4>
									</div>
									
									<div class="mt25">
									<h4><b>
									平台
									<c:if test="${ null==gateway.toolCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.toolCount}">
										${ gateway.toolCount}
									</c:if>
									款测试工具已被使用
									<c:if test="${ null==gateway.usetoolCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.usetoolCount}">
										${ gateway.usetoolCount}
									</c:if>
									次，下载
									<c:if test="${ null==gateway.downtoolCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.downtoolCount}">
										${ gateway.downtoolCount}
									</c:if>
									次
									</b></h4>
									</div>
									
									<div class="mt25">
									<h4><b>
									平台已完成
									<c:if test="${ null==gateway.regressCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.regressCount}">
										${ gateway.regressCount}
									</c:if>
									次自动回归测试，节省至少
									<c:if test="${ null==gateway.regressTime}">
										0
									</c:if>
									<c:if test="${ null!=gateway.regressTime}">
										${ gateway.regressTime}
									</c:if>
									小时人力
									</b></h4>
									</div>
									
									<div class="mt25">
									<h4><b>
									平台已完成
									<c:if test="${ null==gateway.releaseCount}">
										0
									</c:if>
									<c:if test="${ null!=gateway.releaseCount}">
										${ gateway.releaseCount}
									</c:if>
									次发布测试，节省至少
									<c:if test="${ null==gateway.releaseTime}">
										0
									</c:if>
									<c:if test="${ null!=gateway.releaseTime}">
										${ gateway.releaseTime}
									</c:if>
									小时人力
									</b></h4>
									</div>
								</div>
							</div>
							<!-- END BASIC TABLE -->
						</div>
						<div class="col-md-4">
							<!-- TABLE NO PADDING -->
							<div class="panel" style="padding-bottom:20px;<c:if test="${ null!=uid && ''!=uid}">height: 200px;</c:if><c:if test="${ null==uid || ''==uid}">height: 250px;</c:if>">
								<div class="panel-heading">
									<h3 class="panel-title"><b>登录</b></h3>
								</div>
								<div class="panel-body no-padding" style="margin: 0 auto;text-align: center;width:350px;">
									<c:if test="${ null!=uid && ''!=uid}">
										<div class="mt15">
											欢迎，${nickname }
										</div>
										<br>
										<div class="mt10" style="text-align: right;">
											<button class="btn btn-primary" onclick="javascript:window.location.href='project/main.htm'">进入平台</button>
										</div>
									</c:if>
									<c:if test="${ null==uid || ''==uid}">
										<input id="msg" class="none" type="text" value="${msg }">
										<form action="user/login.htm" method="post">
											<div>
												<input name="uname" type="text" class="form-control" placeholder="请输入用户名"/>
											</div>
											<div class="mt10">
												<input name="upass" type="password" class="form-control" placeholder="请输入密码"/>
											</div>
											<div class="mt10">
												<select name="qcname" class="form-control">
													<option selected="selected">请选择QC库</option>
													<c:forEach items="${list }" varStatus="status" var="qcdb">
															<option value="${qcdb.id }">
																	${ qcdb.dbname}
															</option>
														</c:forEach>
												</select>
											</div>
											<div class="mt10" style="text-align: right;">
												<button class="btn btn-primary">登录</button>
											</div>
											<input name="pagename" type="text" value="index" class="none">
										</form>
									</c:if>
								</div>
							</div>
							<!-- END TABLE NO PADDING -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-8">
							<!-- BASIC TABLE -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title"><b>平台动态</b></h3>
								</div>
								<div class="panel-body">
									<table class="table">
									</table>
								</div>
							</div>
							<!-- END BASIC TABLE -->
						</div>
						<div class="col-md-4">
							<!-- TABLE NO PADDING -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title"><b>近期培训</b></h3>
								</div>
								<div class="panel-body no-padding">
									<table class="table">
									</table>
								</div>
							</div>
							<!-- END TABLE NO PADDING -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<!-- BORDERED TABLE -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title"><b>排行榜</b></h3>
									<div class="right">
										<button class="btn-toggle-collapse" type="button"><i class="lnr lnr-chevron-up"></i></button>
									</div>
								</div>
								<div class="panel-body">
									<table class="table table-bordered">
									</table>
								</div>
							</div>
							<!-- END BORDERED TABLE -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<!-- BORDERED TABLE -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title"><b>友情链接</b></h3>
									<div class="right">
										<button class="btn-toggle-collapse" type="button"><i class="lnr lnr-chevron-up"></i></button>
									</div>
								</div>
								<div class="panel-body">
									<table class="table table-bordered">
									</table>
								</div>
							</div>
							<!-- END BORDERED TABLE -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<!-- BORDERED TABLE -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title"></h3>
								</div>
								<div class="panel-body">
									<table class="table table-bordered">
									</table>
								</div>
							</div>
							<!-- END BORDERED TABLE -->
						</div>
					</div>
				</div>
	</div>
	<!-- END MAIN CONTENT -->
</div>
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>

<script src="js/gateway.js"></script>
</body>
</html>