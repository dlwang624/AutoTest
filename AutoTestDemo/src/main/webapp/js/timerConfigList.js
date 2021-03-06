$(function(){
	$("#back").click(function(){
		window.location.href="timer/timer.htm";
	});
	$("#serverTimer").click(function(){
		var viewname = $("#viewname").val();
		$.ajax({
		  type: 'GET',
		  url: "timer/serverTimerStart.htm",
		  dataType: "json",
		  success: function(data){
			  var list = data.list;
			  var msg = "";
			  for(var i=0;i<list.length;i++){
				  msg += list[i].msg +"\n";
			  }
			  if(msg!=""&&msg!=undefined){
				layer.confirm(msg+"\n是否刷新页面", {
					btn: ['是','否'] //按钮
				}, function(){
					if(viewname=="userconfig"){
						window.location.href="timer/userConfig.htm";
					}else{
						window.location.href="timer/allConfig.htm";
					}
				}, function(){
					
				});
			  }else{
				layer.confirm("当前服务器没有计划可启动,是否刷新页面", {
					btn: ['是','否'] //按钮
				}, function(){
					if(viewname=="userconfig"){
						window.location.href="timer/userConfig.htm";
					}else{
						window.location.href="timer/allConfig.htm";
					}
				}, function(){
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
	});
});
function selTimer(id){
	window.location.href="timer/selConfig.htm?id="+id+"&&viewname="+$("#viewname").val();
}
function onTimerTest(id,ele){
	$(ele).attr("disabled","true");
	var btnVal = $(ele).val();
	var method = "";
	if(btnVal=="启动"){
		method = "start";
	}else{
		method = "stop";
	}
	$.ajax({
	  type: 'GET',
	  url: "timer/"+method+".htm?timerID="+id,
	  dataType: "json",
	  success: function(data){
		  layer.alert(data.msg);
		  if(btnVal=="启动"){
				$(ele).val("停止");
			}else{
				$(ele).val("启动");
			}
		  $(ele).removeAttr("disabled");
	  },
	  error: function (xhr, status, text) {
		  $(ele).removeAttr("disabled");
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