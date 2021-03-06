var sk = null;
var sumreport = {};
sumreport.data = null;
$(function(){
	$('#execwait').hide();
	$("#qid").change(function(){
		$(".expect").hide();
		$("#foldername").val("");
		$("#folderID").val("");
		$("#filename").val("");
		$("#sumReport").html("生成");
		$("#qid").val($(this).val());
	});
	$("#sumReport").click(function(){
		$(".expect").hide();
		var html = $(this).html();
		if(html=="生成"){
			var qid = $("#qid").val();
			var folderID = $("#folderID").val();
			var foldername = $("#foldername").val();
			if(qid=="-1"&&foldername==""){
				layer.alert("请选择QC项目名和输入QC文件夹名");
				return;
			}
			if(qid=="-1"){
				layer.alert("请选择QC项目名");
				return;
			}
			if(foldername==""){
				layer.alert("请输入QC文件夹名");
				return;
			}
			sk = new com.czy.websocket("#testname","#localAddress").init();
			$(this).attr("disabled","true");
			$(this).html('<i class="fa fa-spinner fa-spin"></i>请等待...');
			var _this = $(this);
			addWaitCss();
			$("#execwait").show();
			$.ajax({
				type:'POST',
				url:"count/newreport.htm",
				data:{"qid":qid,"folderID":folderID,"foldername":foldername},
				dataType:"json",
				success:function(data){
					$(_this).removeAttr("disabled");
					if(data.filename!=""&&undefined!=data.filename){
						layer.alert(foldername+"总结报告生成成功",{icon:1});
						$(_this).html("下载");
						$("#filename").val(data.filename);
					}else{
						$(_this).html("生成");
						layer.alert(foldername+"总结报告生成失败",{icon:2});
					}
					sk.close();
					$("#execwait").hide();
				},
				error: function (xhr, status, text) {
					$(_this).removeAttr("disabled");
					$(_this).html("生成");
					sk.close();
					$("#execwait").hide();
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
			})
		}else{
			$("#trsum").submit();
		}
	});
	
	$("#foldername").keyup(function(){
		var flag = $(".expect").is(':hidden');
		if(flag){
			$(".expect").show();
		}
		var pName = $(this).val();
		var qid = $("#qid").val();
		if(pName==""){
			$("#expect").html("");
			$(".expect").hide();
		}
		if(pName.replace(/\s+/g, "").length!=0){
			$.ajax({
			  type: 'POST',
			  url: "project/expectReport.htm",
			  data:{"qid":qid,"pName":pName},
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
	
	$("#correctTestStatus").click(function(){
		var qid = $("#qid").val();
		var folderID = $("#folderID").val();
		var foldername = $("#foldername").val();
		if(qid=="-1"&&foldername==""){
			layer.alert("请选择QC项目名和输入QC文件夹名");
			return;
		}
		if(qid=="-1"){
			layer.alert("请选择QC项目名");
			return;
		}
		if(foldername==""){
			layer.alert("请输入QC文件夹名");
			return;
		}
		layer.confirm("是否确定批改[<span class='red'>"+foldername+"</span>]文件夹下所有用例评审状态为Reviewed", {
		  btn: ['确定', '取消'] //可以无限个按钮
		}, function(){
			$.ajax({
			  type: 'POST',
			  url: "count/correct.htm",
			  data:{"qid":qid,"folderID":folderID,"foldername":foldername},
			  dataType: "json",
			  success: function(data){
				  if(data.msg=="批改成功"){
					  layer.alert(data.msg,{icon:1});
				  }else{
					  layer.alert(data.msg,{icon:2});
				  }
			  },
			  error: function (xhr, status, text) {
				  layer.close();
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
		}, function(){
		});
		
	});
	
	$("#canvasReport").click(function(){
		var qid = $("#qid").val();
		var folderID = $("#folderID").val();
		var foldername = $("#foldername").val();
		var redmineID = $("#redmineID").val();
		if(qid=="-1"&&foldername==""){
			layer.alert("请选择QC项目名、输入QC文件夹名或需求ID");
			return;
		}
		if(qid=="-1"){
			layer.alert("请选择QC项目名");
			return;
		}
		if(foldername==""&&redmineID==""){
			layer.alert("请输入QC文件夹名或需求ID");
			return;
		}
		$.ajax({
		  type: 'POST',
		  url: "count/echartsreport.htm",
		  data:{"qid":qid,"folderID":folderID,"foldername":foldername,"redmineID":redmineID},
		  dataType: "json",
		  success: function(data){
			if(data.day!=undefined&&data.day.length!=0){
				$("#bugview").html("<div id='chartmain'></div>");
				sumreport.data = data;
				var chartmain = $("#chartmain");
				if(chartmain!=null&&undefined!=chartmain){
					var width = $("#sumReportPanel").width();
					$(chartmain).width(width);
					$(chartmain).height(400);
				}
				var myChart = echarts.init(document.getElementById('chartmain'));
				var option = {
					    title: {
					        text: 'Bug趋势图'
					    },
					    tooltip : {
					        trigger: 'axis',
					        axisPointer: {
					            type: 'cross',
					            label: {
					                backgroundColor: '#6a7985'
					            }
					        }
					    },
					    legend: {
					        data:['Bug数']
					    },
					    toolbox: {
					        feature: {
					            saveAsImage: {}
					        }
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : false,
					            data : data.day
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'Bug数',
					            type:'line',
					            stack: '总量',
					            areaStyle: {normal: {}},
					            data:data.bug
					        }
					    ]
					};
				myChart.setOption(option);
			}else{
				layer.msg("文件夹["+foldername+"]内无bug数据");
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
	});
});
//预想结果li点击事件
function expectItemClick(li){
	var nameid = $(li).html();
	var name = nameid.substring(0,nameid.lastIndexOf("-"));
	var id = nameid.substring(nameid.lastIndexOf("-")+1);
	$("#foldername").val(name);
	$("#folderID").val(id);
	$("#filename").val("");
	$("#sumReport").html("生成");
	$(".expect").hide();
}

function addWaitCss(){
	$("#execwait").css({
		"position": "absolute",
		"margin": "0 auto",
		"background-color": "#000000",
		"height":"100%",
	    "filter": "alpha(opacity=40)",
	    "opacity": "0.4",
	    "overflow": "hidden",
	    "width": "100%",
	    "z-index": "1110"
	});
}