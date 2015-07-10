
package com.omni.fansoffury.service;

import org.springframework.stereotype.Service;

import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.message.DeviceControllerMessage;

/**
 * Communicates with fan controllers
 * 
 * @author steve.perkins
 */
public interface FanControllerService {

	public void sendMessage(DeviceControllerMessage message);
	
	public void sendMessageToAll(DeviceControllerMessage message);
	
	public void changeFanSpeed(Player player, Integer percentage);
	
}
