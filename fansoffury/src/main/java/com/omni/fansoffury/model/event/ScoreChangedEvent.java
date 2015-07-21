package com.omni.fansoffury.model.event;

import com.omni.fansoffury.model.Headset;

/**
 * Generated when a player's score changes
 * @author steve.perkins
 *
 */
public class ScoreChangedEvent {
	private Integer newScore;
	private Headset headset;
	
	public ScoreChangedEvent(Headset headset, Integer newScore) {
		super();
		this.setHeadset(headset);
		this.newScore = newScore;
	}
	
	public Headset getHeadset() {
		return headset;
	}

	public void setHeadset(Headset headset) {
		this.headset = headset;
	}

	public Integer getNewScore() {
		return newScore;
	}
	public void setNewScore(Integer newScore) {
		this.newScore = newScore;
	}
}
