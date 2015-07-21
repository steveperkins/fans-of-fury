package com.omni.fansoffury.headset.service;

import java.util.List;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;

public interface HeadsetService {
	
	public Headset getHeadset(String id);
	
	public List<Headset> getHeadsets();
	
	public Headset getByDeviceId(String deviceId);
	
	/**
	 * Headset.setPlayer() is public. changeHeadsetPlayer() should be used instead to allow other code to execute when a headset's player changes.
	 * @param headset
	 * @param player
	 */
	public void changeHeadsetPlayer(Headset headset, Player player);
	
	public void shutdown();

	/**
	 * Headset.setDevice() is public. changeHeadsetDevice() should be used instead to allow other code to execute when a headset's device changes.
	 * @param headset
	 * @param device
	 */
	public void changeHeadsetDevice(Headset headset, Device device);
	
}
