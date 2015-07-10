/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omni.fansoffury;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TestEchoWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(TestEchoWebSocketHandler.class);
	WebSocketSession session;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Session established with server " + session.getId());
		this.session = session;
		try {
			sendMessage("Hi");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		logger.info("Message received from server: " + message);
		logger.info("Sending back message: " + message + " h");
		session.sendMessage(new TextMessage(message + " h"));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		logger.error("Socket threw error", e);
		session.close(CloseStatus.SERVER_ERROR);
	}
	
	public void sendMessage(String message) throws IOException {
		session.sendMessage(new TextMessage(message));
	}

}
