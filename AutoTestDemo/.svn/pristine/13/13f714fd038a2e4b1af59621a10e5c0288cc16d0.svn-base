<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>头部导航</title>
</head>
<body>
<!-- NAVBAR -->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="brand" style="line-height:10px;width: 260px;text-align: center;">
		<a href="project/main.htm"><span style="font-size:30px;width: 260px;" class="ellipsis">${ dbname}</span></a>
	</div>
	<div class="container-fluid">
		<div class="navbar-btn">
			<button id="layerfull" type="button" class="btn-toggle-fullwidth">
			<i class="<c:if test="${ layoutfull ==''}">lnr lnr-arrow-left-circle</c:if>
			<c:if test="${ layoutfull !=''}">lnr lnr-arrow-right-circle</c:if>">
			</i>
			</button>
		</div>
		<form id="selTestByQAndP" action="CMS/getTest.htm" class="navbar-form navbar-left" method="post">
			<div class="input-group">
				<input type="text" value="${ likename}" name="likename" class="form-control" placeholder="请输入用例名(模糊查询)">
				<span class="input-group-btn"><button type="button" id="gtestbynq" class="btn btn-primary">搜索</button></span>
			</div>
		</form>
		<div id="navbar-menu" style="margin-right:30px;">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="javascript:;" class="dropdown-toggle icon-menu" data-toggle="dropdown">
						<i class="lnr lnr-alarm"></i>
						<span class="badge bg-danger"><c:if test="${ allUnknown + allFail !=0}">${ allUnknown + allFail}</c:if></span>
					</a>
					<ul class="dropdown-menu notifications">
						<li><a href="javascript:<c:if test="${ allUnknown > 0}">window.location.href='CMS/getStatus.htm?flag=0'</c:if>;" class="notification-item"><span class="dot bg-warning"></span>未执行用例数&nbsp;&nbsp;${allUnknown }</a></li>
						<li><a href="javascript:<c:if test="${ allFail > 0}">window.location.href='CMS/getStatus.htm?flag=1'</c:if>;" class="notification-item"><span class="dot bg-danger"></span>错误用例个数&nbsp;&nbsp;${allFail }</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="lnr lnr-question-circle"></i> <span>帮助</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
					<ul class="dropdown-menu">
						<li><a href="javascript:;">XXXXX</a></li>
						<li><a href="javascript:;">XXXXX</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><span>${nickname }</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
					<ul class="dropdown-menu">
						<li><a href="javascript:window.location.href='CMS/allUserTest.htm';"><i class="lnr lnr-user"></i> <span>我的用例</span></a></li>
						<li><a href="javascript:;"><i class="lnr lnr-envelope"></i> <span>我的消息</span></a></li>
						<li><a href="javascript:window.location.href='timer/userConfig.htm';"><i class="lnr lnr-cog"></i> <span>定期用例</span></a></li>
						<li><a href="javascript:logout();"><i class="lnr lnr-exit"></i> <span>登出</span></a></li>
					</ul>
				</li>
				<!-- <li>
					<a class="update-pro" href="#downloads/klorofil-pro-bootstrap-admin-dashboard-template/?utm_source=klorofil&utm_medium=template&utm_campaign=KlorofilPro" title="Upgrade to Pro" target="_blank"><i class="fa fa-rocket"></i> <span>UPGRADE TO PRO</span></a>
				</li> -->
			</ul>
		</div>
	</div>
</nav>
</body>
</html>