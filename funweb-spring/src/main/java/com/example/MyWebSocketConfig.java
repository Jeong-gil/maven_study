package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.websocket.SimpleChatTextWebSocketHandler;


@Configuration
public class MyWebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private SimpleChatTextWebSocketHandler simpleChatHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// "/simpleChat" 경로는 소켓연결을 위한 ws 웹소켓 프로토콜 연결 경로가 됨!
		// ws 또는 wss 프로토콜을 이용해 아래 경로로 소켓서버에 접속해야 연결됨.
		registry.addHandler(simpleChatHandler, "/simpleChat");
	}
	
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(2);
		taskScheduler.setThreadNamePrefix("scheduled-task-");
		taskScheduler.setDaemon(true);
		return taskScheduler;
	}

}




