package com.omni.fansoffury.model.event;

import com.omni.fansoffury.model.Player;
import com.sperkins.mindwave.event.EventType;

/**
 * Generated when a player's Attention or Meditation value changes - roughly twice per second per headset
 * @author steve.perkins
 *
 */
public class EegChangedEvent {
	private Player player;
	private String headsetId;
	private EventType eventType;
	private Integer value;
	
	public EegChangedEvent(Player player, EventType eventType, Integer value) {
		super();
		this.player = player;
		this.eventType = eventType;
		this.value = value;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getHeadsetId() {
		return headsetId;
	}

	public void setHeadsetId(String headsetId) {
		this.headsetId = headsetId;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
