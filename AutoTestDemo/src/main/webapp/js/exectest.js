var sk = null;
var IntervalID = 0;
$('#divLocker').hide();
$(function(){
	$("#testlog").text("请等待...");
	$("#startTest").click(function(){
		var swid = $("#switchAmbient").val();
		var browserFlag = $("#browserflag").is(':checked');
		var num = $("#execNum").val();
		var dataFilename = $("#dataFilename").val();
		var testID = $("#testID").val();
		var reg=new RegExp("[0-9]+");
		if(!reg.test(num)){
			alert("请输入正确的用例执行次数");
			$("#execNum").val(1);
			return;
		}
		var proid = $("#projectid").val();
//		var iframeName = $("#iframeName").val();
		
		if(proid=="请选择文件夹名"){
			alert("请选择文件夹名");
			return;
		}
		var tName = $("#testName").val();
		var temp = tName.replace(/\s+/g, "").length;
		if(temp == 0||testID==""){
			alert("实施用例名不能为空或者空格");
		}else{
//			if(dataFilename=="null"){
//				alert("请选择数据文件");
//				return false;
//			}
			$(this).attr("disabled","true");
			$("#back").attr("disabled","true");
			$("#batch").attr("disabled","true");
			sk = new com.czy.websocket("#testlog","#localAddress").init();
			$.ajax({
			  type: 'POST',
			  url: "Exec/ExecTest.htm",
			  data:{"swid":swid,"tName":tName,"proid":proid,"num":num,"dataFilename":dataFilename,"testID":testID,"browserFlag":browserFlag},
			  dataType: "json",
			  success: function(data){
				  //生成报告URL参数填充
				  if(null!=data.testID){
					  $("#testID").val(data.testID); 
				  }
				  if(data.msg.indexOf("是否继续等待?")>0){
					  if(!confirm(data.msg)){
						  	closeWait();
						  	return false;
						}else{
							addLockerCss();
							$("#divLocker").show();
							IntervalID = setInterval("waitQueue()",5000);
						}
				  }else{
					  closeWait();
				  }
				  if(data.msg!="SUCCESS"&&data.msg.indexOf("是否继续等待?")<0){
					  alert(data.msg);
				  }
			  },
			  error: function (xhr, status, text) {
				  closeWait();
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
	
	//下拉列表事件
	$("#projectid").change(function(){
		var pid = $(this).val();
		if(pid=="请选择文件夹名"){
			$("#fileshowmsg").show();
			return;
		}else{
			if($("#fileshowmsg").css("display")=="block")
				$("#fileshowmsg").hide();
		}
		$.ajax({
		  type: 'GET',
		  url: "Exec/syncfolder.htm?id="+pid,
		  success: function(){
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
		
		$("#testName").val("");
		$("#expect").html("");
		$(".expect").hide();
		$("#dataFilename").html('<option value="null">可选-先选择用例名</option>');
		$("#showtestdata").html('<p class="f16 bold">请选择用例数据文件</p>');
	});
	
	//返回事件
	$("#back").click(function(){
		window.location.href="project/main.htm";
	});
	$("#testName").keyup(function(){
		var proid = $("#projectid").val();
		if(proid=="请选择文件夹名"){
			$("#fileshowmsg").show();
			return;
		}else{
			if($("#fileshowmsg").css("display")=="block")
				$("#fileshowmsg").hide();
		}
		var flag = $(".expect").is(':hidden');
		if(flag){
			$(".expect").show();
		}
		var tName = $(this).val();
		if(tName==""){
			$("#expect").html("");
			$(".expect").hide();
		}
		if(tName.replace(/\s+/g, "").length!=0){
			$.ajax({
			  type: 'POST',
			  url: "CMS/expectTest.htm",
			  data:{"tName":tName,"proid":proid},
			  dataType: "json",
			  success: function(data){
				  $("#expect").html("");
				  $('#expectTemplate').tmpl(data.tests).appendTo('#expect');
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
	
	$("#batch").click(function(){
		window.location.href="CMS/smoking.htm";
	});
	
	$("#dataFilename").change(function(){
		var excelName = $(this).val();
		var testID = $("#testID").val();
		if("null"!=excelName){
			$.ajax({
			  type: 'POST',
			  url: "file/showdata.htm",
			  data:{"filename":excelName,"testID":testID},
			  dataType: "json",
			  success: function(data){
				  $("#showtestdata").html("");
				  if(null==data.sheet||data.sheet.length==0){
					  $("#showtestdata").html('<p class="f16 bold fontred">此文件数据有误或无数据</p>');
				  }else{
					  $('#excelTemplate').tmpl(data).appendTo('#showtestdata'); 
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
		}else{
			$("#showtestdata").html("");
		}
	});
});
//预想结果li点击事件
function expectItemClick(li){
	var testID = $(li).attr("value");
	$("#testID").val(testID);
	$("#testName").val($(li).html());
	$(".expect").hide();
	$("#showtestdata").html('<p class="f16 bold">请选择用例数据文件</p>');
	$.ajax({
		type: 'GET',
		  url: "file/folderNames.htm?id="+testID,
		  dataType: "json",
		  success: function(data){
			 if(data.list.length!=0){
				 $("#dataFilename").html('<option value="null">可选-先选择用例名</option>');
				 for(var i = 0;i<data.list.length;i++){
					 $("#dataFilename").append("<option value='"+data.list[i].filename+"'>"+data.list[i].filename+"</option>");
				 } 
			 }else{
				 $("#dataFilename").html('<option value="null">可选-此用例没有数据文件</option>');
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
function showReport(){
	var testID = $("#testID").val();
	var dataFilename = $("#dataFilename").val();
	var url = "";
	if(dataFilename!="null"){
		url = "output/"+testID+"/"+showDate()+"/1/html/index.html";
	}else{
		url = "output/"+testID+"/"+showDate()+"/report/html/index.html";
	}
	window.open(url);
}

function waitQueue(){
	$.ajax({
		  type: 'GET',
		  url: "Exec/waitQueue.htm",
		  dataType: "json",
		  success: function(data){
			  if(data.index!=0){
				  $("#divLocker").html("<div style='left:50%;background:white;height:150px;width:300px;position: relative;top: 50%;margin-left:-150px;margin-top:-150px;text-align: bottom;'><p style='color:red;font-weight:bold;font-size:16px;'>您前面还有["+data.index+"]人</p><div><image src='image/wait.gif' width='300px' height='100px' /></div></div>"
						  +"<div style='background:red;height:20px;width:20px;margin-left:925px;margin-top:-45px;cursor: pointer;' onclick='closeWaitAndDIV()'><h3>X</h3></div>");
			  }else{
				  clearInterval(IntervalID);
				  $("#divLocker").hide();
				  var swid = $("#switchAmbient").val();
				  var browserFlag = $("#browserflag").is(':checked');
				  var proid = $("#projectid").val();
				  var tName = $("#testName").val();
				  var num = $("#execNum").val();
				  var dataFilename = $("#dataFilename").val();
				  var testID = $("#testID").val();
				  $.ajax({
					  type: 'POST',
					  url: "Exec/ExecTest.htm",
					  data:{"swid":swid,"tName":tName,"proid":proid,"num":num,"dataFilename":dataFilename,"testID":testID,"browserFlag":browserFlag},
					  dataType: "json",
					  success: function(data){
						  if(null!=data.testID){
							  $("#testID").val(data.testID); 
						  }
						  closeWait();
					  },
					  error: function (xhr, status, text) {
						  closeWait();
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
		  },
		  error: function (xhr, status, text) {
			  removeQueue();
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


function removeQueue(){
	$.ajax({
		  type: 'GET',
		  url: "Exec/RemoveQueue.htm",
		  success: function(){
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

function addLockerCss(){
	$('#divLocker').css({
	    "position": "absolute",
	    "margin": "0 auto",
	    "background-color": "#000000",
	    "height":"100%",
	    "filter": "alpha(opacity=30)",
	    "opacity": "0.3",
	    "overflow": "hidden",
	    "width": "100%",
	    "z-index": "999",
	});
}

function closeWaitAndDIV(){
	clearInterval(IntervalID);
	$("#divLocker").hide();
	sk.close();
  	$("#startTest").removeAttr("disabled");
  	$("#back").removeAttr("disabled");
  	$("#batch").removeAttr("disabled");
  	removeQueue();
}

function closeWait(){
	sk.close();
  	$("#startTest").removeAttr("disabled");
  	$("#back").removeAttr("disabled");
  	$("#batch").removeAttr("disabled");
  	removeQueue();
}