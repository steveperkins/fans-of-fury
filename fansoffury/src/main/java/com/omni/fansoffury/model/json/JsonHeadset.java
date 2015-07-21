package com.omni.fansoffury.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sperkins.mindwave.event.EventType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonHeadset {
	String playerId;
	String headsetId;
	EventType measurementType;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String fanId;
	
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String id) {
		this.playerId = id;
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
	public String getFanId() {
		return fanId;
	}
	public void setFanId(String fanId) {
		this.fanId = fanId;
	}
	
}
