package com.omni.fansoffury;

import com.omni.fansoffury.player.DataLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.omni.fansoffury.socket.handler.EegSocketListener;
import com.omni.fansoffury.socket.handler.FanControllerSocketListener;
import com.omni.fansoffury.socket.handler.ScoreSocketListener;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableWebMvc
@EnableWebSocket
@EnableScheduling
//@SpringBootApplication
public class FansOfFuryWebApplication extends WebMvcAutoConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(fanControllerSocketListener(), "/ws/fancontroller").setAllowedOrigins("*").withSockJS();
		registry.addHandler(scoreSocketListener(), "/ws/score").setAllowedOrigins("*").withSockJS();
		registry.addHandler(eegSocketListener(), "/ws/eeg").setAllowedOrigins("*").withSockJS();
	}

//	@Override
//	protected SpringApplicationBuilder configure(
//			SpringApplicationBuilder application) {
//		
//		return application.sources(FansOfFuryWebApplication.class);
//	}

	@Bean
	public FanControllerSocketListener fanControllerSocketListener() {
		return new FanControllerSocketListener();
	}
	
	@Bean
	public ScoreSocketListener scoreSocketListener() {
		return new ScoreSocketListener();
	}
	
	@Bean
	public EegSocketListener eegSocketListener() {
		return new EegSocketListener();
	}

	@Bean
	public DataLogger dataLogger() {
		return new DataLogger();
	}

//	@Bean 
//	public ServletListenerRegistrationBean servletListener() {
//	    return new ServletListenerRegistrationBean(new ApplicationStartupListener());
//	}

	public static void main(String[] args) {
		SpringApplication.run(FansOfFuryWebApplication.class, args);
	}

}
