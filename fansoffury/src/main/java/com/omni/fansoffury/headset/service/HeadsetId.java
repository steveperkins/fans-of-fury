package com.omni.fansoffury.headset.service;

public enum HeadsetId {
	
	HEADSET_1("74E543D575B0"),
	HEADSET_2("20689D4C0A08");
	
	String id;
	HeadsetId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
