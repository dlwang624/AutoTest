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
			  url: "maindata/checktrain.htm",
			  data:{'title':$(this).val()},
			  dataType: "json",
			  success: function(data){
				  if(data.flag){
					  checknameflag = false;
				  }else{
					  layer.tips('培训课题已被占用', $("input[name='title']"),{tips:[2,'#C00']});
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
	$("#uptrain").click(function(){
		var title = $("input[name='title']").val();
		if(""==title){
			layer.msg("培训课题不能为空", {
	            icon: 2,
	        });
			return false;
		}
		var author = $("input[name='author']").val();
		if(""==author){
			layer.msg("培训讲师不能为空", {
	            icon: 2,
	        });
			return false;
		}
		if(checknameflag){
			layer.msg("培训课题重复使用", {
	            icon: 2,
	        });
			return false;
		}
		$("#trainForm").submit();
		$(this).attr("disabled","true");
		$(this).html('<i class="fa fa-spinner fa-spin"></i>请等待...');
	});
	$("#back").click(function(){
		window.location.href="maindata/trainll.htm";
	});
});
function removeppt(name,did){
	var id = $("input[name='id']").val();
	$.ajax({
	  type: 'POST',
	  url: "maindata/deltrainppt.htm",
	  data:{'id':id,'pptname':name},
	  dataType: "json",
	  success: function(data){
		  if(data.flag){
			  $(did).remove();
			  	var temp = $('input[name="temp"]').val();
				var hs = temp.split(",");
				var h = "";
				for(var i=0;i<hs.length;i++){
					if(trim(hs[i])!=name){
						h += hs[i] + ",";
					}
				}
				$('input[name="temp"]').val(h.substring(0,h.length-1));
			  layer.msg("["+name+"]课件删除成功", {
		            icon: 1,
		        });
		  }else{
			  layer.msg("["+name+"]课件删除失败", {
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
function removeword(name,did){
	var id = $("input[name='id']").val();
	$.ajax({
	  type: 'POST',
	  url: "maindata/deltrainword.htm",
	  data:{'id':id,'wordname':name},
	  dataType: "json",
	  success: function(data){
		  if(data.flag){
			  $(did).remove();
			  	var fileurl = $('input[name="fileurl"]').val();
				var hs = fileurl.split(",");
				var h = "";
				for(var i=0;i<hs.length;i++){
					if(trim(hs[i])!=name){
						h += hs[i] + ",";
					}
				}
				$('input[name="fileurl"]').val(h.substring(0,h.length-1));
			  layer.msg("["+name+"]课件删除成功", {
		            icon: 1,
		        });
		  }else{
			  layer.msg("["+name+"]课件删除失败", {
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
