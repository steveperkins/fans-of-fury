package com.omni.fansoffury.device;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.device.DeviceController;

@Service
public class DeviceServiceImpl implements DeviceService {

	private static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
	
	// TODO Load from a data store
	private List<Device> devices = new ArrayList<Device>();
	
	@Override
	public Device getDevice(String id) {
		for(Device device: devices) {
			if(device.getId().equals(id)) return device;
		}
		return null;
	}
	
	@Override
	public List<Device> getDevices() {
		return devices;
	}
	
	@Override
	public void addDevice(Device device) {
		if(devices.contains(device)) {
			devices.remove(device);
		}
		devices.add(device);
	}
	
	@Override
	public void addDevices(List<Device> devices) {
		for(Device device: devices) {
			addDevice(device);
		}
	}
	
	@Override
	public void addDevices(DeviceController deviceController) {
		for(Device device: deviceController.getDevices()) {
			device.setDeviceController(deviceController);
			addDevice(device);
		}
	}
	
	@Override
	public void removeDevice(Device device) {
		devices.remove(device);
	}
	
	@Override
	public void removeDevices(List<Device> devices) {
		this.devices.removeAll(devices);
	}
	
	@Override
	public void removeDevices(DeviceController deviceController) {
		removeDevices(deviceController.getDevices());
	}

	
/*	@PostConstruct
	private void loadDevices() {
		DeviceController deviceController = DeviceControllerServiceImpl.TEST_DEVICE_CONTROLLER;
		
		Device fan1 = new FanDevice("0", deviceController, 9.0, 15.0);
		Device fan2 = new FanDevice("1", deviceController, 9.0, 15.0);
		
		List<Device> devices = new ArrayList<Device>();
		devices.add(fan1);
		devices.add(fan2);
		deviceController.setDevices(devices);
		
		this.devices.addAll(devices);
	}*/
}
