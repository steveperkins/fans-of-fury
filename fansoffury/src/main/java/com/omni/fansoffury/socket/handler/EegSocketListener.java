
package com.omni.fansoffury.socket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.omni.fansoffury.headset.service.BluetoothSocketService;
import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.event.EegChangedEvent;
import com.omni.fansoffury.player.PlayerService;
import com.sperkins.mindwave.event.AttentionEvent;
import com.sperkins.mindwave.event.Event;
import com.sperkins.mindwave.event.EventType;
import com.sperkins.mindwave.event.MeditationEvent;
import com.sperkins.mindwave.event.MindwaveEventListener;

/**
 * Handles websocket connections from clients interested in headset EEG changes
 * 
 * @author steve.perkins
 */
public class EegSocketListener extends TextWebSocketHandler implements MindwaveEventListener {

	private static Logger logger = LoggerFactory.getLogger(EegSocketListener.class);
	
	@Autowired
	private BluetoothSocketService bluetoothSocketService;
	
	@Autowired
	private PlayerService playerService;
	
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>(); 
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Opened new EEG session " + session.getId());
		sessions.add(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// EEG listeners shouldn't be sending any messages
		logger.debug("EEG listener sent message: {}", message.getPayload());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		logger.error("Transport error on session " + session.getId(), e);
		session.close(CloseStatus.PROTOCOL_ERROR);
	}
	
	public void broadcast(EegChangedEvent event) {
		for(WebSocketSession session: sessions) {
			if(null != session && session.isOpen()) {
				String json = new Gson().toJson(event);
				logger.debug("Sending EEG change event to session ID {}: {}", session.getId(), json);
				try {
					session.sendMessage(new TextMessage(json));
				} catch (IOException e) {
					logger.error("Could not send EEG change event to session ID " + session.getId(), e);
				}
			}
		}
	}
	
	@PostConstruct
	private void postConstruct() {
		bluetoothSocketService.addListener(this);
	}

	@Override
	public void onEvent(Event event) {
		if(EventType.ATTENTION.equals(event.getEventType()) || EventType.MEDITATION.equals(event.getEventType())) {
			Integer value = 0;
			if(EventType.ATTENTION.equals(event.getEventType())) value = ((AttentionEvent)event).getValue();
			else value = ((MeditationEvent)event).getValue();
			
			Player player = playerService.getPlayer(new Headset(event.getDeviceAddress()));
			if(null != player) {
				EegChangedEvent eegEvent = new EegChangedEvent(player, event.getEventType(), value);
				broadcast(eegEvent);
			} else logger.error("EEG event generated without a valid player (headset ID: " + event.getDeviceAddress() + ")");
		}
	}

	
}
