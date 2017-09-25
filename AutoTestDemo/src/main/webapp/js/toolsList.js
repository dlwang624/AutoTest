$(function(){
	var msg = $("#msg").val();
	if(undefined!=msg&&null!=msg&&""!=msg){
		if(msg=="删除成功"){
			layer.alert(msg, {
	            icon: 1,
	        });
		}else{
			layer.alert(msg, {
	            icon: 2,
	        });
		}
	}
})

function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x]; 
        // layer.alert(opt.name)        
        temp.appendChild(opt);        
    }    
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}

function getDataFile(path,id,filename){
	$.ajax({
		type:"POST",
		url:"file/downfile.htm",
		data:{'path':path,'id':id,'filename':filename},
		dataType: "json",
		success:function(date){
			window.location.href = date.url;
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

function useOpen(id){
	$.ajax({
		type:"GET",
		url:"servertools/usecount.htm?id="+id,
		dataType: "json",
		success:function(date){
			window.open(date.urlOpen);
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
function removetool(name,id,type){
	layer.confirm('您确定要删除<span class="red">'+name+'</span>吗?', {
	  btn: ['确定', '取消'] //可以无限个按钮
	}, function(){
		window.location.href="servertools/remove.htm?id="+id+"&&type="+type;
	}, function(){
		
	});
}
function show(id){
	window.location.href="servertools/show.htm?id="+id;
}