package com.omni.fansoffury.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.model.device.DeviceController;
import com.omni.fansoffury.model.message.DeviceControllerMessage;
import com.omni.fansoffury.socket.handler.DeviceControllerSocketSessionHandler;

/**
 * This class is essentially a proxy to DeviceControllerSocketSessionHandler
 * @author steve.perkins
 *
 */
@Service
public class DeviceControllerServiceImpl implements DeviceControllerService {
	
	@Autowired
	private DeviceControllerSocketSessionHandler socketSessionHandler;
	
	@Override
	public DeviceController getDeviceController(String id) {
		return socketSessionHandler.getDeviceController(id);
	}

	@Override
	public void sendMessage(DeviceControllerMessage message) {
		socketSessionHandler.sendToDeviceController(message);
	}

	@Override
	public void broadcast(DeviceControllerMessage message) {
		socketSessionHandler.broadcast(message);
	}
	
}
