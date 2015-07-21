package com.omni.fansoffury.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.Player;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private HeadsetService headsetService;
	
	@Autowired
	private DeviceService deviceService;
	
	// TODO Replace with database
	private List<Player> players = new ArrayList<Player>();

	@Override
	public Player getPlayer(String id) {
		for(Player player: players) {
			if(player.getId().equals(id)) return player;
		}
		return null;
	}

	@Override
	public Player createPlayer(Player player) {
		Player existingPlayer = getPlayer(player.getId());
		if(null != existingPlayer) {
			player = existingPlayer;
		} else {
			players.add(player);
		}
		return player;
	}
}
