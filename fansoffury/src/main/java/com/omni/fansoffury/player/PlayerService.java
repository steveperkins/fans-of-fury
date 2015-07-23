package com.omni.fansoffury.player;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;
import com.sperkins.mindwave.event.EventType;

public interface PlayerService {

	Player getPlayer(String id);
	void startPlayerSession(Player player, Headset headset, EventType eventType, Device device);
	void endPlayerSession(Headset headset);
}
