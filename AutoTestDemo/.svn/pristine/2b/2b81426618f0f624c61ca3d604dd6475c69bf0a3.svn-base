<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>头部导航</title>
</head>
<body>
<!-- SIDEBAR -->
<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-scroll">
				<nav>
					<ul class="nav">
						<li><a href="project/main.htm" class="active"><i class="lnr lnr-home"></i> <span>主画面</span></a></li>
						<c:forEach items="${mainRoot}" varStatus="status" var="rt">
							<c:if test="${rt == 0 }">
								<li>
								<c:set var="sidebar0" value="${sidebar[rt]}" />
								<a onclick="sidebarRemember(${rt},this)" href="#subPages" data-toggle="collapse" class="${ sidebar0.aClass}" aria-expanded="${ sidebar0.aExpanded}"><i class="lnr lnr-file-empty"></i> <span>用例</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
								<div id="subPages" class="${ sidebar0.divClass}" aria-expanded="${ sidebar0.divExpanded}" style="${ sidebar0.divStyle}">
									<ul class="nav">
										<c:forEach items="${mainLi}" varStatus="status" var="auth">
											<c:if test="${auth.section == 0 }">
												<li><a href="javascript:toBrowse(${auth.id})" class="">${auth.name}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
								</li>
							</c:if>
							<c:if test="${rt == 1 }">
								<li>
								<c:set var="sidebar1" value="${sidebar[rt]}" />
								<a onclick="sidebarRemember(${rt},this)" href="#subPanels" data-toggle="collapse" class="${ sidebar1.aClass}" aria-expanded="${ sidebar1.aExpanded}"><i class="lnr lnr-cog"></i> <span>配置</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
								<div id="subPanels" class="${ sidebar1.divClass}" aria-expanded="${ sidebar1.divExpanded}" style="${ sidebar1.divStyle}">
									<ul class="nav">
										<c:forEach items="${mainLi}" varStatus="status" var="auth">
											<c:if test="${auth.section == 1 }">
												<li><a href="javascript:toBrowse(${auth.id})" class="">${auth.name}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
								</li>
							</c:if>
							<c:if test="${rt == 2 }">
								<li>
								<c:set var="sidebar2" value="${sidebar[rt]}" />
								<a onclick="sidebarRemember(${rt},this)" href="#subProCount" data-toggle="collapse" class="${ sidebar2.aClass}" aria-expanded="${ sidebar2.aExpanded}"><i class="lnr lnr-chart-bars"></i> <span>统计</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
								<div id="subProCount" class="${ sidebar2.divClass}" aria-expanded="${ sidebar2.divExpanded}" style="${ sidebar2.divStyle}">
									<ul class="nav">
										<c:forEach items="${mainLi}" varStatus="status" var="auth">
											<c:if test="${auth.section == 2 }">
												<li><a href="javascript:toBrowse(${auth.id})" class="">${auth.name}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
								</li>
							</c:if>
							<c:if test="${rt == 3 }">
								<li>
								<c:set var="sidebar3" value="${sidebar[rt]}" />
								<a onclick="sidebarRemember(${rt},this)" href="#subTools" data-toggle="collapse" class="${ sidebar3.aClass}" aria-expanded="${ sidebar3.aExpanded}"><i class="fa fa-wrench"></i> <span>工具</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
								<div id="subTools" class="${ sidebar3.divClass}" aria-expanded="${ sidebar3.divExpanded}" style="${ sidebar3.divStyle}">
									<ul class="nav">
										<c:forEach items="${mainLi}" varStatus="status" var="auth">
											<c:if test="${auth.section == 3 }">
												<li><a href="javascript:toBrowse(${auth.id})" class="">${auth.name}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</nav>
			</div>
		</div>
</body>
</html>