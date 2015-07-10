package com.omni.fansoffury.device;

import com.omni.fansoffury.model.device.DeviceController;
import com.omni.fansoffury.model.message.DeviceControllerMessage;

public interface DeviceControllerService {
	
	public DeviceController getDeviceController(String id);
	
	public void sendMessage(DeviceControllerMessage message);
	
	public void broadcast(DeviceControllerMessage message);
	
}
