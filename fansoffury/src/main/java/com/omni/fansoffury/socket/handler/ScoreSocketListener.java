
package com.omni.fansoffury.socket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.omni.fansoffury.model.event.ScoreChangedEvent;
import com.omni.fansoffury.player.ScoreListener;
import com.omni.fansoffury.player.ScoreService;

/**
 * Handles websocket connections from clients interested in score changes
 * 
 * @author steve.perkins
 */
@Service
public class ScoreSocketListener extends TextWebSocketHandler implements ScoreListener {

	private static Logger logger = LoggerFactory.getLogger(ScoreSocketListener.class);
	
	@Autowired
	private ScoreService scoreService;
	
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>(); 
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Opened new score session " + session.getId());
		sessions.add(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// Score listeners shouldn't be sending any messages
		logger.debug("Score listener sent message: {}", message.getPayload());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		logger.error("Transport error on session " + session.getId(), e);
		session.close(CloseStatus.PROTOCOL_ERROR);
		sessions.remove(session);
	}
	
	public void broadcast(ScoreChangedEvent event) {
		for(WebSocketSession session: sessions) {
			if(null != session && session.isOpen()) {
				String json = new Gson().toJson(event);
				logger.debug("Sending score change event to session ID {}: {}", session.getId(), json);
				try {
					session.sendMessage(new TextMessage(json));
				} catch (IOException e) {
					logger.error("Could not send score change event to session ID " + session.getId(), e);
				}
			}
		}
	}
	
	@Override
	public void scoreChanged(ScoreChangedEvent event) {
		broadcast(event);
	}
	
	@PostConstruct
	private void postConstruct() {
		scoreService.addListener(this);
	}
	
}
