package com.omni.fansoffury.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omni.fansoffury.model.Headset;
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
	public void startPlayerSession(Headset headset) {
		playerRepository.startPlayerSession(headset);
	}
	
	@Override
	public void endPlayerSession(Headset headset) {
		playerRepository.endPlayerSession(headset);
		headset.setDevice(null);
		headset.setPlayer(null);
	}
}
