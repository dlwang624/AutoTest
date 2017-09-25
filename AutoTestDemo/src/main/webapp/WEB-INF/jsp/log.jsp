<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}">
<meta charset="utf-8">
<title>log</title>
<style>
	html,body
	{
		height:100%;
		width:100%;
	}
</style>
</head>

<body>
    <div id="log-container" style="height: 100%; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
        <div>
        </div>
        <button id="myin" onclick="myinclick()">in</button>
    </div>
</body>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
	    // 指定websocket路径
// 	    var websocket = new WebSocket('ws://websocket:8080/AutoTestDemo/ws.htm');
	  	//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
		    socket = new WebSocket("ws://localhost:8080/AutoTestDemo/ws.htm");
		}
		else {
		    alert('当前浏览器 Not support websocket')
		}
		
		//连接发生错误的回调方法
		socket.onerror = function () {
		    setMessageInnerHTML("WebSocket连接发生错误");
		};
		
		//连接成功建立的回调方法
		socket.onopen = function () {
		    setMessageInnerHTML("WebSocket连接成功");
		}
		
		//接收到消息的回调方法
// 		socket.onmessage = function (event) {
// 		    setMessageInnerHTML(event.data);
// 		}
		
		//连接关闭的回调方法
		socket.onclose = function () {
		    setMessageInnerHTML("socket连接关闭");
		}
		
		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		window.onbeforeunload = function () {
		    closeWebSocket();
		}
	    socket.onmessage = function(event) {
	    	alert(event.data);
	        // 接收服务端的实时日志并添加到HTML页面中
	        $("#log-container div").append(event.data + "<p> </p>");
	        // 滚动条滚动到最低部
	        $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
	    };
	});
	//将消息显示在网页上
	function setMessageInnerHTML(content) {
	    document.getElementById("log-container").innerHTML += content + '<br/>';
	}
	
	//关闭WebSocket连接
	function closeWebSocket() {
	    socket.close();
	}
	
	//发送消息
	function send() {
	    var message = document.getElementById().value;
	    socket.send(message);
	}
	
	function myinclick(){
		$("#myin").click(function(){
			$.ajax({
				  type: 'GET',
				  url: "Exec/websocket.htm",
				  success: function(data){
					  
				  },
				  error: function (xhr, status, text) {
			          switch (status) {
			             case 404:
			                 alert('File not found');
			                 break;
			             case 500:
			                 alert('Server error');
			                 break;
			             case 0:
			                 alert('Request aborted');
			                 break;
			             default:
			                 alert('Unknown error: ' + status + " " + text);
			            }
			        }
			  });
		})
	}
</script>
</body>
</html>