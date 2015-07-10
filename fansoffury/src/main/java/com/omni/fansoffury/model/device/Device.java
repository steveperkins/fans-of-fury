package com.omni.fansoffury.model.device;

import java.io.Serializable;
/**
 * Parent class for connected devices
 * @author steve.perkins
 *
 */
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private transient DeviceController deviceController;
	private String type;
	private Double minInputValue;
	private Double maxInputValue;
	
	
	public Device() {}
	
	public Device(String id, DeviceController deviceController) {
		this.id = id;
		this.setDeviceController(deviceController);
	}
	
	public Device(String id, DeviceController deviceController, Double minInputValue, Double maxInputValue) {
		this(id, deviceController);
		this.minInputValue = minInputValue;
		this.maxInputValue = maxInputValue;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public DeviceController getDeviceController() {
		return deviceController;
	}
	public void setDeviceController(DeviceController deviceController) {
		this.deviceController = deviceController;
	}
	public Double getMinInputValue() {
		return minInputValue;
	}
	public void setMinInputValue(Double minInputValue) {
		this.minInputValue = minInputValue;
	}
	public Double getMaxInputValue() {
		return maxInputValue;
	}
	public void setMaxInputValue(Double maxInputValue) {
		this.maxInputValue = maxInputValue;
	}
}
