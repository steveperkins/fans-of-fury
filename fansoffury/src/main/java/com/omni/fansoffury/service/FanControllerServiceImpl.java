
package com.omni.fansoffury.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.balance.FanSpeedBalanceStrategy;
import com.omni.fansoffury.device.DeviceControllerService;
import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.message.DeviceControllerMessage;
import com.omni.fansoffury.model.message.FanSpeedChangeEvent;
import com.omni.fansoffury.player.PlayerService;

/**
 * Communicates with fan controllers
 * 
 * @author steve.perkins
 */
@Service
public class FanControllerServiceImpl implements FanControllerService {

	private static Logger logger = LoggerFactory.getLogger(FanControllerServiceImpl.class);
	
	@Autowired
	private DeviceControllerService deviceControllerService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private FanSpeedBalanceStrategy fanSpeedBalanceStrategy;
	
	
	public void sendMessage(DeviceControllerMessage message) {
		deviceControllerService.sendMessage(message);
	}
	
	public void sendMessageToAll(DeviceControllerMessage message) {
		deviceControllerService.broadcast(message);
	}
	
	public void changeFanSpeed(Headset headset, Integer percentage) {
		// Look up the fan belonging to this headset ID
		Device device = headset.getDevice();
		if(null != device) {
//			Double alteredPercentage = fanSpeedBalanceStrategy.apply(headset.getPlayer(), percentage);
			Double alteredPercentage = new Double(percentage);
			
			// Convert percentage to real analog values the device can understand
			//Double analogValue = percentageToAnalog(alteredPercentage, device);
			//alteredPercentage = analogValue;
			
			logger.debug("Changing fan speed for Device {} to {} (originally {})", device.getId(), alteredPercentage, percentage);
			FanSpeedChangeEvent event = new FanSpeedChangeEvent(device, alteredPercentage);
			deviceControllerService.sendMessage(event);
		}
		
	}
	
	protected Double percentageToAnalog(Double percentage, Device fanDevice) {
		if(percentage >= fanDevice.getMaxInputValue()) return fanDevice.getMaxInputValue();
		if(percentage <= fanDevice.getMinInputValue()) return fanDevice.getMinInputValue();
		
		Double delta = fanDevice.getMaxInputValue() - fanDevice.getMinInputValue() + 1;
		Double analogValue = (percentage / 100) * delta;
		analogValue += fanDevice.getMinInputValue();
		return analogValue;
		
	}
}
