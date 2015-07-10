package com.omni.fansoffury.player;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;

public interface PlayerService {

	public Player getPlayer(String id);
	
	public void mapPlayerToDevice(Player player, Device device);
	
	public Device getDevice(Player player);
	
	public Player getPlayer(Device device);
	
	public Player getPlayer(Headset headset);
	
	public Player createPlayer(Player player);
}
