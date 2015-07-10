package com.omni.fansoffury.model.device;

public class FanDevice extends Device {
	private static final long serialVersionUID = 1L;
	
	public FanDevice() {
		super();
	}
	
	public FanDevice(String id, DeviceController deviceController) {
		super(id, deviceController);
	}
	
	public FanDevice(String id, DeviceController deviceController, Double minInputValue, Double maxInputValue) {
		super(id, deviceController, minInputValue, maxInputValue);
	}

	@Override
	public String getType() {
		return "fan";
	}
	
}
