function selTrain(id){
	window.location.href="maindata/showtrain.htm?id="+id;
}
function delTrain(title,id){
	layer.confirm("是否删除[<span class='red'>"+title+"</span>]培训", {
		  btn: ['确定', '取消'] //可以无限个按钮
		}, function(){
			window.location.href="maindata/deltrain.htm?id="+id;
			layer.closeAll('dialog');
		}, function(){
			layer.closeAll('dialog');
		});
}
$(function(){
	$("#addTrain").click(function(){
		window.location.href="maindata/addTrain.htm";
	});
});

