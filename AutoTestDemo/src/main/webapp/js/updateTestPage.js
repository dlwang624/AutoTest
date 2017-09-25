//var com = window.NameSpace || {};  
//com.czy = {};
//com.czy.testpagenum = 1;

jQuery(document).ready(function ($) {
    if (window.history && window.history.pushState) {
        $(window).on('popstate', function () {
            var hashLocation = location.hash;
            var hashSplit = hashLocation.split("/");
            var hashName = hashSplit[1];
            if (hashName !== '') {
                var hash = window.location.hash;
                if (hash === '') {
                	unlocked();
                	window.location ="CMS/allTest.htm"
                }
            }
        });
        var url = document.URL;
        url = url.substring(url.lastIndexOf("/"),url.length);
        window.history.pushState('forward', null, './CMS'+url);
    }
});

//浏览器加载绑定
$(function(){
	new com.czy.websocket("#localAddress").init();
//	com.czy.testpagenum = $("#steptable tr[id^='row']").length;
//	$("#addstep").click(function(){
//		var stepcount = $("#stepcount").val();
//		//一个步骤和多个步骤不同处理
//		var data = [];
//		for(var i=0;i<stepcount;i++){
//			data[i]={num:com.czy.testpagenum}
//			com.czy.testpagenum++;
//		}
//		$('#myTemplate').tmpl(data).appendTo('#steptable');
//	});
	//初始化绑定删除事件
//	bundleDelStep();
	//提交前验证是否符合提交规范
	//下载数据
	$("#testdown").click(function(){
		$("#getDescData").submit();
	});
	//上传数据
	$("#testup").click(function(){
		$("#upfileData").click();
	});
	$("#upfileData").change(function(){
		$("#upDescData").submit();
	});
	$("#testsubmit").click(function(){
//		var notNull = "/\s+/g";
//		var testname = $("input[name='testname']").val();
//		var names = $("input[name$='.name']");
////		var descriptions = $("input[name$='.description']");
//		var msg = "";
//		var flag = true;
//		if(testname.replace(notNull, "").length == 0){
//			flag = false;
//			msg = "用例名不能为空!";
//			showMSG(msg);
//			$("input[name='testname']").addClass("fontred");
//			return;
//		}
//		$(names).each(function(){
//			var namesVal = $(this).val();
//			if(namesVal.replace(notNull, "").length == 0){
//				flag = false;
//				msg = "定位符不能为空!";
//				$(this).addClass("fontred");
//				return false;
//			}
//		});
//		if(flag){
//			$(descriptions).each(function(){
//				var descVal = $(this).val();
//				if(descVal.replace(notNull, "").length == 0){
//					flag = false;
//					msg = "定位符描述不能为空!";
//					$(this).addClass("fontred");
//					return false;
//				}
//			});
//		}
		
//		if(flag){
		$.ajax({
			  type: 'POST',
			  url: "CMS/updateTest.htm",
			  data:$('#updatetest').serialize(),
			  dataType: "json",
			  contentType: "application/x-www-form-urlencoded; charset=utf-8",
			  success: function(data){
			  	layer.alert(data.msg);
				$(".sync").each(function(){
					var textValue = $(this).val();
					$(this).attr("data-value",textValue);
				});
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
//		}else{
//			showMSG(msg);
//		}
	});
	//绑定删除空步骤事件
//	$("#delNullStep").click(function(){
//		var nameElements = $("input[name$='.name']");
//		var descElements = $("input[name$='.description']");
//		var flag = false;
//		for(var i=0;i<nameElements.length;i++){
//			if(nameElements[i].value==""&&descElements[i].value==""){
//				$(nameElements[i]).parent().parent().remove();
//				flag = true;
//			}
//		}
//		if(flag){
//			refreshRowNum();
//		}
//	});
	
	
	//返回事件
	$("#back").click(function(){
		var viewname = $("#viewname").val();
		var nameFlag = $("input[name='testname']").val()==$("input[name='testname']").attr("data-value")?true:false;
		var textFlag = true;
		$(".sync").each(function(){
			var textValue = $(this).val();
			var originalValue = $(this).attr("data-value");
			if(textValue!=originalValue){
				textFlag = false;
				return false;
			}
		});
		if(nameFlag&&textFlag){
			window.location.href="CMS/updateTestValue.htm?id=" + $("input[name='id']").val()+"&&viewname="+viewname+"&&statusflag="+$("#statusflag").val();
		}else{
			layer.confirm('确定要放弃此次编辑吗?', {
			  btn: ['确定', '取消'] //可以无限个按钮
			}, function(){
				window.location.href="CMS/updateTestValue.htm?id=" + $("input[name='id']").val()+"&&viewname="+viewname+"&&statusflag="+$("#statusflag").val();
			}, function(){
				
			});
		}
	});
})
//删除事件
//function bundleDelStep(){
//	//绑定删除事件
//	$("input[name='delstep']").each(function(index,ele){
//		$(this).click(function(){
//			var id = $(this).attr("data-id");
//			var element = $(this);
//			if(id!=null&&id!=""){
//				if(!confirm('确定要删除?')){
//					  return false;
//				}else{
//					$.ajax({
//					  type: 'GET',
//					  url: "CMS/delTest.htm?id="+id,
//					  success: function(data){
//						  console.log(data);
//						  $(element).parent().parent().remove();
//						  refreshRowNum();
//					  },
//					  error: function (xhr, status, text) {
//				          switch (status) {
//				             case 404:
//				                 console.log("File not found");
//				                 break;
//				             case 500:
//				                 console.log("Server error");
//				                 break;
//				             case 0:
//				                 console.log("Request aborted");
//				                 break;
//				             default:
//				            	 var msg = 'Unknown error: ' + status + " " + text + "\n"
//				            	 		+ "readyState: " + xhr.readyState + "\n"
//				            	 		+ "responseText: "+ xhr.responseText + "\n"
//				            	 		+ "status: " + xhr.status + "\n"
//				            	 console.log(msg);
//				            }
//				        }
//					});
//				}
//			}
//		});
//	});
//}

//function checkFontColor(element){
//	//错误时文字变红色
//	$(element).focus(function(){
//		$(this).removeClass("fontred");
		//鼠标移动到单元格上对应行添加背景色
//			$(this).siblings().each(function(){
//				if($(this).attr("class")=="outbg"){
//					$(this).addClass("movebg");
//				}
//			});
//			layer.alert($(this).siblings("th .outbg").attr("class"));
//		});
//		.blur(function(){
//			$(this).siblings().each(function(){
//				if($(this).attr("class")=="outbg"){
//					$(this).removeClass("movebg");
//				}
//			});
//		});
//	});
//}
//删除浏览器上的对应的元素,不是初次加载
//function delNull(element){
//	if(!confirm('确定要删除?')){
//	  return false;
//	}else{
//		$(element).parent().parent().remove();
//		refreshRowNum();
//	}
//}
	
//插入指定行
//function insertStep(btn){
//	var rowID = $(btn).parents("tr[id^='row']").attr("id");
//	var num = rowID.substring(3);
//	var data = [{num:num}];
//	$('#myTemplate').tmpl(data).insertBefore("#"+rowID);
//	refreshRowNum();
//}
//刷新行号
//function refreshRowNum(){
//	var rows = $("th[scope='row']");
//	var rownum = rows.length;
//	var j = "";
//	$(rows).each(function(i,ele){
//		$(this).html(i+1);
//		var eles = $(this).parent().attr("id","row"+i).find("select,input");
//		$(eles).each(function(index,ele){
//			var className = $(this).attr("CLASS");
//			if(className=="sync"){
//				var nameValue = $(this).attr("name");
//				var z = nameValue.indexOf("[");
//				j = nameValue.substring(z+1,z+2);
//				var begin = nameValue.substring(0,z+1);
//				var end = nameValue.substring(z+2);
//				$(this).attr("name",begin+i+end);
//			}
//		});
//	});
//	com.czy.testpagenum = rownum;
//}
//非空判断
function showMSG(msg){
	layer.alert(msg);
}
function isNull(str){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}