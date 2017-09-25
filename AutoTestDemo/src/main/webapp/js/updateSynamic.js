$(function(){
	var _msg = $("#msg").val();
	if(_msg!=""&&undefined!=msg&&null!=msg){
		if(_msg=="更新成功"){
			layer.msg(_msg, {
	            icon: 1,
	        });
		}else{
			layer.msg(_msg, {
	            icon: 2,
	        });
		}
	}
	var checknameflag = false;
	$('input[name="title"]').blur(function(){
		var title = $("#titleTemp").val();
		if(title!=$(this).val()){
			$.ajax({
			  type: 'POST',
			  url: "maindata/checksynamic.htm",
			  data:{'title':$(this).val()},
			  dataType: "json",
			  success: function(data){
				  if(data.flag){
					  checknameflag = false;
				  }else{
					  layer.tips('动态标题已被占用', $("input[name='title']"),{tips:[2,'#C00']});
					  checknameflag = true;
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
	});
	$("#upworksynamic").click(function(){
		var title = $("input[name='title']").val();
		if(""==title){
			layer.msg("动态标题不能为空", {
	            icon: 2,
	        });
			return false;
		}
		if(checknameflag){
			layer.msg("动态标题重复使用", {
	            icon: 2,
	        });
			return false;
		}
		$("#dynamicForm").submit();
		$(this).attr("disabled","true");
		$(this).html('<i class="fa fa-spinner fa-spin"></i>请等待...');
	});
	$("#back").click(function(){
		window.location.href="maindata/all.htm";
	});
});