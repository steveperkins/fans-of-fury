package com.omni.fansoffury.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.device.DeviceService;
import com.omni.fansoffury.headset.service.HeadsetService;
import com.omni.fansoffury.model.Headset;
import com.omni.fansoffury.model.Player;
import com.omni.fansoffury.model.device.Device;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private HeadsetService headsetService;
	
	@Autowired
	private DeviceService deviceService;
	
	// TODO Replace with database
	private List<Player> players = new ArrayList<Player>();
	private Map<Player, Device> playerToDeviceMap = new HashMap<Player, Device>();

	@Override
	public Player getPlayer(String id) {
		for(Player player: players) {
			if(player.getId().equals(id)) return player;
		}
		return null;
	}
	
	@Override
	public Device getDevice(Player player) {
		for(Map.Entry<Player, Device> entry: playerToDeviceMap.entrySet()) {
			if(player.getId().equals(entry.getKey().getId())) return entry.getValue();
		}
		return null;
	}
	
	@Override
	public void mapPlayerToDevice(Player player, Device device) {
		playerToDeviceMap.put(player, device);
	}

	@Override
	public Player getPlayer(Device device) {
		for(Map.Entry<Player, Device> entry: playerToDeviceMap.entrySet()) {
			if(entry.getValue().getId().equals(device.getId())) return entry.getKey();
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

	@Override
	public Player getPlayer(Headset headset) {
		for(Player player: players) {
			if(null != player.getHeadset() && player.getHeadset().getId().equals(headset.getId())) return player;
		}
		return null;
	}
}
