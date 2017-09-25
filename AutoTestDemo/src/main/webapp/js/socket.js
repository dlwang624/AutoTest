var com = window.NameSpace || {};  
com.czy = {};
com.czy.websocket = function(showContentId,ip){
	var socket = null;
	var localAddress = $(ip).val();
	
	this.init = (function(){
		//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
		    socket = new WebSocket("ws://"+localAddress+":8080/AutoTestDemo/ws.htm");
		}
		else {
			setMessageInnerHTML(showContentId,'当前浏览器 Not support websocket');
		}
		
		//连接发生错误的回调方法
		socket.onerror = function () {
		    setMessageInnerHTML(showContentId,"WebSocket连接发生错误");
		};
		
		//连接成功建立的回调方法
		socket.onopen = function () {
		    setMessageInnerHTML(showContentId,"WebSocket连接成功");
		}
		
		//接收到消息的回调方法
		socket.onmessage = function (event) {
		    setMessageInnerHTML(showContentId,event.data);
		}
		
		//连接关闭的回调方法
		socket.onclose = function () {
		    setMessageInnerHTML(showContentId,"测试结束");
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
com.czy.websocket.flag = true;
//将消息显示在网页上
function setMessageInnerHTML(id,content) {
	var ele = $(id);
    var eleTagName = $(ele).prop("tagName");
    com.czy.websocket.flag = content=="WebSocket连接发生错误"?false:true;
    if(eleTagName=="INPUT"){
    	
    }else if(eleTagName=="TEXTAREA"){
    	
    }else{
    	if(content=="WebSocket连接成功"){
    		$(ele).html("测试开始<br/>");
    	}
    	if(content=="测试结束"){
    		if(com.czy.websocket.flag){
    			$("#reportLink").show();
    		}
    	}

    	if(content!="WebSocket连接成功"){
    		var index = content.indexOf("@@@");
    		if(index>0){
    			$(ele).append("<p style='color:red;'>\""+content.replace("@@@","")+"</p>");
        	}else{
        		$(ele).append(content + "<br/>");
        	}
    	}
    }
}

function showDate(){//将当前时间转换成yyyymmdd格式
	var mydate = new Date();
	var str = "" + mydate.getFullYear();
	var mm = mydate.getMonth()+1
	if(mydate.getMonth()>9){
		str += mm;
	}
	else{
		str += "0" + mm;
	}
	if(mydate.getDate()>9){
		str += mydate.getDate();
	}
	else{
		str += "0" + mydate.getDate();
	}
	return str;
}

