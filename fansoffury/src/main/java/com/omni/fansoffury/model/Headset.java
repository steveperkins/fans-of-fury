package com.omni.fansoffury.model;

import com.omni.fansoffury.model.device.Device;

public class Headset {
	private String id;
	private Player player;
	private Device device;

	public Headset() {}
	
	public Headset(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
