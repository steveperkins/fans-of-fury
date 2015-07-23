package com.omni.fansoffury.player;

import java.util.ArrayList;
import java.util.List;

import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.device.Device;
import com.omni.fansoffury.model.device.FanDevice;
import com.sperkins.mindwave.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.Player;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;


	@Override
	public Player getPlayer(String id) {
		return playerRepository.findOrCreateByQRCode(id);
	}

	@Override
	public void startPlayerSession(Player player, Headset headset, EventType eventType, Device fan) {
		playerRepository.startPlayerSession(headset,eventType,player,fan);
	}

	@Override
	public void endPlayerSession(Headset headset) {
		playerRepository.endPlayerSession(headset);
	}
}
