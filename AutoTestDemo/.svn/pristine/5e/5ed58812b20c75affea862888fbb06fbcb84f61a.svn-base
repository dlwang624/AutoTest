package org.czy.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(), "/ws.htm").addInterceptors(new MyHandShake());

		registry.addHandler(myHandler(), "/ws/sockjs").addInterceptors(new MyHandShake()).withSockJS();
	}
	
	@Bean
    public WebsocketHandler myHandler() {
        return new WebsocketHandler();
    }

}
