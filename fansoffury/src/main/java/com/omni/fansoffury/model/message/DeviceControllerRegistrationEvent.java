package com.omni.fansoffury.model.message;

import com.omni.fansoffury.model.device.DeviceController;


/**
 * Indicates a device controller wants to register itself and its attached devices
 * @author steve.perkins
 *
 */
public class DeviceControllerRegistrationEvent extends WebSocketMessage {
	private static final long serialVersionUID = 1L;
	
	private DeviceController deviceController;
	
	public DeviceControllerRegistrationEvent() {}
	
	public DeviceControllerRegistrationEvent(DeviceController deviceController) {
		this.setDeviceController(deviceController);
	}
	
	public DeviceController getDeviceController() {
		return deviceController;
	}

	public void setDeviceController(DeviceController deviceController) {
		this.deviceController = deviceController;
	}
	
	@Override
	public String getMessageType() {
		return WebsocketMessageType.DEVICE_CONTROLLER_REGISTRATION.name();
	}
}
