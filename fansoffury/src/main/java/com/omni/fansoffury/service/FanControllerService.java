
package com.omni.fansoffury.service;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.message.DeviceControllerMessage;

/**
 * Communicates with fan controllers
 * 
 * @author steve.perkins
 */
public interface FanControllerService {

	public void sendMessage(DeviceControllerMessage message);
	
	public void sendMessageToAll(DeviceControllerMessage message);
	
	public void changeFanSpeed(Headset headset, Integer percentage);
	
}
