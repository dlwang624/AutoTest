var QCwebsocket = function(showContentId,ip){
	var socket = null;
	var localAddress = $(ip).val();
	
	this.init = (function(){
		//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
		    socket = new WebSocket("ws://"+localAddress+":8080/AutoTestDemo/ws.htm");
		}
		else {
			showMessageInnerHTML(showContentId,'当前浏览器 Not support websocket');
		}
		
		//连接发生错误的回调方法
		socket.onerror = function () {
		    showMessageInnerHTML(showContentId,"WebSocket连接发生错误");
		};
		
		//连接成功建立的回调方法
		socket.onopen = function () {
		    showMessageInnerHTML(showContentId,"WebSocket连接成功");
		}
		
		//接收到消息的回调方法
		socket.onmessage = function (event) {
		    showMessageInnerHTML(showContentId,event.data);
		}
		
		//连接关闭的回调方法
		socket.onclose = function () {
		    showMessageInnerHTML(showContentId,"<p>验证结束</p>");
		}
		
		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		window.onbeforeunload = function () {
			socket.close();
		}
		return socket;
	});
	
	//关闭WebSocket连接
	this.close = (function() {
	    socket.close();
	});
	
	//发送消息
	this.send = (function(id) {
	    var message = $(id).val();
	    socket.send(message);
	});
}
//将消息显示在网页上
function showMessageInnerHTML(id,content) {
	var ele = $(id);
    var eleTagName = $(ele).prop("tagName");
    if(eleTagName=="INPUT"){
    	
    }else if(eleTagName=="TEXTAREA"){
    	
    }else{
    	if(content!="WebSocket连接成功"){
    		//错误标识
        	if(content.indexOf("@@@")>0){
        		$(ele).append("<p style='color:red;'>\""+content.substring(4)+"</p>");
        	}else{
        		if(content=="<p>验证结束</p>"){
            		if($(ele).html()=="<p>验证开始</p>"){
            			$(ele).append("<p style='color:green;'>验证通过</p>");
            		}
            		$(ele).append("<p>"+content + "</p>");
            	}
        	}
    	}
    	if(content=="WebSocket连接成功"){
    		$(ele).html("<p>验证开始</p>");
    	}
    }
}

