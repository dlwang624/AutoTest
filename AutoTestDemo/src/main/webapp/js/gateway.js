$(function(){
	var msg = $("#msg").val();
	if(msg!=""&&undefined!=msg){
	    layer.alert(msg, {
            icon: 2,
        });
	}
	$("button[class='blue']").click(function(){
		var name = $("input[name='uname']").val();
		var password = $("input[name='upass']").val();
		var qcname = $("select[name='qcname']").val();
		if((name==""&&password=="")||(name==undefined && password==undefined)){
			layer.alert('用户名和密码不能为空!');
			return false;
		}
		if(name==""||name==undefined){
			layer.tips('用户名不能为空!', $("input[name='uname']"),{tips:[2,'#C00']});
			return false;
		}
		if(password==""||password==undefined){
			layer.tips('密码不能为空!', $("input[name='upass']"),{tips:[2,'#C00']});
			return false;
		}
		if(qcname=="请选择QC库"){
			layer.tips('请选择QC库!', $("select[name='qcname']"),{tips:[2,'#C00']});
			return false;
		}
	});
});