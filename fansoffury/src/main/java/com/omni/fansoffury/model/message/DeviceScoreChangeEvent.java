package com.omni.fansoffury.model.message;

import com.omni.fansoffury.model.device.Device;


/**
 * Indicates the score changed for a specific device
 * @author steve.perkins
 *
 */
public class DeviceScoreChangeEvent extends DeviceControllerMessage {
	private static final long serialVersionUID = 1L;

	public DeviceScoreChangeEvent() {}
	
	public DeviceScoreChangeEvent(Device device) {
		this.setDevice(device);
	}
	
	@Override
	public String getMessageType() {
		return WebsocketMessageType.DEVICE_SCORE_CHANGE.name();
	}
}
