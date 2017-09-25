$(function(){
	var tids = $("input[name='testid']").val();
	$.ajax({
	  type: 'POST',
	  url: "CMS/byIdsTest.htm",
	  data:{"ids":tids},
	  dataType: "json",
	  success: function(data){
		  if(data!=null){
//			  layer.alert(JSON.stringify(data));
			  $("#testtree").tree({
		        data: data,
		        checkbox: true,
		        cascadeCheck: true,
		        onClick: function (node, checked) {
		          if (checked) {
		            var parentNode = $("#testtree").tree('getParent', node.target);
					var childNode = $("#testtree").tree('getChildren', node.target);
		            if (parentNode != null) {
		              $("#testtree").tree('check', parentNode.target);
		            }
		          } else {
		            var childNode = $("#testtree").tree('getChildren', node.target);
		            if (childNode.length > 0) {
		              for (var i = 0; i < childNode.length; i++) {
		                $("#testtree").tree('uncheck', childNode[i].target);
		              }
		            }
		          }
		        }
		      });
		  }else{
			  $("#testtree").html("错误,无数据");
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
	
	//加载email
	var toms = $("input[name='touserid']").val().split(",");
	var ccms = $("input[name='ccuserid']").val().split(",");
	
	if(toms!=""){
		for(var i=0;i<toms.length;i++){
			$("#tomails").append("<div><input type='text' name='tomails' value='"+toms[i]+"' readonly='readonly' style='border:none;background-color: transparent;border-color:transparent;width:180px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;'><i onclick='removeMail(this)' style='cursor: pointer;color:red;' class='fa fa-remove'></i></div>");
		}
	}
	if(ccms!=""){
		for(var i=0;i<ccms.length;i++){
			$("#ccmails").append("<div><input type='text' name='ccmails' value='"+ccms[i]+"' readonly='readonly' style='border:none;background-color: transparent;border-color:transparent;width:180px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;'><i onclick='removeMail(this)' style='cursor: pointer;color:red;' class='fa fa-remove'></i></div>");
		}
	}
	
	$("#week").change(function(){
		var week = $(this).val();
		var weekandtime = $("input[name='execdate']").val().split("@");
		$.ajax({
		  type: 'GET',
		  url: "timer/weekChangeExcept.htm?week="+week+"&&exceptTime="+weekandtime[1],
		  dataType: "json",
		  success: function(data){
			  $("#time").html('<option value="null">请选择时间</option>');
			  $('#weekChangeTemplate').tmpl(data).appendTo('#time');
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
		var viewname = $("#viewname").val();
		if(viewname=="userconfig"){
			window.location.href="timer/userConfig.htm";
		}else{
			window.location.href="timer/allConfig.htm";
		}
	});
	$("#time").change(function(){
		$("#timetemp").val($(this).val());
	});
	
	$("#upConfig").click(function(){
		var remark = $("#remark").val();
		if(remark==""||remark==undefined){
			layer.alert("请输入定期执行用例的备注");
			return false;
		}
		var testids = getChecked();
		if(testids==""||testids==undefined){
			layer.alert("请选择执行用例");
			return false;
		}
		var time = $("#timetemp").val();
		if(time==""||time==undefined){
			layer.alert("请输入执行时间");
			return false;
		}
		var regex = /^([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$/;
		if(!regex.test(time)){
			layer.alert("输入的时间格式有误");
			$("#time").focus();
			return false;
		}
		var swid = $("#switchAmbient").val();
		var week = $("#week").val();
		var tomails = $("input[name='tomails']").map(function(){return $(this).val()}).get().join(",");
		var ccmails = $("input[name='ccmails']").map(function(){return $(this).val()}).get().join(",");
		$("input[name='testid']").val(testids);
		var startFlag = $("input[name='execdate']").val();
		var edatas = startFlag.split("@");
		startFlag = edatas[2];
		$("input[name='execdate']").val(week+"@"+time+"@"+startFlag+"@"+swid);
		$("input[name='touserid']").val(tomails);
		$("input[name='ccuserid']").val(ccmails);
		$("input[name='remark']").val(remark);
		$.ajax({
			  type: 'POST',
			  url: "timer/upconfig.htm",
			  data:$("#timer").serialize(),
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
	$("#to").keyup(function(e){
		var mail = $(this).val();
		//回车
		if(e.keyCode==13){
			if(!checkMailEquals("tomails",mail)){
				$(this).val("");
				$("#expectto").html("");
				$(".expectto").hide();
				return false;
			}
			var regex = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@inner.czy.com/;
			if(regex.test(mail)){
				$("#expectto").html("");
				$(".expectto").hide();
				$("#to").val("");
				$("#tomails").append("<div><input type='text' name='tomails' value='"+mail+"' readonly='readonly' style='border:none;background-color: transparent;border-color:transparent;width:180px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;'><i onclick='removeMail(this)' style='cursor: pointer;color:red;' class='fa fa-remove'></i></div>");
				return false;
			}
		}
		
		var flag = $(".expectto").is(':hidden');
		if(flag){
			$(".expectto").show();
		}
		if(mail==""){
			$("#expectto").html("");
			$(".expectto").hide();
		}
		if(mail.replace(/\s+/g, "").length!=0){
			$.ajax({
			  type: 'POST',
			  url: "timer/expectmail.htm",
			  data:{"mail":mail},
			  dataType: "json",
			  success: function(data){
				  $("#expectto").html("");
				  $('#expectTemplate').tmpl(data.mails).appendTo('#expectto');
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
	
	$("#cc").keyup(function(e){
		var mail = $(this).val();
		//回车
		if(e.keyCode==13){
			if(!checkMailEquals("ccmails",mail)){
				$(this).val("");
				$("#expectcc").html("");
				$(".expectcc").hide();
				return false;
			}
			var regex = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@inner.czy.com/;
			if(regex.test(mail)){
				$("#expectcc").html("");
				$(".expectcc").hide();
				$("#cc").val("");
				$("#ccmails").append("<div><input type='text' name='ccmails' value='"+mail+"' readonly='readonly' style='border:none;background-color: transparent;border-color:transparent;width:180px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;'><i onclick='removeMail(this)' style='cursor: pointer;color:red;' class='fa fa-remove'></i></div>");
				return false;
			}
		}
		
		var flag = $(".expectcc").is(':hidden');
		if(flag){
			$(".expectcc").show();
		}
		if(mail==""){
			$("#expectcc").html("");
			$(".expectcc").hide();
		}
		if(mail.replace(/\s+/g, "").length!=0){
			$.ajax({
			  type: 'POST',
			  url: "timer/expectmail.htm",
			  data:{"mail":mail},
			  dataType: "json",
			  success: function(data){
				  $("#expectcc").html("");
				  $('#expectTemplate').tmpl(data.mails).appendTo('#expectcc');
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
	$("#getall").click(function(){
		window.location.href="";
	});
});

function expectItemClick(li){
	var mail = $(li).html();
	var mails = $(li).parent().parent().next();
	var id = $(mails).attr("id");
	if(!checkMailEquals(id,mail)){
		return false;
	}
	$("#to").val("");
	$("#cc").val("");
	var mails = $(li).parent().parent().next();
	var id = $(mails).attr("id");
	if(id=="tomails"){
		$(mails).append("<div><input type='text' name='tomails' value='"+mail+"' readonly='readonly' style='border:none;background-color: transparent;border-color:transparent;width:180px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;'><i onclick='removeMail(this)' style='cursor: pointer;color:red;' class='fa fa-remove'></i></div>");
	}else{
		$(mails).append("<div><input type='text' name='ccmails' value='"+mail+"' readonly='readonly' style='border:none;background-color: transparent;border-color:transparent;width:180px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;'><i onclick='removeMail(this)' style='cursor: pointer;color:red;' class='fa fa-remove'></i></div>");
	}
	$(li).parent().parent().hide();
}

function checkMailEquals(elename,mail){
	var flag = true;
	$("input[name='"+elename+"']").each(function(){
		var mailtemp = $(this).val();
		if(mailtemp==mail){
			layer.alert("["+mailtemp+"]此mail已添加");
			flag = false;
		}
	});
	return flag;
}

function removeMail(e){
	$(e).parent().remove();
}
function getChecked()
{
  var arr = [];
  var checkeds = $('#testtree').tree('getChecked', 'checked');
  for (var i = 0; i < checkeds.length; i++) {
	var id = checkeds[i].id;
	if(id!=""&&id!=undefined){
		arr.push(id);
	}
  }
  return arr.join(',');
}