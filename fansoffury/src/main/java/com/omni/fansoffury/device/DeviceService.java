package com.omni.fansoffury.device;

import java.util.List;

import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.device.DeviceController;

public interface DeviceService {

	public Device getDevice(String id);
	
	public List<Device> getDevices();
	
	public void addDevice(Device device);
	
	public void addDevices(List<Device> devices);
	
	public void addDevices(DeviceController deviceController);
	
	public void removeDevice(Device device);
	
	public void removeDevices(List<Device> devices);
	
	public void removeDevices(DeviceController deviceController);
	
}
