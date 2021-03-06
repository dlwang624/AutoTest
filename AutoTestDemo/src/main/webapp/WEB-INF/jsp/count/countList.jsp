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
.loadingParent{ width:150px; margin:0 auto;}
.loadingParent canvas{ display:block; margin:0 auto;}
.table thead tr th{
	text-align: center;
	padding:0 !important;
	line-height:30px !important;
}
.table tbody tr td{
	text-align: center;
	padding:5px 0px 5px 0px !important;
	line-height: 150px;
	overflow:hidden;
	white-space:nowrap;
	text-overflow: ellipsis;
}
#usertable tbody tr td{
	line-height: 40px !important;
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
			<div class="main-content" style="padding:20px 10px 0px 10px;">
				<div class="container-fluid">
					<div class="fl"><h3 class="page-title">数据统计</h3></div>
					<div class="fr">
						今日平台使用人数:${fn:length(aggregate.ppCount)}人，今日服务被调用次数: ${ aggregate.apiCount}次
						<c:if test="${uid == 3 || uid ==4 }"><button class="btn btn-default" onclick="releasecount()">统计</button></c:if>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-6" style="padding:0px 5px 0px 5px;">
							<!-- 文件夹统计 -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">文件夹统计</h3>
								</div>
								<div class="panel-body" id="folder-scroll">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th class="w50">行号</th>
												<th>文件夹名</th>
												<th style="width:60px;">Success</th>
												<th class="w50">Fail</th>
												<th class="w150">Fail比例</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list }" varStatus="status" var="count">
												<tr>
													<td>${status.index+1 }</td> 
													<td style="font-size:18px;"><a href="javascript:selcount(${ count.id},'${ count.name}');">${ count.name}</a></td> 
													<td style="font-size:18px;font-weight: bold;color:#a3cf62;">${ count.success}</td> 
													<td style="font-size:18px;font-weight: bold;color:#d93a49;">${ count.fail}</td>
													<td>
														<div class="loadingParent">
															<input type="text" id="${ count.id}" value="${ count.percent}" class="none">
															<canvas id="canvasIndex${ count.id}" width="150" height="150" amount="10000" vote="10000" tip="50">${count.percent }%</canvas>
														</div>
													</td> 
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<!-- 文件夹统计 -->
						</div>

						<div class="col-md-6" style="padding:0px 5px 0px 5px;">
							<!-- 用户统计 -->
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">用户统计</h3>
								</div>
								<div class="panel-body" id="user-scroll">
									<table id="usertable" class="table table-bordered">
										<thead>
											<tr>
												<th>行号</th>
												<th>用户名</th>
												<th>总用例数</th>
												<th>总步骤数</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${userList }" varStatus="status" var="uc">
												<tr>
													<td>${status.index+1 }</td> 
													<td style="font-size:18px;"><a href="javascript:selUCount(${ uc.id},'${uc.name }');">${ uc.name}</a></td> 
													<td style="font-size:18px;font-weight: bold;">${ uc.testCount}</td> 
													<td style="font-size:18px;font-weight: bold;">${ uc.stepCount}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<!-- 用户统计  -->
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

<script type="text/javascript" src="js/canvas/cycle.js"></script>
<script type="text/javascript" src="js/canvas/hidpi-canvas.min.js"></script>
<script type="text/javascript" src="js/canvas/modernizr-2.6.2.min.js"></script>

<script type="text/javascript">
$(function(){
// 	$("#folder-scroll").slimScroll({
// 		 height:'470px'
// 	});
// 	$("#user-scroll").slimScroll({
// 		 height:'470px'
// 	});
	$(".loadingParent").each(function(){
		var per = $(this).children(".none").val();
		var id = $(this).children(".none").attr("id");
		var width=$(".loadingParent").width();
		var option={
			   percent:per,   //百分比数值
			   w:width,          //宽度
			   oneCircle:"ture"  //是否是整个圆  默认半圆
	 		}
		 $("#canvasIndex"+id).audios2(option);
	});
});

function releasecount(){
	$.ajax({
		type:"GET",
		url:"count/servercount.htm",
		dataType:"json",
		success:function(data){
			if(data.msg=="服务器数据统计成功"){
				layer.alert(data.msg,{
		            icon: 1,
		        });
			}else{
				layer.alert(data.msg,{
		            icon: 2,
		        });
			}
		},
		error: function (xhr, status, text) {
          switch (status) {
             case 404:
                 console.log("File not found");
                 break;
             case 500:
                 console.log("Server error");
                 break;
             case 0:
                 console.log("Request aborted");
                 break;
             default:
            	 var msg = 'Unknown error: ' + status + " " + text + "\n"
            	 		+ "readyState: " + xhr.readyState + "\n"
            	 		+ "responseText: "+ xhr.responseText + "\n"
            	 		+ "status: " + xhr.status + "\n"
            	 console.log(msg);
            }
        }
	});
}

function selcount(id,proname){
	window.location.href="count/desc.htm?id="+id+"&&proname="+proname;
}
function selUCount(uid,name){
	window.location.href="count/userdesc.htm?uid="+uid+"&&name="+name;
}
</script>
</body>
</html>