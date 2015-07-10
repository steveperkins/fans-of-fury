package com.omni.fansoffury.headset.service;

import java.util.List;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;

public interface HeadsetService {
	
	public Headset getHeadset(String id);
	
	public List<Headset> getHeadsets();
	
	public void mapHeadsetToPlayer(Player player, Headset headset);
	
	public void shutdown();
	
}
