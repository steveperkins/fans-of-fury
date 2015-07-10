package com.omni.fansoffury.model.message;

import com.omni.fansoffury.model.device.Device;

public abstract class DeviceControllerMessage extends WebSocketMessage {
	private static final long serialVersionUID = 1L;
	
	private Device device;
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public abstract String getMessageType();

}
