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
			alert("socket初始化失败");
		}
		
		//连接发生错误的回调方法
		socket.onerror = function () {
			alert("socket连接失败");
		};
		
		//连接成功建立的回调方法
		socket.onopen = function () {
		}
		
		//接收到消息的回调方法
		socket.onmessage = function (event) {
		    $(showContentId).html(event.data)
		}
		
		//连接关闭的回调方法
		socket.onclose = function () {
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

