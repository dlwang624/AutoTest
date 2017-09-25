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
	$("#back").click(function(){
		window.location.href="servertools/tools.htm?type="+$("#tooltype").val();
	});
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
			$("#checkClass").html('<div class="mt10"><div class="fl w150 tr mr10 h30">工具</div><div class="fl mr10 h30">:</div><div class="fl"><div><input style="cursor: pointer;" type="file" name="tl"></div></div></div><div class="clearfix"></div>');
			return;
		}
		if(_class == 1){
			var url = $("input[name='url']").val();
			var strRegex = '^((https|http|ftp|rtsp|mms)?://)';
			var re=new RegExp(strRegex);
			if (re.test(url)) { 
				$("#checkClass").html('<input style="display:none;" type="file" name="tl"><div class="mt10"><div class="fl w150 tr mr10 h30">链接</div><div class="fl mr10 h30">:</div><div class="fl"><input id="url" onblur="urlchange()" type="text" value="'+url+'" class="form-control" placeholder="请输入工具链接"></div></div><div class="clearfix"></div>');
			}else{
				$("#checkClass").html('<input style="display:none;" type="file" name="tl"><div class="mt10"><div class="fl w150 tr mr10 h30">链接</div><div class="fl mr10 h30">:</div><div class="fl"><input id="url" onblur="urlchange()" type="text" value="" class="form-control" placeholder="请输入工具链接"></div></div><div class="clearfix"></div>');
			}
			return;
		}
		$("#checkClass").html("");
	});
	$("#updateTool").click(function(){
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
		$("#back").attr("disabled","true");
		$(this).attr("disabled","true");
		$(this).html('<i class="fa fa-spinner fa-spin"></i>请等待...');
	});
});

function removehelp(name,id){
	var helps = $('input[name="studyurl"]').val();
	var hs = helps.split(",");
	var h = "";
	for(var i=0;i<hs.length;i++){
		if(hs[i]==name){
			$(id).remove();
		}else{
			h += hs[i] + ",";
		}
	}
	$('input[name="studyurl"]').val(h.substring(0,h.length-1));
}

function removetool(){
	$('input[name="url"]').val("");
}

function urlchange(){
	var url = $("#url").val();
	$("input[name='url']").val(url);
}