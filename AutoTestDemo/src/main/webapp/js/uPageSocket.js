var com = window.NameSpace || {};  
com.czy = {};
com.czy.websocket = function(ip){
	var socket = null;
	var localAddress = $(ip).val();
	
	this.init = (function(){
		//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
		    socket = new WebSocket("ws://"+localAddress+":8080/AutoTestDemo/ws.htm");
		}
		
		//连接成功建立的回调方法
		socket.onopen = function () {
			lock();
		}
		
		//连接发生错误的回调方法
		socket.onerror = function () {
			unlocked();
		};
		
		//连接关闭的回调方法
		socket.onclose = function () {
			unlocked();
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
}

//锁定用例
function lock(){
	var id = $("input[name='id']").val();
	$.ajax({
		  type: 'GET',
		  url: "CMS/lock.htm?id="+id,
		  success: function(data){
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

//解锁用例
function unlocked(){
	var id = $("input[name='id']").val();
	$.ajax({
		  type: 'GET',
		  url: "CMS/unlocked.htm?id="+id,
		  success: function(data){
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

