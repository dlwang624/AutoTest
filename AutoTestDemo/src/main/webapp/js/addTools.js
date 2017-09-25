$(function(){
	var _msg = $("#msg").val();
	if(_msg!=""&&undefined!=msg&&null!=msg){
		if(_msg=="上传成功"){
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
	$('input[name="name"]').blur(function(){
		$.ajax({
			  type: 'GET',
			  url: "servertools/checkname.htm?name="+$(this).val(),
			  dataType: "json",
			  success: function(data){
				  if(data.checkname){
					  layer.tips('工具名已被占用', $("input[name='name']"),{tips:[2,'#C00']});
					  checknameflag = true;
				  }else{
					  checknameflag = false;
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
	$('select[name="toolclass"]').change(function(){
		var _class = $(this).val();
		if(_class == 0){
			$("#checkClass").html('<div class="mt10"><div class="fl w150 tr mr10 h30">工具</div><div class="fl mr10 h30">:</div><div class="fl"><input style="cursor: pointer;" type="file" name="tl" id="filename"></div></div><div class="clearfix"></div>');
			return;
		}
		if(_class == 1){
			$("#checkClass").html('<input style="display:none;" type="file" name="tl"><div class="mt10"><div class="fl w150 tr mr10 h30">链接</div><div class="fl mr10 h30">:</div><div class="fl"><input name="url" type="text" class="form-control" placeholder="请输入工具链接"></div></div><div class="clearfix"></div>');
			return;
		}
		$("#checkClass").html("");
	});
	$("#addTool").click(function(){
		var name = $("input[name='name']").val();
		var type = $("input[name='type']").val();
		var toolclass = $("input[name='toolclass']").val();
		if(""==name){
			layer.msg("工具名不能为空", {
	            icon: 2,
	        });
			return false;
		}
		if(checknameflag){
			layer.msg("工具名重复使用", {
	            icon: 2,
	        });
			return false;
		}
		if(""==type||"工具所属范围"==type){
			layer.msg("范围不能为空", {
	            icon: 2,
	        });	
			return false;
		}
		if(""==toolclass||"工具所属种类"==toolclass){
			layer.msg("种类不能为空", {
	            icon: 2,
	        });
			return false;
		}
		$("#qcdbForm").submit();
		$(this).attr("disabled","true");
		$(this).html('<i class="fa fa-spinner fa-spin"></i>请等待...');
	});
});