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
			<input id="userAddress" type="text" value="${userAddress}" class="none"/>
			<input id="localAddress" type="text" value="${localAddress}" class="none"/>
			<input id="msg" type="text" value="${msg }" class="none"/>
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-bottom:0px;">
				<div class="container-fluid">
					<h3 class="page-title">拷贝QC文件夹</h3>
					<div class="row">
							<div class="col-md-12">
								<div class="panel">
									<div class="panel-heading">
<!-- 										<h3 class="panel-title">用例</h3> -->
									</div>
									<!-- Copy文件夹main -->
									<div class="mainContentDiv">
										<div class="contentDiv" style="width:45%;">
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">文件夹编号</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input style="width:250px;" name="baseid" type="text" class="form-control">
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10">
												<div class="fl w150 tr mr10 h30">目标文件夹的编号</div>
												<div class="fl mr10 h30">:</div>
												<div class="fl">
													<input style="width:250px;" id="newid" name="newid" type="text" class="form-control" placeholder="请输入编号或文件夹名选择编号">
													<div class="expect">
														<ul id="expect"></ul>
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="mt10 tr">
												<button class="btn btn-default" id="backup" style="width:120px;">Copy</button>
											</div>
											<div class="clearfix"></div>
											
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
<script id="expectTemplate" type="text/x-jquery-tmpl">
	<li style="width:250px;" onclick="expectItemClick(this)" value="{{= projectid}}">{{= name}}-{{= projectid}}</li>
</script>
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script src="js/layer/layer.js"></script>
<script type="text/javascript" src="js/nav.js"></script>

<script type="text/javascript">
$(function(){
	$("#backup").click(function(){
		var _this=$(this);
		var baseid = $("input[name='baseid']").val();
		var newid = $("input[name='newid']").val();
		if(baseid==""&&newid==""){
			layer.alert("Copy的文件夹编号和Copy至目标文件夹的编号不能为空");
			return;
		}
		if(baseid==""){
			layer.alert("Copy的文件夹编号不能为空");
			return;
		}
		if(newid==""){
			layer.alert("Copy至目标文件夹的编号不能为空");
			return;
		}
		$(_this).attr("disabled","true");
		$(_this).html('<i class="fa fa-spinner fa-spin"></i>请等待...');
		$.ajax({
		 type: 'GET',
		  url: "project/backupsFolder.htm?baseid="+baseid+"&newid="+newid,
		  dataType: "json",
		  success: function(data){
			layer.alert(data.msg);
			$("input[name='baseid']").val("");
			$("input[name='newid']").val("");
			$(_this).removeAttr("disabled");
			$(_this).html("Copy");
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
				$(_this).removeAttr("disabled");
				$(_this).html("Copy");
	        }
		});
	});
	
	$("#newid").keyup(function(){
		var flag = $(".expect").is(':hidden');
		if(flag){
			$(".expect").show();
		}
		var pName = $(this).val();
		if(pName==""){
			$("#expect").html("");
			$(".expect").hide();
		}
		if(pName.replace(/\s+/g, "").length!=0){
			$.ajax({
			  type: 'POST',
			  url: "project/expectProject.htm",
			  data:{"pName":pName},
			  dataType: "json",
			  success: function(data){
				  $("#expect").html("");
				  $('#expectTemplate').tmpl(data.prolist).appendTo('#expect');
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
	});
});
//预想结果li点击事件
function expectItemClick(li){
	var proID = $(li).attr("value");
	$("#newid").val(proID);
	$(".expect").hide();
}
</script>
</body>
</html>