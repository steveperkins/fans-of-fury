package com.omni.fansoffury.model.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private List<Device> devices;
	
	public DeviceController() {}
	
	public DeviceController(String id) {
		this.id = id;
	}
	
	public DeviceController(String id, List<Device> devices) {
		this.id = id;
		this.devices = devices;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Device> getDevices() {
		if(null == devices) devices = new ArrayList<Device>();
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}
