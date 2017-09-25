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
	width:400px;
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
			<input id="userAddress" type="text" value="${userAddress}" class="none"/>
			<input id="localAddress" type="text" value="${localAddress}" class="none"/>
			<input id="msg" type="text" value="${msg }" class="none"/>
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<div class="fl"><h3 class="page-title">环境配置</h3></div>
					<div class="fr">
						<input type="button" value="返回" class="btn btn-default <c:if test="${!flag }">none</c:if>" id="back">
					</div>
					<div class="clearfix"></div>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading">
<!-- 										<h3 class="panel-title">环境配置</h3> -->
									</div>
									<!-- 添加文件夹main -->
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:50%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">描述信息</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input id="desc" name="amdesc" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">环境URL</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input id="url" name="amurl" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10 tr">
												<input id="addAmbient" value="添加" type="button"  class="btn btn-default">
											</div>
											<div class="clearfix"></div>
										</div>
									</div>
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
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>
<script type="text/javascript" src="js/nav.js"></script>

<script type="text/javascript">
$(function(){
	var msg = $("#msg").val();
	if(""!=msg&&undefined!=msg){
		if(msg=="true"){
			 layer.alert("环境删除成功",{icon:1});
		}else{
			 layer.alert("环境删除失败",{icon:2});
		}
	}
	$("#addAmbient").click(function(){
		var desc = $("#desc").val();
		var url = $("#url").val();
		if(desc==""&&url==""){
			layer.alert("描述信息与url不能为空");
			return;
		}
		if(desc==""){
			layer.alert("描述信息不能为空");
			return;
		}
		if(url==""){
			layer.alert("url不能为空");
			return;
		}
		$.ajax({
			 type: 'POST',
			  url: "switchambient/append.htm",
			  data:{"amdesc":desc,"amurl":url},
			  dataType: "json",
			  success: function(data){
				 if(data.msg){
					 layer.alert("环境添加成功",{icon:1});
					 $("#back").show();
				 }else{
					 layer.alert("环境添加失败",{icon:2});
				 }
				
				$("#desc").val("");
				$("#url").val("");
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
	$("#back").click(function(){
		window.location.href="switchambient/ambient.htm";
	});
});
</script>
</body>
</html>