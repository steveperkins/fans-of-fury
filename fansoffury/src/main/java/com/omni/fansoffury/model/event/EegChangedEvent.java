package com.omni.fansoffury.model.event;

import java.util.Date;

import com.omni.fansoffury.model.Headset;
import com.sperkins.mindwave.event.EventType;

/**
 * Generated when a player's Attention or Meditation value changes - roughly twice per second per headset
 * @author steve.perkins
 *
 */
public class EegChangedEvent {
	private Headset headset;
	private EventType eventType;
	private Integer value;
	private Long timestamp = new Date().getTime();
	
	public EegChangedEvent(Headset headset, EventType eventType, Integer value) {
		super();
		this.headset = headset;
		this.eventType = eventType;
		this.value = value;
	}
	
	public Headset getHeadset() {
		return headset;
	}

	public void setHeadset(Headset headset) {
		this.headset = headset;
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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
