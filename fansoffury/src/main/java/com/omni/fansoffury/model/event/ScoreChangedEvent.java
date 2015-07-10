package com.omni.fansoffury.model.event;

import com.omni.fansoffury.model.Player;

/**
 * Generated when a player's score changes
 * @author steve.perkins
 *
 */
public class ScoreChangedEvent {
	private Player player;
	private Integer newScore;
	
	public ScoreChangedEvent(Player player, Integer newScore) {
		super();
		this.player = player;
		this.newScore = newScore;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Integer getNewScore() {
		return newScore;
	}
	public void setNewScore(Integer newScore) {
		this.newScore = newScore;
	}
}
