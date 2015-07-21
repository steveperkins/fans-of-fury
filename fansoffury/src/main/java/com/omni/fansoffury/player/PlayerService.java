package com.omni.fansoffury.player;

import com.omni.fansoffury.model.Player;

public interface PlayerService {

	public Player getPlayer(String id);
	
	public Player createPlayer(Player player);
}
