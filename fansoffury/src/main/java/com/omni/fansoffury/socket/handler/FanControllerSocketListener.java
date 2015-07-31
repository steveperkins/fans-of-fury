
package com.omni.fansoffury.socket.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.event.ScoreChangedEvent;
import com.omni.fansoffury.model.message.DeviceControllerRegistrationEvent;
import com.omni.fansoffury.model.message.DeviceScoreChangeEvent;
import com.omni.fansoffury.model.message.WebSocketMessage;
import com.omni.fansoffury.model.message.WebsocketMessageType;
import com.omni.fansoffury.player.ScoreService;
import com.omni.fansoffury.service.FanControllerService;

/**
 * Handles websocket connections from fan controllers
 * 
 * @author steve.perkins
 */
@Service
public class FanControllerSocketListener extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(FanControllerSocketListener.class);
	
	@Autowired
	DeviceControllerSocketSessionHandler deviceControllerSocketSessionHandler;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private FanControllerService fanControllerService;
	
	@Autowired
	private HeadsetService headsetService;
	
	@Autowired
	private ScoreService scoreService;
	
	
	Thread fakeDataThread;
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Opened new session " + session.getId());

		// TODO Remove when going live
		// Start generating fake data
		if(null == fakeDataThread) (fakeDataThread = new FakeScoreChangeThread()).start();
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// The only inbound messages from fan controllers will be score information and possibly a message indicating which devices are available
		logger.debug("Fan controller sent message: {}", message.getPayload());
		WebSocketMessage wsMessage = new Gson().fromJson(message.getPayload(), WebSocketMessage.class);
		if(WebsocketMessageType.DEVICE_CONTROLLER_REGISTRATION.name().equalsIgnoreCase(wsMessage.getMessageType())) {
			handleDeviceControllerRegistration(session, message);
		} else if(WebsocketMessageType.DEVICE_SCORE_CHANGE.name().equalsIgnoreCase(wsMessage.getMessageType())) {
			handleDeviceScoreChange( message);
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		logger.error("Fan controller transport exception", e);
		session.close(CloseStatus.PROTOCOL_ERROR);
	}
	
	protected void handleDeviceControllerRegistration(WebSocketSession session, TextMessage message) {
		DeviceControllerRegistrationEvent event = new Gson().fromJson(message.getPayload(), DeviceControllerRegistrationEvent.class);
		if(null != event && null != event.getDeviceController()) {
			deviceControllerSocketSessionHandler.addDeviceController(event.getDeviceController(), session);
		} else {
			logger.error("Device controller registration event received with no device controller");
		}
	}
	
	protected void handleDeviceScoreChange(TextMessage message) {
		DeviceScoreChangeEvent event = new Gson().fromJson(message.getPayload(), DeviceScoreChangeEvent.class);
		if(null != event && null != event.getDevice()) {
			logger.info("Score changed for device {}", event.getDevice().getId());
			Headset headset = headsetService.getByDeviceId(event.getDevice().getId());
			if(null != headset && null != headset.getPlayer()) {
				Player player = headset.getPlayer();
				player.setScore(player.getScore() + 1);
				ScoreChangedEvent scoreChangedEvent = new ScoreChangedEvent(headset, player.getScore());
				scoreService.raiseEvent(scoreChangedEvent);
			} else {
				logger.error("Score change event received for device ID " + event.getDevice().getId() + " with no associated player");
			}
		} else {
			logger.error("Score change event received without an associated device: " + message.getPayload());
		}
	}
	
	/**
	 * Generates fake data for testing purposes
	 * @author steve.perkins
	 *
	 */
	class FakeScoreChangeThread extends Thread {

		@Override
		public void run() {
			// Generate fake score changes
			while (true) {
				List<Device> devicesCopy = new ArrayList<Device>(deviceService.getDevices());
				for(Device device: devicesCopy) {
					if(null != device) {
						DeviceScoreChangeEvent event = new DeviceScoreChangeEvent();
						event.setDevice(device);
						event.setMessageType(WebsocketMessageType.DEVICE_SCORE_CHANGE.name());
						
						String json = new Gson().toJson(event);
						logger.debug("FAKE: Sending score changed event for device ID {}: {}", device.getId(), json);
						TextMessage message = new TextMessage(json);
						handleDeviceScoreChange(message);
						sleep();
					}
				}
				
			}
		}
		
		private void sleep() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
}
