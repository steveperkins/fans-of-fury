package com.omni.fansoffury.model.message;

import com.omni.fansoffury.model.device.Device;

/**
 * Indicates a fan needs to change its speed
 * @author steve.perkins
 *
 */
public class FanSpeedChangeEvent extends DeviceControllerMessage {
	private static final long serialVersionUID = 1L;
	
	private Double newSpeed;

	public FanSpeedChangeEvent(Device device, Double newSpeed) {
		super();
		this.newSpeed = newSpeed;
		setDevice(device);
	}

	public Double getNewSpeed() {
		return newSpeed;
	}

	public void setNewSpeed(Double newSpeed) {
		this.newSpeed = newSpeed;
	}

	@Override
	public String getMessageType() {
		return WebsocketMessageType.FAN_SPEED_CHANGE.name();
	}
}
