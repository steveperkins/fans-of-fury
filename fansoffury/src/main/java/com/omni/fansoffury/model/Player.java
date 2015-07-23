package com.omni.fansoffury.model;

import java.io.Serializable;
import java.util.Objects;

import com.sperkins.mindwave.event.EventType;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	// QR code ID
	private String id;
	// Double so we can record/show partial levels
	private Double attentionLevel = 0.0;
	private Double meditationLevel = 0.0;
	private String headsetId;
	private EventType measurementType = EventType.ATTENTION;
	private Integer score = 0;

	public Player() {}

	public Player(String id, Double attentionLevel, Double meditationLevel, Headset headset) {
		super();
		this.id = id;
		this.attentionLevel = attentionLevel;
		this.meditationLevel = meditationLevel;
		this.headset = headset;
	}

	public Player(String id, Double attentionLevel, Double meditationLevel, Headset headset, EventType measurementType) {
		this(id, attentionLevel, meditationLevel, headset);
		this.measurementType = measurementType;
	}

	public Player(String id, Double attentionLevel, Double meditationLevel, EventType measurementType) {
		this(id, attentionLevel, meditationLevel, null, measurementType);
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getAttentionLevel() {
		return attentionLevel;
	}
	public void setAttentionLevel(Double level) {
		this.attentionLevel = level;
	}

	public Double getMeditationLevel() {
		return meditationLevel;
	}

	public void setMeditationLevel(Double meditationLevel) {
		this.meditationLevel = meditationLevel;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return Objects.equals(id, player.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
