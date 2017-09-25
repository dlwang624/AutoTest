$(function(){
	$("#test-scroll").slimScroll({
        height:'470px'
    });
	$("#testlog").slimScroll({
        height:'470px'
    });
	$("#showtestdata").slimScroll({
        height:'470px'
    });
	$("#back").click(function(){
		window.location.href="CMS/main.htm";
	});
	addLockerCss();
	$("#testlog").text("等待操作...");
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
	$("#startTest").click(function(){
		var swid = $("#switchAmbient").val();
		var browserFlag = $("#browserflag").is(':checked');
		var num = $("#execNum").val();
		var dataFilename = $("#dataFilename").val();
		var testID = $("#testID").val();
		var reg=new RegExp("[0-9]+");
		if(!reg.test(num)){
			layer.alert("请输入正确的用例执行次数");
			$("#execNum").val(1);
			return;
		}
		var proid = $("#projectid").val();
		if(proid=="请选择文件夹名"){
			layer.alert("请选择文件夹名");
			return;
		}
		var tName = $("#testName").val();
		var temp = tName.replace(/\s+/g, "").length;
		if(temp == 0||testID==""){
			layer.alert("请选择左侧执行用例");
		}else{
			allDisabled();
			sk = new com.czy.websocket("#testlog","#localAddress").init();
			$.ajax({
			  type: 'POST',
			  url: "Exec/userExecTest.htm",
			  data:{"swid":swid,"tName":tName,"proid":proid,"num":num,"dataFilename":dataFilename,"testID":testID,"browserFlag":browserFlag},
			  dataType: "json",
			  success: function(data){
				  //生成报告URL参数填充
				  if(null!=data.testID){
					  complateExecRefresh(data);
				  }
				  if(data.msg.indexOf("是否继续等待?")>0){
					  layer.confirm(data.msg, {
						  btn: ['确定', '取消'] //可以无限个按钮
						}, function(){
							$("#divLocker").show();
							if(!waitQueue()){
								IntervalID = setInterval("waitQueue()",5000);
							}
							layer.closeAll('dialog');
						}, function(){
							closeWait();
							layer.closeAll('dialog');
						});
				  }else{
					  closeWait();
				  }
				  if(data.msg!="SUCCESS"&&data.msg.indexOf("是否继续等待?")<0){
					  layer.alert(data.msg);
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
});


function give(ele,testname,id,projectid){
	if(id=="null"||null==id){
		layer.alert("请先同步用例");
		return;
	}
	$("td[select='0']").css({"background-color":"white","font-weight":"normal","color":"#676a6d"});	
	$("td[select='0']").parent().css({"background-color":"white","font-weight":"normal","color":"#676a6d"});	
	$("#testName").val(testname);
	$("#testID").val(id);
	$(ele).parent().parent().css({"background-color":"#E2E2E2","font-weight":"bold","color":"black"});
	$(ele).parent().parent().parent().css({"background-color":"#E2E2E2","font-weight":"bold","color":"black"});
	$("#projectid").val(projectid);
	$.ajax({
		type: 'GET',
		  url: "file/folderNames.htm?id="+id,
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
$(document).scroll(function(){
	var top = $("#detailed").scrollTop();
	var martop = $(this).scrollTop();
	$("#exceldata").css("margin-top",martop+top+"px");
	$("#detailed").css("margin-top",martop+top+"px");
	$("#top").css("margin-top",martop-45+"px");
	if(martop>45){
		$("#top").show();
	}else{
		$("#top").hide();
	}
});
function unlockTest(id){
	if(id==""||null==id||id==undefined){
		layer.alert("请先同步用例");
		return;
	}
	allDisabled();
	$.ajax({
		  type: 'GET',
		  url: "CMS/unlocked.htm?id="+id,
		  dataType: "json",
		  success: function(data){
			layer.alert(data.msg);
			removeAllDisabled();
		  },
		  error: function (xhr, status, text) {
			  removeAllDisabled();
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

//查找实例
function selTestOrSyncQC(btn,testName,id,projectid){
	$(btn).attr("disabled","true");
	var btnVal = $(btn).attr("title");
	var viewname = $("#viewname").val();
	if(btnVal=="查看"){
		if(id!=""){
			$.ajax({
			  type: 'POST',
			  url: "CMS/testValue.htm",
			  data:{"id":id},
			  dataType: "json",
			  success: function(data){
				if(data.value=="1"){
					layer.alert("此用例正在被他人编辑");
					$(btn).removeAttr("disabled");
				}else{
					window.location.href="CMS/selusertest.htm?id="+id+"&&proid="+projectid+"&&viewname="+viewname+"&&statusflag="+$("#statusflag").val();
					$(btn).removeAttr("disabled");
				}
			  },
			  error: function (xhr, status, text) {
				  $(btn).removeAttr("disabled");
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
	}
}
//checkQC规范
function checkQC(testName,projectid){
	allDisabled();
	$("#testlog").text("请等待...");
	var sk = new QCwebsocket("#testlog","#localAddress").init();
	$.ajax({
	  type: 'POST',
	  url: "CMS/usercheckQC.htm",
	  data:{"testName":testName,"projectid":projectid},
	  dataType: "text",
	  success: function(data){
		  sk.close();
		  removeAllDisabled();
	  },
	  error: function (xhr, status, text) {
		  sk.close();
		  removeAllDisabled();
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

function waitQueue(){
	$.ajax({
		  type: 'GET',
		  url: "Exec/waitQueue.htm",
		  dataType: "json",
		  success: function(data){
			  if(data.index!=0){
				  $("#divLocker").html("<div style='left:50%;background: transparent;height:150px;width:300px;position: relative;top: 50%;margin-left:-150px;margin-top:-75px;'><div role='alert' class='alert alert-warning alert-dismissible' style='margin: 0;padding:0;height:100%;color:black;text-align: center;'><button onclick='closeWaitAndDIV()' aria-label='Close' data-dismiss='alert' class='close' type='button' style='left: 0px;'><span aria-hidden='true' class='red'>×</span></button><i class='fa fa-warning yellow'></i> 您前面还有["+data.index+"]人<div style='width:300px;height:100px;text-align: center;line-height:100px;'><image src='image/loading.gif' style='border:0px;'/></div></div></div>");
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
					  url: "Exec/userExecTest.htm",
					  data:{"swid":swid,"tName":tName,"proid":proid,"num":num,"dataFilename":dataFilename,"testID":testID,"browserFlag":browserFlag},
					  dataType: "json",
					  success: function(data){
						  if(null!=data.testID){
							  complateExecRefresh(data);
						  }
						  closeWait();
						  return false;
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
	    "height": "100%",
	    "filter": "alpha(opacity=40)",
	    "opacity": "0.4",
	    "overflow": "hidden",
	    "width": "100%",
	    "z-index": "1111"
	});
	$("#divLocker").hide();
}

function allDisabled(){
	$("input[type='button']").attr("disabled","true");
	$("select").attr("disabled","true");
	$("button").attr("disabled","true");
}

function removeAllDisabled(){
	$("input[type='button']").removeAttr("disabled","true");
	$("select").removeAttr("disabled","true");
	$("button").removeAttr("disabled","true");
}

function closeWaitAndDIV(){
	$("#divLocker").hide();
	clearInterval(IntervalID);
	sk.close();
	removeAllDisabled();
  	removeQueue();
}


function complateExecRefresh(data){
	$("#testID").val(data.testID); 
	  var css = "fa fa-exclamation-circle yellow";
	  switch(data.execFlag){
  		case "0":
  			css = "fa fa-exclamation-circle yellow";
  			break;
  		case "1":
  			css = "fa fa-minus-circle red";
  			break;
  		case "2":
  			css = "fa fa-check-circle-o green";
  			break;
		default:
			css = "fa fa-exclamation-circle yellow";
			break;
	  }
	 $("span[title='"+data.testID+"']").attr("class",css);
	 $("#showreport").unbind("click");
	 $("#showreport").bind("click",function(){
		var dataFilename = $("#dataFilename").val();
		var url = "";
		if(dataFilename!="null"){
			url = "output/"+data.testID+"/"+showDate()+"/1/html/index.html";
		}else{
			url = "output/"+data.testID+"/"+showDate()+"/report/html/index.html";
		}
		window.open(url);
	}).show();
}

function closeWait(){
	sk.close();
	removeAllDisabled();
  	removeQueue();
}