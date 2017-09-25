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
.form-control{
	width:250px;
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
					<h3 class="page-title">编辑文件夹</h3>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading tr">
<!-- 										<h3 class="panel-title">用例</h3> -->
										<div><input type="button" value="返回" class="btn btn-default" id="back"></div>
									</div>
									<!-- 添加文件夹main -->
									<form id="pro">
									<input type="text" value="${pro.id }" name="id" class="none">
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:45%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">QC文件夹编号</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input name="projectid" value="${pro.projectid }" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">QC文件夹名</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input name="name" value="${pro.name }" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10 tr">
												<input id="updatePro" value="修改" type="button"  class="btn btn-default">
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

<script type="text/javascript">
$(function(){
	$("#back").click(function(){
		window.location.href="Exec/projectlist.htm";
	});
	
	$("#updatePro").click(function(){
		var name = $("input[name='projectname']").val();
		var projectid = $("input[name='projectid']").val();
		if(name==""){
			layer.alert("文件夹名不能为空");
			return;
		}
		if(projectid==""){
			layer.alert("文件夹编号不能为空");
			return;
		}
		if(name==""&&projectid==""){
			layer.alert("文件夹名和编号不能为空");
			return;
		}
		$.ajax({
			 type: 'POST',
			  url: "Exec/updatepro.htm",
			  data:$("#pro").serialize(),
			  dataType: "json",
			  success: function(data){
				layer.alert(data.msg);
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
	});
});
</script>
</body>
</html>