package com.omni.fansoffury.socket.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.model.device.DeviceController;
import com.omni.fansoffury.model.message.DeviceControllerMessage;

/**
 * Manages sessions for device controllers
 * @author steve.perkins
 *
 */
@Component
public class DeviceControllerSocketSessionHandler extends TextWebSocketHandler {
	private static Logger logger = LoggerFactory.getLogger(DeviceControllerSocketSessionHandler.class);
	
	@Autowired
	DeviceService deviceService;
	
	private Map<DeviceController, WebSocketSession> deviceControllerToSessionMap = new HashMap<DeviceController, WebSocketSession>();

	public DeviceControllerSocketSessionHandler() {}
	
	public DeviceController getDeviceController(String id) {
		for(DeviceController controller: deviceControllerToSessionMap.keySet()) {
			if(controller.getId().equals(id)) return controller;
		}
		return null;
	}
	
	public void addDeviceController(DeviceController deviceController, WebSocketSession session) {
		logger.info("Now tracking device controller {} with session ID {}", deviceController.getId(), session.getId());
		deviceService.addDevices(deviceController);
		deviceControllerToSessionMap.put(deviceController, session);
    }

    public void removeDeviceController(DeviceController deviceController) {
    	logger.info("Removing session for device controller {}", deviceController.getId());
    	deviceControllerToSessionMap.remove(deviceController);
    	deviceService.removeDevices(deviceController);
    }
    
    public void broadcast(DeviceControllerMessage message) {
    	for(DeviceController deviceController: deviceControllerToSessionMap.keySet()) {
    		message.getDevice().setDeviceController(deviceController);
    		sendToDeviceController(message);
    	}
    }

    public void sendToDeviceController(DeviceControllerMessage message) {
    	String deviceControllerId = message.getDevice().getDeviceController().getId();
    	try {
    		WebSocketSession session = deviceControllerToSessionMap.get(getDeviceController(message.getDevice().getDeviceController().getId()));
    		if(null != session && session.isOpen()) {
	    		String json = new Gson().toJson(message);
	    		logger.debug("Sending message '{}' to device controller {}", json, deviceControllerId);
	            session.sendMessage(new TextMessage(json));
    		} else {
    			logger.error("No active session for device controller " + deviceControllerId);
    		}
        } catch (IOException e) {
        	// Assume the exception occurred because the client closed the session
        	deviceControllerToSessionMap.remove(message.getDevice().getDeviceController());
            logger.error("Device controller " + deviceControllerId + " died", e);
        }
    }
}
