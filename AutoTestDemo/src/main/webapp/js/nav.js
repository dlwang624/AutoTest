
var _defaultViewWidth = document.body.clientWidth;
var _showViewWidth = _defaultViewWidth;

function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

$(function(){
//	var msg = $("#alertMSG").val();
//	if(''!=msg&&undefined!=msg&&null!=msg){
//		layer.alert(msg);
//	}
	$("#gtestbynq").click(function(){
		var testname = $("input[name='likename']").val();
		if(testname==undefined||testname==""){
			layer.msg("请输入用例名");
			return false;
		}
		$("#selTestByQAndP").submit();
	});
	$("#layerfull").click(function(){
		var _class = $("body").attr("class");
		var flag;
		if(""!=_class&&undefined!=_class&&null!=_class){
			flag = true;
			_defaultViewWidth = $("#sumReportPanel").width();
		}else{
			flag = false;
			_showViewWidth = $("#sumReportPanel").width();
		}
		$.ajax({
			type:"POST",
			url:"user/layoutfull.htm",
			data:{"full":flag},
			success:function(){
				if(sumreport.data!=undefined&&sumreport.data.day.length!=0){
					$("#bugview").html("<div id='chartmain'></div>");
					var chartmain = $("#chartmain");
					var data = sumreport.data;
					if(chartmain!=null&&undefined!=chartmain){
						if(flag){
							$(chartmain).width(_showViewWidth);
						}else{
							$(chartmain).width(_defaultViewWidth);
						}
						$(chartmain).height(400);
					}
					var myChart = echarts.init(document.getElementById('chartmain'));
					var option = {
						    title: {
						        text: 'Bug趋势图'
						    },
						    tooltip : {
						        trigger: 'axis',
						        axisPointer: {
						            type: 'cross',
						            label: {
						                backgroundColor: '#6a7985'
						            }
						        }
						    },
						    legend: {
						        data:['Bug数']
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    xAxis : [
						        {
						            type : 'category',
						            boundaryGap : false,
						            data : data.day
						        }
						    ],
						    yAxis : [
						        {
						            type : 'value'
						        }
						    ],
						    series : [
						        {
						            name:'Bug数',
						            type:'line',
						            stack: '总量',
						            areaStyle: {normal: {}},
						            data:data.bug
						        }
						    ]
						};
					myChart.setOption(option);
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
function toBrowse(id){
	var url = "";
	switch(id){
		case 1:
			url = "file/page.htm";
			break;
		case 2:
			url = "project/tobackup.htm";
			break;
		case 3:
			url = "CMS/allTest.htm";
			break;
		case 4:
			url = "Exec/project.htm";
			break;
		case 5:
			url = "Exec/projectlist.htm";
			break;
		case 6:
			url = "project/config.htm";
			break;
		case 7:
			url = "project/configList.htm";
			break;
		case 8:
			url = "count/main.htm";
			break;
		case 9:
			url = "timer/timer.htm";
			break;
		case 10:
			url = "servertools/addtool.htm";
			break;
		case 11:
			url = "servertools/tools.htm?type=0";
			break;
		case 12:
			url = "servertools/tools.htm?type=1";
			break;
		case 13:
			url = "servertools/tools.htm?type=2";
			break;
		case 14:
			url = "count/testreport.htm";
			break;
		case 15:
			url = "switchambient/ambient.htm";
			break;
		case 16:
			url = "maindata/addsynamic.htm";
			break;
		case 17:
			url = "maindata/train.htm";
			break;
		default:
			url = "Exec/logout.htm";
			break;
	}
	window.location.href=url;
}	
function sidebarRemember(rt,ele){
	var collapse = false;
	var flagVal = $(ele).attr("aria-expanded");
	if(flagVal=="false"){
		collapse = true;
	}else{
		collapse = false;
	}
	$.ajax({
		type:"POST",
		url:"user/sidebar.htm",
		data:{"rt":rt,"collapse":collapse},
		success:function(data){
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
function logout(){
	window.location.href="Exec/logout.htm";
}