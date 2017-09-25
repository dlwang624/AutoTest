var com = window.NameSpace || {};  
com.czy = {};
com.czy.testpagenum = 1;
//浏览器加载绑定
$(function(){
	com.czy.testpagenum = $("#steptable tr[id^='row']").length;
	$("#addstep").click(function(){
		var stepcount = $("#stepcount").val();
		//一个步骤和多个步骤不同处理
		var data = [];
		for(var i=0;i<stepcount;i++){
			data[i]={num:com.czy.testpagenum}
			com.czy.testpagenum++;
		}
		$('#myTemplate').tmpl(data).appendTo('#steptable');
	});
	//提交前验证是否符合提交规范
	$("#testsubmit").click(function(){
		var notNull = /\s+/g;
		var testname = $("input[name='testname']").val();
		var names = $("input[name$='.name']");
		var descriptions = $("input[name$='.description']");
		var msg = "";
		var flag = true;
		if(testname.replace(notNull, "").length == 0){
			flag = false;
			msg = "用例名不能为空!";
			showMSG(msg);
			$("input[name='testname']").addClass("fontred");
			return;
		}
		$(names).each(function(){
			var namesVal = $(this).val();
			if(namesVal.replace(notNull, "").length == 0){
				flag = false;
				msg = "定位符不能为空!";
				$(this).addClass("fontred");
				return false;
			}
		});
		if(flag){
			$(descriptions).each(function(){
				var descVal = $(this).val();
				if(descVal.replace(notNull, "").length == 0){
					flag = false;
					msg = "定位符描述不能为空!";
					$(this).addClass("fontred");
					return false;
				}
			});
		}
		
		if(flag){
			$("#addtest").submit();
		}else{
			showMSG(msg);
		}
	});
	
	//返回事件
	$("#back").click(function(){
		window.location.href="project/main.htm";
	});
	
	//绑定删除空步骤事件
	$("#delNullStep").click(function(){
		var nameElements = $("input[name$='.name']");
		var descElements = $("input[name$='.description']");
		var flag = false;
		for(var i=0;i<nameElements.length;i++){
			if(nameElements[i].value==""&&descElements[i].value==""){
				$(nameElements[i]).parent().parent().remove();
				flag = true;
			}
		}
		if(flag){
			refreshRowNum();
		}
	});
})


//删除浏览器上的对应的元素,不是初次加载
function delNull(element){
	if(!confirm('你确定要删除?')){
	  return false;
	}else{
		$(element).parent().parent().remove();
		refreshRowNum();
	}
}

//插入指定行
function insertStep(btn){
	var rowID = $(btn).parents("tr[id^='row']").attr("id");
	var num = rowID.substring(3);
	var data = [{num:num}];
	$('#myTemplate').tmpl(data).insertBefore("#"+rowID);
	refreshRowNum();
}

//刷新行号
function refreshRowNum(){
	var rows = $("th[scope='row']");
	var rownum = rows.length;
	var j = "";
	$(rows).each(function(i,ele){
		$(this).html(i+1);
		var eles = $(this).parent().attr("id","row"+i).find("select,input");
		$(eles).each(function(index,ele){
			var className = $(this).attr("CLASS");
			if(className=="sync"){
				var nameValue = $(this).attr("name");
				var z = nameValue.indexOf("[");
				j = nameValue.substring(z+1,z+2);
				var begin = nameValue.substring(0,z+1);
				var end = nameValue.substring(z+2);
				$(this).attr("name",begin+i+end);
			}
		});
	});
	com.czy.testpagenum = rownum;
}

//错误时文字变红色
function checkFontColor(element){
	//错误时文字变红色
	$(element).focus(function(){
		$(this).removeClass("fontred");
	});
}

//非空判断
function showMSG(msg){
	alert(msg);
}
function isNull(str){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}