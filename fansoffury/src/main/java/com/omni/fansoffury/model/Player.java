package com.omni.fansoffury.model;

import java.io.Serializable;

import com.sperkins.mindwave.event.EventType;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// QR code ID
	private String id;
	// Double so we can record/show partial levels
	private Double level = 0.0;
	private Headset headset;
	private String headsetId;
	private EventType measurementType = EventType.ATTENTION;
	private Integer score = 0;
	
	public Player() {}
	
	public Player(String id, Double level, Headset headset) {
		super();
		this.id = id;
		this.level = level;
		this.headset = headset;
	}
	
	public Player(String id, Double level, Headset headset, EventType measurementType) {
		this(id, level, headset);
		this.measurementType = measurementType;
	}
	
	public Player(String id, Double level, EventType measurementType) {
		this(id, level, null, measurementType);
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getLevel() {
		return level;
	}
	public void setLevel(Double level) {
		this.level = level;
	}
	public Headset getHeadset() {
		return headset;
	}
	public void setHeadset(Headset headset) {
		this.headset = headset;
	}

	public String getHeadsetId() {
		return headsetId;
	}

	public void setHeadsetId(String headsetId) {
		this.headsetId = headsetId;
	}

	public EventType getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(EventType measurementType) {
		this.measurementType = measurementType;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
